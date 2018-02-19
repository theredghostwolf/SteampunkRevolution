package ghostwolf.steampunkrevolution.tesr;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.tileentities.TileEntityDisplay;
import ghostwolf.steampunkrevolution.tileentities.TileEntityPedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class DisplayTesr extends TileEntitySpecialRenderer<TileEntityDisplay>{
	
	  @Override
	    public void render(TileEntityDisplay tile, double x, double y, double z, float par5, int par6, float f){
	        if(!(tile instanceof TileEntityDisplay)){
	            return;
	        }

	        ItemStack stack = ((TileEntityDisplay)tile).getStack();
	        if(! stack.isEmpty()){
	            GlStateManager.pushMatrix();
	            GlStateManager.translate((float)x+0.5F, (float)y+1.2F, (float)z+0.5F);

	            double boop = Minecraft.getSystemTime()/800D;
	            GlStateManager.translate(0D, Math.sin(boop%(2*Math.PI))*0.065, 0D);
	            GlStateManager.rotate((float)(((boop*40D)%360)), 0, 1, 0);

	            float scale = stack.getItem() instanceof ItemBlock ? 0.45F : 0.65F;
	            GlStateManager.scale(scale, scale, scale);
	            try{
	            	  Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
	            }
	            catch(Exception e){
	                SteampunkRevolutionMod.logger.error("Something went wrong trying to render an item in a Display! The item is "+stack.getItem().getRegistryName()+"!", e);
	            }

	            GlStateManager.popMatrix();
	        }
	}
	
}
