package net.reodont.radioblock;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.ServerStopped;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.reodont.radioblock.BlockRadio;
import net.reodont.radioblock.CommonProxy;
import net.reodont.radioblock.ConnectionHandler;
import net.reodont.radioblock.PacketHandler;
import net.reodont.radioblock.TileEntityRadio;
import net.reodont.radioblock.player.MP3Player;

@Mod(
   modid = "ReodontRadio",
   name = "Reodont Radio Block",
   version = "0.2"
)
@NetworkMod(
   channels = {"ReodontRadio"},
   clientSideRequired = true,
   serverSideRequired = false,
   packetHandler = PacketHandler.class,
   connectionHandler = ConnectionHandler.class
)
public class ModRadioBlock {

   @Instance
   public static ModRadioBlock instance;
   @SidedProxy(
      clientSide = "net.reodont.radioblock.ClientProxy",
      serverSide = "net.reodont.radioblock.CommonProxy"
   )
   public static CommonProxy proxy;
   public static final Block blockRadio = new BlockRadio(196, Material.wood);
   public static List playerList = new ArrayList();


   @Init
   public void init(FMLInitializationEvent evt) {
      GameRegistry.registerBlock(blockRadio, "Радио");
      GameRegistry.registerTileEntity(TileEntityRadio.class, "Радио");
      LanguageRegistry.addName(blockRadio, "Радио");
      proxy.initTileEntities();
   }

   @ServerStopped
   public void serverStop(FMLServerStoppedEvent event) {
      killAllStreams();
   }

   public static void killAllStreams() {
      Iterator var0 = playerList.iterator();

      while(var0.hasNext()) {
         MP3Player p = (MP3Player)var0.next();
         p.stop();
      }

   }

   public static Packet250CustomPayload setPacket(int x, int y, int z, String streamURL, boolean playing) {
      Packet250CustomPayload p = new Packet250CustomPayload();
      p.channel = "ReodontRadio";
      ByteArrayOutputStream baos = new ByteArrayOutputStream(8);
      DataOutputStream dos = new DataOutputStream(baos);

      try {
         dos.writeInt(1);
         dos.writeInt(x);
         dos.writeInt(y);
         dos.writeInt(z);
         dos.writeUTF(streamURL);
         dos.writeBoolean(playing);
      } catch (IOException var9) {
         var9.printStackTrace();
      }

      p.length = baos.toByteArray().length;
      p.data = baos.toByteArray();
      return p;
   }

}
