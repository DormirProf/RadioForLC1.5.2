package net.reodont.radioblock.gui.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.reodont.radioblock.player.MP3Player;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiVolumeSlider extends GuiButton {

   public float sliderValue = 1.0F;
   public boolean dragging = false;
   private MP3Player player;


   public GuiVolumeSlider(int par1, int par2, int par3, String par5Str, float par6) {
      super(par1, par2, par3, 150, 20, par5Str);
      this.sliderValue = par6;
   }

   protected int getHoverState(boolean par1) {
      return 0;
   }

   public void setPlayer(MP3Player player) {
      this.player = player;
   }

   protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
      if(super.drawButton) {
         if(this.dragging) {
            this.sliderValue = (float)(par2 - (super.xPosition + 4)) / (float)(super.width - 8);
            if(this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            }

            if(this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)), super.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)) + 4, super.yPosition, 196, 66, 4, 20);
      }

   }

   public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
      if(super.mousePressed(par1Minecraft, par2, par3)) {
         this.sliderValue = (float)(par2 - (super.xPosition + 4)) / (float)(super.width - 8);
         if(this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         }

         if(this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased(int par1, int par2) {
      this.dragging = false;
      if(this.player != null) {
         this.player.setMaxVolume(this.sliderValue);
      }

   }
}
