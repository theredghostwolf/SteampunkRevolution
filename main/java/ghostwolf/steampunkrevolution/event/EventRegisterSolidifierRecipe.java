package ghostwolf.steampunkrevolution.event;

import ghostwolf.steampunkrevolution.recipe.SolidifierRecipe;
import ghostwolf.steampunkrevolution.recipe.SolidifierRecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventRegisterSolidifierRecipe extends Event {
	
	public void registerRecipe(String modid, ItemStack item, FluidStack cost){
	   	 SolidifierRecipeRegistry.registerRecipe(modid, new SolidifierRecipe(cost, item));
	}
}
