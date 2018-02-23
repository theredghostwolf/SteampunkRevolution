package ghostwolf.steampunkrevolution.gui;

import ghostwolf.steampunkrevolution.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;


public class GuiCosmeticArmor extends GuiContainer {

	private float oldMouseX;
	private float oldMouseY;
	
	public GuiCosmeticArmor(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	      this.oldMouseX = (float)mouseX;
	      this.oldMouseY = (float)mouseY;
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		 	mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/cosmeticarmorgui.png"));
		   drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 166 );
		   int i = this.guiLeft;
	        int j = this.guiTop;
		   GuiInventory.drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.mc.player );
	}
	
 
	

	
	

}
