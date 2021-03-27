package net.reodont.radioblock.player;

import java.net.URL;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MP3Player extends PlaybackListener implements Runnable {

   private String streamURL;
   private AdvancedPlayer player;
   private Thread pThread;
   private float maxVolume;


   public AdvancedPlayer getPlayer() {
      return this.player;
   }

   public MP3Player(String mp3url) {
      try {
         this.maxVolume = 0.1F;
         this.streamURL = mp3url;
         this.pThread = new Thread(this);
         this.pThread.start();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public void run() {
      try {
         System.out.println(this.streamURL);
         this.player = new AdvancedPlayer((new URL(this.streamURL)).openStream());
         this.player.setPlayBackListener(this);
         this.player.play();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void stop() {
      if(this.player != null && this.isPlaying()) {
         this.player.stop();
      }

   }

   public void playbackStarted(PlaybackEvent evt) {}

   public void playbackFinished(PlaybackEvent evt) {}

   public boolean isPlaying() {
      return this.pThread.isAlive();
   }

   public void setVolume(float f) {
      if(this.player != null) {
         this.player.setVolume(f * this.maxVolume);
      }

   }

   public float getVolume() {
      return this.player.getVolume();
   }

   public void setMaxVolume(float maxVolume) {
      this.maxVolume = maxVolume;
   }
}
