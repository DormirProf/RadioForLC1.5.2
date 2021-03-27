package net.reodont.radioblock.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PlayList {

   List mp3 = new ArrayList();
   int next;


   public PlayList(URL m3u) throws IOException {
      URLConnection con = m3u.openConnection();
      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

      try {
         String ex;
         try {
            while((ex = br.readLine()) != null) {
               System.out.println(ex);
               this.addMP3(ex);
            }
         } catch (IOException var8) {
            System.out.println("error is reading the file");
         }
      } finally {
         br.close();
      }

      this.next = -1;
   }

   private void addMP3(String line) {
      if(line != null) {
         if(!line.isEmpty()) {
            if(!line.startsWith("#")) {
               System.out.println(line);
               this.mp3.add(line);
            }
         }
      }
   }

   public String getNext() {
      ++this.next;
      if(this.mp3.size() <= this.next) {
         this.next = 0;
      }

      return (String)this.mp3.get(this.next);
   }
}
