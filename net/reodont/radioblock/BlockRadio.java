package net.reodont.radioblock;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.reodont.radioblock.TileEntityRadio;
import net.reodont.radioblock.gui.GuiRadio;

public class BlockRadio extends Block implements ITileEntityProvider {

   public BlockRadio(int par1, Material par2Material) {
      super(par1, par2Material);
      this.setHardness(2.0F);
      this.setResistance(10.0F);
      this.setStepSound(Block.soundStoneFootstep);
      this.setUnlocalizedName("Радио");
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public void registerIcons(IconRegister ir) {
      super.blockIcon = ir.registerIcon("radioblock:radio");
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if(side == Side.CLIENT) {
         this.openGUI(par1World, par2, par3, par4);
      }

      return true;
   }

   @SideOnly(Side.CLIENT)
   private void openGUI(World par1World, int par2, int par3, int par4) {
      TileEntityRadio ter = (TileEntityRadio)par1World.getBlockTileEntity(par2, par3, par4);
      Minecraft.getMinecraft().displayGuiScreen(new GuiRadio(ter));
   }

   public TileEntity createNewTileEntity(World world) {
      return new TileEntityRadio();
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return 189;
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      switch(par1IBlockAccess.getBlockTileEntity(par2, par3, par4).getBlockMetadata()) {
      case 1:
         this.setBlockBounds(0.4F, 0.5F, 0.0F, 0.7F, 0.0F, 1.0F);
         break;
      case 2:
         this.setBlockBounds(0.0F, 0.0F, 0.4F, 1.0F, 0.5F, 0.7F);
         break;
      case 3:
         this.setBlockBounds(0.4F, 0.5F, 0.0F, 0.7F, 0.0F, 1.0F);
         break;
      default:
         this.setBlockBounds(0.0F, 0.0F, 0.4F, 1.0F, 0.5F, 0.7F);
      }

   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack) {
      int dir = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      par1World.setBlockMetadataWithNotify(par2, par3, par4, dir, 0);
   }
}
