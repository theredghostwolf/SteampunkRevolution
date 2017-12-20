package ghostwolf.steampunkrevolution.render;

import java.util.List;

import javax.net.ssl.HostnameVerifier;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.util.AccessPoint;
import ghostwolf.steampunkrevolution.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@Mod.EventBusSubscriber(Side.CLIENT)
public class RenderWorldLastEventHandler {
	
	private static InventoryHelper invHelper = new InventoryHelper();
	
	private static float thickness = 0.02F;
	
	@SubscribeEvent
	 public static void tick(RenderWorldLastEvent evt) {
	  Minecraft mc = Minecraft.getMinecraft();
	  EntityPlayerSP player = mc.player;
	  ItemStack heldItem = player.getHeldItemMainhand();
	  if (heldItem.getItem() == ModItems.robotwrench) {
		  Entity e = mc.world.getEntityByID(ModItems.robotwrench.getRobot(heldItem));
		  if (e != null && e instanceof EntityRobot) {
			  EntityRobot bot =  (EntityRobot) e;
			  switch (ModItems.robotwrench.getMode(heldItem)) {
			case 0:
				//extract
				//SteampunkRevolutionMod.logger.log(Level.INFO, bot.ExtractPoints.size());
				renderBoxes(bot.ExtractPoints, mc, evt, 1, 0.01F, 0.01F, 0.5F, bot);
				break;
			case 1:
				//SteampunkRevolutionMod.logger.log(Level.INFO, bot.InsertPoints.size());
				renderBoxes(bot.InsertPoints, mc, evt, 0.01F, 0.01F, 1, 0.5F, bot);
				//insert
				break;
			case 2:
				//SteampunkRevolutionMod.logger.log(Level.INFO, bot.refuelPoints.size());
				renderBoxes(bot.refuelPoints, mc, evt, 0.01F, 1F, 0.01F, 0.5F, bot);
				//fuel
				break;
			case 3:
				renderHomePos(bot, mc, evt);
				break;
			}
		  }
	  }
	  if (player.inventory.armorInventory.get(3).getItem() == ModItems.brassgoggles && heldItem.getItem() != ModItems.robotwrench) {
		  if (player.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
		  IItemHandler pInv = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		  if (pInv != null && invHelper.inventoryContainsItem(pInv, ModItems.robotwrench)) {
			  
			 ItemStack wrench = invHelper.inventoryGetItem(pInv, ModItems.robotwrench);
			 if (! wrench.isEmpty() && wrench != null) {
				 Entity e = mc.world.getEntityByID(ModItems.robotwrench.getRobot(wrench));
				 if (e != null && e instanceof EntityRobot) {
					  EntityRobot bot =  (EntityRobot) e;
						renderBoxes(bot.refuelPoints, mc, evt, 0.01F, 1F, 0.01F, 0.5F, bot);
						renderBoxes(bot.ExtractPoints, mc, evt, 1, 0.01F, 0.01F, 0.5F, bot);
						renderBoxes(bot.InsertPoints, mc, evt, 0.01F, 0.01F, 1, 0.5F, bot);
						renderHomePos(bot,mc, evt);
				 }
			 }
			  
		  }
	  	}
	  }
	 }
	
	 private static void renderHomePos (EntityRobot robot, Minecraft mc, RenderWorldLastEvent evt) {
		 BlockPos home = robot.getHomePosition();
		if (home != null && home != BlockPos.ORIGIN) {	
			Entity entity = mc.getRenderViewEntity();
			//Interpolating everything back to 0,0,0. These are transforms you can find at RenderEntity class
			double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)evt.getPartialTicks();
			double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)evt.getPartialTicks();
			double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)evt.getPartialTicks();
			//Apply 0-our transforms to set everything back to 0,0,0
			Tessellator.getInstance().getBuffer().setTranslation(-d0, -d1, -d2);
			GlStateManager.disableTexture2D();

			GlStateManager.disableDepth();
			GlStateManager.enableBlend();
			renderBoxOutline(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), home.getX(), home.getY(), home.getZ(), home.getX() + 1, home.getY() + 1, home.getZ() + 1, 0.8F, 0.8F, 0.5F, 0.5F, 3);
		 
			Tessellator.getInstance().getBuffer().setTranslation(0, 0, 0);
			GlStateManager.enableDepth();
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
		}
	 }
	 
	
	private static void renderBoxes (List<AccessPoint> l, Minecraft mc, RenderWorldLastEvent evt, float red, float green, float blue, float alpha, EntityRobot bot ) {
		if (l != null) {
			Entity entity = mc.getRenderViewEntity();
			//Interpolating everything back to 0,0,0. These are transforms you can find at RenderEntity class
			double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)evt.getPartialTicks();
			double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)evt.getPartialTicks();
			double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)evt.getPartialTicks();
			//Apply 0-our transforms to set everything back to 0,0,0
			Tessellator.getInstance().getBuffer().setTranslation(-d0, -d1, -d2);
			//Your render function which renders boxes at a desired position. In this example I just copy-pasted the one on TileEntityStructureRenderer
			GlStateManager.disableTexture2D();

			GlStateManager.disableDepth();
			GlStateManager.enableBlend();
			for (int i = 0; i < l.size(); i++) {
			BlockPos p = l.get(i).pos;
			
			
			renderBox(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), p.getX() - thickness, p.getY() - thickness, p.getZ() - thickness , p.getX() + 1 + thickness , p.getY() + 1 + thickness , p.getZ() + 1 + thickness, red, green, blue, alpha, l.get(i).facing);
			//When you are done rendering all your boxes reset the offsets. We do not want everything that renders next to still be at 0,0,0 :)

			renderBoxOutline(Tessellator.getInstance(),Tessellator.getInstance().getBuffer(), p.getX() - thickness, p.getY() - thickness, p.getZ() - thickness , p.getX() + 1 + thickness , p.getY() + 1 + thickness , p.getZ() + 1 + thickness, red, green, blue, alpha, 3);
			
			
			
		}
		Tessellator.getInstance().getBuffer().setTranslation(0, 0, 0);
		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		}
	}
	
	 private static void renderBox(Tessellator tessellator, BufferBuilder b, float x, float y, float z, float x2, float y2, float z2, float red, float green, float blue, float alpha, EnumFacing facing)
	    {
	       
	       
	        if (facing == EnumFacing.DOWN) {
		        renderBoxOutline(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), x, y, z, x2 , y + thickness, z2, red, green, blue, alpha, 7);
	        }
	        if (facing == EnumFacing.UP) {
		        renderBoxOutline(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), x, y2, z, x2 , y2 - thickness, z2, red, green, blue, alpha, 7);
	        }
			
			//north
	        if (facing == EnumFacing.NORTH) {
		        renderBoxOutline(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), x, y, z, x2 , y2, z - thickness, red, green, blue, alpha, 7);
	        }
			//south
	        if (facing == EnumFacing.SOUTH) {
		        renderBoxOutline(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), x, y, z2, x2 , y2, z2 + thickness, red, green, blue, alpha, 7);

	        }
			
			//east
	        if (facing == EnumFacing.EAST) {
	      
		        renderBoxOutline(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), x2, y, z, x2 - thickness, y2, z2, red, green, blue, alpha, 7);

	        }
			//west
	        if (facing == EnumFacing.WEST) {
	        
		        renderBoxOutline(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), x, y, z, x + thickness, y2, z2, red, green, blue, alpha, 7);

	        }
	        
	        
	      
	       // SteampunkRevolutionMod.logger.log(Level.INFO, "finished drawing box");
	        GlStateManager.glLineWidth(1.0F);
	    }
	 
	 private static void renderBoxOutline (Tessellator t, BufferBuilder b, float x,float y,float z,float x1,float y1,float z1,float red,float green,float blue, float alpha, int mode) {
		 b.begin(mode, DefaultVertexFormats.POSITION_COLOR);
		 GlStateManager.glLineWidth(3F);
		 
		 //bottom
		 b.pos(x, y, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y, z).color(red, green, blue, alpha).endVertex();
		 
		 //side 1
		 b.pos(x, y1, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y1, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y, z).color(red, green, blue, alpha).endVertex();
		 
		 //side 2
		 b.pos(x, y1, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y1, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y, z).color(red, green, blue, alpha).endVertex();
		
		 //side 3
		 b.pos(x, y, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y1, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x, y, z1).color(red, green, blue, alpha).endVertex();
		 
		 //side 4
		 b.pos(x1, y, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y1, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y, z).color(red, green, blue, alpha).endVertex();
		 b.pos(x1, y, z1).color(red, green, blue, alpha).endVertex();
		 
		 
		//top
		b.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
		b.pos(x1, y1, z).color(red, green, blue, alpha).endVertex();
		b.pos(x, y1, z).color(red, green, blue, alpha).endVertex();
		b.pos(x, y1, z1).color(red, green, blue, alpha).endVertex();
		b.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
			 
		 
		 
		 t.draw();
	 }
	 
	
	
	

}
