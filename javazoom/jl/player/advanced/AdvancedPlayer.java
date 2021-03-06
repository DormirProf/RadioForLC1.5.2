package javazoom.jl.player.advanced;

import java.io.InputStream;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class AdvancedPlayer {

   private Bitstream bitstream;
   private Decoder decoder;
   private AudioDevice audio;
   private boolean closed;
   private boolean complete;
   private int lastPosition;
   private PlaybackListener listener;
   private float volume;


   public AdvancedPlayer(InputStream stream) throws JavaLayerException {
      this(stream, (AudioDevice)null);
   }

   public AdvancedPlayer(InputStream stream, AudioDevice device) throws JavaLayerException {
      this.closed = false;
      this.complete = false;
      this.lastPosition = 0;
      this.volume = 1.0F;
      this.bitstream = new Bitstream(stream);
      if(device != null) {
         this.audio = device;
      } else {
         this.audio = FactoryRegistry.systemRegistry().createAudioDevice();
      }

      this.audio.open(this.decoder = new Decoder());
   }

   public void play() throws JavaLayerException {
      this.play(Integer.MAX_VALUE);
   }

   public boolean play(int frames) throws JavaLayerException {
      boolean ret = true;
      if(this.listener != null) {
         this.listener.playbackStarted(this.createEvent(PlaybackEvent.STARTED));
      }

      while(frames-- > 0 && ret) {
         ret = this.decodeFrame();
      }

      AudioDevice out = this.audio;
      if(out != null) {
         out.flush();
         synchronized(this) {
            this.complete = !this.closed;
            this.close();
         }

         if(this.listener != null) {
            this.listener.playbackFinished(this.createEvent(out, PlaybackEvent.STOPPED));
         }
      }

      return ret;
   }

   public synchronized void close() {
      AudioDevice out = this.audio;
      if(out != null) {
         this.closed = true;
         this.audio = null;
         out.close();
         this.lastPosition = out.getPosition();

         try {
            this.bitstream.close();
         } catch (BitstreamException var3) {
            ;
         }
      }

   }

   protected boolean decodeFrame() throws JavaLayerException {
      try {
         AudioDevice ex = this.audio;
         if(ex == null) {
            return false;
         } else {
            Header h = this.bitstream.readFrame();
            if(h == null) {
               return false;
            } else {
               SampleBuffer output = (SampleBuffer)this.decoder.decodeFrame(h, this.bitstream);
               synchronized(this) {
                  ex = this.audio;
                  if(ex != null) {
                     short[] samples = output.getBuffer();

                     for(int samp = 0; samp < samples.length; ++samp) {
                        samples[samp] = (short)((int)((float)samples[samp] * this.volume));
                     }

                     ex.write(samples, 0, output.getBufferLength());
                  }
               }

               this.bitstream.closeFrame();
               return true;
            }
         }
      } catch (RuntimeException var9) {
         throw new JavaLayerException("Exception decoding audio frame", var9);
      }
   }

   protected boolean skipFrame() throws JavaLayerException {
      Header h = this.bitstream.readFrame();
      if(h == null) {
         return false;
      } else {
         this.bitstream.closeFrame();
         return true;
      }
   }

   public boolean play(int start, int end) throws JavaLayerException {
      boolean ret = true;

      for(int offset = start; offset-- > 0 && ret; ret = this.skipFrame()) {
         ;
      }

      return this.play(end - start);
   }

   private PlaybackEvent createEvent(int id) {
      return this.createEvent(this.audio, id);
   }

   private PlaybackEvent createEvent(AudioDevice dev, int id) {
      return new PlaybackEvent(this, id, dev.getPosition());
   }

   public void setPlayBackListener(PlaybackListener listener) {
      this.listener = listener;
   }

   public PlaybackListener getPlayBackListener() {
      return this.listener;
   }

   public void stop() {
      this.listener.playbackFinished(this.createEvent(PlaybackEvent.STOPPED));
      this.close();
   }

   public void setVolume(float f) {
      this.volume = f;
   }

   public float getVolume() {
      return this.volume;
   }
}
