package net.reodont.radioblock;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.reodont.radioblock.CommonProxy;
import net.reodont.radioblock.RenderRadioBlock;
import net.reodont.radioblock.TileEntityRadio;

public class ClientProxy extends CommonProxy {

   public void registerRenderers() {}

   public void initTileEntities() {
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadio.class, new RenderRadioBlock());
   }
}
