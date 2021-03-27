package net.reodont.radioblock;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.reodont.radioblock.ModRadioBlock;
import net.reodont.radioblock.player.MP3Player;

public class TileEntityRadio extends TileEntity {

   private MP3Player player = null;
   private boolean isPlaying = false;
   public String streamURL = "";


   public MP3Player getPlayer() {
      return this.player;
   }

   public Block getBlockType() {
      return ModRadioBlock.blockRadio;
   }

   void startStream() {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if(!this.isPlaying) {
         this.isPlaying = true;
         if(side == Side.CLIENT) {
            this.player = new MP3Player(this.streamURL);
            ModRadioBlock.playerList.add(this.player);
         }
      }

   }

   void stopStream() {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if(this.isPlaying) {
         if(side == Side.CLIENT && this.player != null) {
            this.player.stop();
            ModRadioBlock.playerList.remove(this.player);
         }

         this.isPlaying = false;
      }

   }

   public boolean isPlaying() {
      return this.isPlaying;
   }

   @SideOnly(Side.CLIENT)
   public void invalidate() {
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(ModRadioBlock.setPacket(super.xCoord, super.yCoord, super.zCoord, this.streamURL, !this.isPlaying()));
      this.stopStream();
      super.invalidate();
   }

   @SideOnly(Side.CLIENT)
   public void onChunkUnload() {
      if(this.isPlaying) {
         this.stopStream();
      }

   }

   @SideOnly(Side.CLIENT)
   public void updateEntity() {
      if(Minecraft.getMinecraft().thePlayer != null && this.player != null && !this.isInvalid()) {
         float vol = (float)this.getDistanceFrom(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
         if(vol > 500.0F) {
            this.player.setVolume(0.0F);
         } else {
            float v2 = 500.0F / vol / 10.0F;
            if(v2 > 1.0F) {
               this.player.setVolume(1.0F);
            } else {
               this.player.setVolume(v2);
            }
         }
      }

   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.streamURL = par1NBTTagCompound.getString("streamurl");
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setString("streamurl", this.streamURL);
   }

   public Packet getDescriptionPacket() {
      return ModRadioBlock.setPacket(super.xCoord, super.yCoord, super.zCoord, this.streamURL, this.isPlaying);
   }
}
