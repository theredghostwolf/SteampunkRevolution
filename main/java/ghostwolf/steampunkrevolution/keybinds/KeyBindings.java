package ghostwolf.steampunkrevolution.keybinds;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBindings {
	
	public static KeyBinding CosmeticArmorGuiKey;
	
	  public static void init() {
	        CosmeticArmorGuiKey = new KeyBinding("Key to open Cosmetic armor gui", Keyboard.KEY_C, "Steampunk revolution");
	        ClientRegistry.registerKeyBinding(CosmeticArmorGuiKey);
	    }

}
