package net.reodont.radioblock;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRadio extends ModelBase {

   ModelRenderer Body;
   ModelRenderer Speaker_Right;
   ModelRenderer Speaker_Left;
   ModelRenderer HandleBaseRight;
   ModelRenderer HandleBaseLeft;
   ModelRenderer HandleBar;
   ModelRenderer Antenna;


   public ModelRadio() {
      super.textureWidth = 32;
      super.textureHeight = 16;
      this.Body = new ModelRenderer(this, 0, 9);
      this.Body.addBox(0.0F, 0.0F, 0.0F, 9, 4, 3);
      this.Body.setRotationPoint(-5.0F, 0.0F, 0.0F);
      this.Body.setTextureSize(32, 16);
      this.Body.mirror = true;
      this.setRotation(this.Body, 0.0F, 0.0F, 0.0F);
      this.Speaker_Right = new ModelRenderer(this, 7, 0);
      this.Speaker_Right.mirror = true;
      this.Speaker_Right.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
      this.Speaker_Right.setRotationPoint(1.0F, 1.0F, -0.5333334F);
      this.Speaker_Right.setTextureSize(32, 16);
      this.Speaker_Right.mirror = true;
      this.setRotation(this.Speaker_Right, 0.0F, 0.0F, 0.0F);
      this.Speaker_Right.mirror = false;
      this.Speaker_Left = new ModelRenderer(this, 1, 0);
      this.Speaker_Left.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
      this.Speaker_Left.setRotationPoint(-4.0F, 1.0F, -0.5F);
      this.Speaker_Left.setTextureSize(32, 16);
      this.Speaker_Left.mirror = true;
      this.setRotation(this.Speaker_Left, 0.0F, 0.0F, 0.0F);
      this.HandleBaseRight = new ModelRenderer(this, 0, 0);
      this.HandleBaseRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.HandleBaseRight.setRotationPoint(1.0F, -1.0F, 1.0F);
      this.HandleBaseRight.setTextureSize(32, 16);
      this.HandleBaseRight.mirror = true;
      this.setRotation(this.HandleBaseRight, 0.0F, 0.0F, 0.0F);
      this.HandleBaseLeft = new ModelRenderer(this, 0, 0);
      this.HandleBaseLeft.addBox(0.0F, 0.0F, -1.0F, 1, 1, 1);
      this.HandleBaseLeft.setRotationPoint(-3.0F, -1.0F, 2.0F);
      this.HandleBaseLeft.setTextureSize(32, 16);
      this.HandleBaseLeft.mirror = true;
      this.setRotation(this.HandleBaseLeft, 0.0F, 0.0F, 0.0F);
      this.HandleBar = new ModelRenderer(this, 0, 0);
      this.HandleBar.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.HandleBar.setRotationPoint(-3.0F, -2.0F, 1.0F);
      this.HandleBar.setTextureSize(32, 16);
      this.HandleBar.mirror = true;
      this.setRotation(this.HandleBar, 0.0F, 0.0F, 0.0F);
      this.Antenna = new ModelRenderer(this, 26, 0);
      this.Antenna.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1);
      this.Antenna.setRotationPoint(-1.0F, -3.2F, 1.8F);
      this.Antenna.setTextureSize(32, 16);
      this.Antenna.mirror = true;
      this.setRotation(this.Antenna, 0.0F, 0.0F, -0.6457718F);
   }

   public void render(float f, float f1, float f2, float f3, float f4, float f5) {
      this.Body.render(f5);
      this.Speaker_Right.render(f5);
      this.Speaker_Left.render(f5);
      this.HandleBaseRight.render(f5);
      this.HandleBaseLeft.render(f5);
      this.HandleBar.render(f5);
      this.Antenna.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
   }
}
