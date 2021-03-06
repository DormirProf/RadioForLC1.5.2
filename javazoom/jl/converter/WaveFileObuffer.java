package javazoom.jl.converter;

import javazoom.jl.converter.WaveFile;
import javazoom.jl.decoder.Obuffer;

public class WaveFileObuffer extends Obuffer {

   private short[] buffer;
   private short[] bufferp;
   private int channels;
   private WaveFile outWave;
   short[] myBuffer = new short[2];


   public WaveFileObuffer(int number_of_channels, int freq, String FileName) {
      if(FileName == null) {
         throw new NullPointerException("FileName");
      } else {
         this.buffer = new short[2304];
         this.bufferp = new short[2];
         this.channels = number_of_channels;

         for(int rc = 0; rc < number_of_channels; ++rc) {
            this.bufferp[rc] = (short)rc;
         }

         this.outWave = new WaveFile();
         this.outWave.OpenForWrite(FileName, freq, (short)16, (short)this.channels);
      }
   }

   public void append(int channel, short value) {
      this.buffer[this.bufferp[channel]] = value;
      this.bufferp[channel] = (short)(this.bufferp[channel] + this.channels);
   }

   public void write_buffer(int val) {
      boolean k = false;
      boolean rc = false;
      int var5 = this.outWave.WriteData(this.buffer, this.bufferp[0]);

      for(int i = 0; i < this.channels; ++i) {
         this.bufferp[i] = (short)i;
      }

   }

   public void close() {
      this.outWave.Close();
   }

   public void clear_buffer() {}

   public void set_stop_flag() {}
}
