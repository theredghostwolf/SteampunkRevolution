package ghostwolf.steampunkrevolution.keybinds;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketOpenCosmeticGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class InputHandler {
	
	   @SubscribeEvent
	    public void onKeyInput(InputEvent.KeyInputEvent event) {
	        if (KeyBindings.CosmeticArmorGuiKey.isPressed()) {
	        	PacketHandler.INSTANCE.sendToServer(new PacketOpenCosmeticGui());
	        }
	    }

}
