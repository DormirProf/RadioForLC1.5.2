package net.reodont.radioblock;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.reodont.radioblock.TileEntityRadio;

public class PacketHandler implements IPacketHandler {

   public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
      if(packet.channel.equals("ReodontRadio")) {
         Side side = FMLCommonHandler.instance().getEffectiveSide();
         if(side == Side.SERVER) {
            PacketDispatcher.sendPacketToAllPlayers(packet);
         }

         DataInputStream is = new DataInputStream(new ByteArrayInputStream(packet.data));

         try {
            is.readInt();
            int e = is.readInt();
            int y = is.readInt();
            int z = is.readInt();
            TileEntity te = null;
            if(side == Side.SERVER) {
               EntityPlayerMP r = (EntityPlayerMP)player;
               te = MinecraftServer.getServer().worldServerForDimension(r.dimension).getBlockTileEntity(e, y, z);
            }

            if(side == Side.CLIENT) {
               te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(e, y, z);
            }

            if(te instanceof TileEntityRadio) {
               TileEntityRadio r1 = (TileEntityRadio)te;
               String surl = is.readUTF();
               r1.streamURL = surl;
               boolean playing = is.readBoolean();
               if(playing && !r1.isPlaying()) {
                  r1.startStream();
               } else {
                  r1.stopStream();
               }
            }
         } catch (Exception var13) {
            var13.printStackTrace();
         }
      }

   }
}
