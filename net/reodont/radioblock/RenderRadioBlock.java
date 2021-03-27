package net.reodont.radioblock;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.reodont.radioblock.ModelRadio;
import org.lwjgl.opengl.GL11;

public class RenderRadioBlock extends TileEntitySpecialRenderer {

   ModelRadio model = new ModelRadio();


   public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
      GL11.glDisable(2884);
      GL11.glPushMatrix();
      switch(tileentity.getBlockMetadata()) {
      case 1:
         GL11.glTranslatef((float)d0 + 0.7F, (float)d1 + 0.4F, (float)d2 + 0.45F);
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 2:
         GL11.glTranslatef((float)d0 + 0.55F, (float)d1 + 0.4F, (float)d2 + 0.7F);
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(360.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 3:
         GL11.glTranslatef((float)d0 + 0.4F, (float)d1 + 0.4F, (float)d2 + 0.55F);
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         break;
      default:
         GL11.glTranslatef((float)d0 + 0.45F, (float)d1 + 0.4F, (float)d2 + 0.4F);
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      }

      this.bindTextureByName("/mods/radioblock/textures/blocks/radioentity.png");
      this.model.render(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F);
      GL11.glPopMatrix();
   }
}
