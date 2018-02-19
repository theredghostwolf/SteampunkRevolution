package ghostwolf.steampunkrevolution.gui;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.containers.ContainerOven;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSteamOven extends GuiContainer {
	
    private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/testcontainer.png");
	
	 public static final int WIDTH = 180;
	    public static final int HEIGHT = 152;

	public GuiSteamOven(TileEntitySteamOven tileEntity,ContainerOven container) {
		super(container);
		// TODO Auto-generated constructor stub
		  xSize = WIDTH;
	       ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
	     mc.getTextureManager().bindTexture(background);
	      drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
