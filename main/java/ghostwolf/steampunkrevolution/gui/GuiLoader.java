package ghostwolf.steampunkrevolution.gui;

import java.io.IOException;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSetLoaderConfig;
import ghostwolf.steampunkrevolution.tileentities.TileEntityLoader;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import scala.swing.Action;
import scala.swing.Button;

public class GuiLoader extends GuiContainer {
	
	private static final ResourceLocation background = new ResourceLocation("minecraft", "textures/gui/container/dispenser.png");
		
	public static final int WIDTH = 180;
	public static final int HEIGHT = 170;
	
	private TileEntityLoader loaderEntity;
	
	public GuiLoader(TileEntityLoader loader, Container inventorySlotsIn) {
		super(inventorySlotsIn);
		  xSize = WIDTH;
	      ySize = HEIGHT;
	      this.loaderEntity = loader;
	}
	
	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		
		buttonList.add(new GuiButton(1, 300,80, 10, 10, "test"));
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		   mc.getTextureManager().bindTexture(background);
		   drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
			if (button.id == 1) {
				PacketHandler.INSTANCE.sendToServer(new PacketSetLoaderConfig(this.loaderEntity.getPos().getX(),this.loaderEntity.getPos().getY(),this.loaderEntity.getPos().getZ(), !this.loaderEntity.emitWhenLoaderEmpty, this.loaderEntity.emitWhenCartFull, true ));
			}
	}
	

	


}
