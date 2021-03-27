package net.reodont.radioblock.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.reodont.radioblock.ModRadioBlock;
import net.reodont.radioblock.TileEntityRadio;
import net.reodont.radioblock.gui.util.GuiVolumeSlider;
import net.reodont.radioblock.player.PlayList;
import org.lwjgl.input.Keyboard;

public class GuiRadio extends GuiScreen {

   private TileEntityRadio radio;
   private GuiTextField streamTextBox;


   public GuiRadio(TileEntityRadio r) {
      this.radio = r;
   }
   
   //Update
   private GuiButton start;
   private GuiButton stop;
   public void initGui() {
 	  super.buttonList.add(stop = new GuiButton(4, super.width / 2 - 50, super.height / 2 + 10, 90, 20, "Остановить"));
      super.buttonList.add(start = new GuiButton(0, super.width / 2 - 150, super.height / 2 + 10, 90, 20, "Запустить"));
      super.buttonList.add(new GuiButton(1, super.width / 2 + 50, super.height / 2 + 10, 90, 20, "Сохранить"));
      super.buttonList.add(new GuiVolumeSlider(2, super.width / 2 - 80, super.height / 2 - 10, "Громкость", 0.3F));
      this.streamTextBox = new GuiTextField(super.fontRenderer, super.width / 2 - 100, super.height / 2 + 35, 200, 20);
      this.streamTextBox.setMaxStringLength(1000);
      this.streamTextBox.setText(this.radio.streamURL);
      Keyboard.enableRepeatEvents(true);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.streamTextBox.drawTextBox();
      super.drawScreen(par1, par2, par3);
   }

   public void updateScreen() {
      this.streamTextBox.updateCursorCounter();
      if(this.radio.isInvalid()) {
         super.mc.displayGuiScreen((GuiScreen)null);
         super.mc.setIngameFocus();
      }

   }

   protected void keyTyped(char par1, int par2) {
      this.streamTextBox.textboxKeyTyped(par1, par2);
      if(par1 == 13) {
         this.actionPerformed((GuiButton)super.buttonList.get(1));
      }

      super.keyTyped(par1, par2);
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      this.streamTextBox.mouseClicked(par1, par2, par3);
      super.mouseClicked(par1, par2, par3);
   }
   //Update
   @SideOnly(Side.CLIENT)
   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.id == 0) 
    	  if(!this.radio.isPlaying())
    		  Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(ModRadioBlock.setPacket(this.radio.xCoord, this.radio.yCoord, this.radio.zCoord, this.radio.streamURL, !this.radio.isPlaying()));      
      if(par1GuiButton.id == 1) {
         if(this.streamTextBox.getText().toLowerCase().endsWith(".m3u")) 
            this.radio.streamURL = this.firstChannel(this.streamTextBox.getText());
          else 
            this.radio.streamURL = this.streamTextBox.getText();
         Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(ModRadioBlock.setPacket(this.radio.xCoord, this.radio.yCoord, this.radio.zCoord, this.radio.streamURL, this.radio.isPlaying()));
      }
      if(par1GuiButton.id == 2 && this.radio != null && this.radio.getPlayer() != null)
         this.radio.getPlayer().setMaxVolume(((GuiVolumeSlider)par1GuiButton).sliderValue);   
      if(par1GuiButton.id == 3) 
          this.radio.getPlayer().setMaxVolume(((GuiVolumeSlider)par1GuiButton).sliderValue);
          Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(ModRadioBlock.setPacket(this.radio.xCoord, this.radio.yCoord, this.radio.zCoord, this.radio.streamURL, !this.radio.isPlaying()));
      if(par1GuiButton.id == 4)
    	  if(this.radio.isPlaying())  
    		  Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(ModRadioBlock.setPacket(this.radio.xCoord, this.radio.yCoord, this.radio.zCoord, this.radio.streamURL, !this.radio.isPlaying()));
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public String firstChannel(String m3Uurl) {
      String out = "";

      try {
         PlayList e = new PlayList(new URL(m3Uurl));
         out = e.getNext();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return out;
   }
}
