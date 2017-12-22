package ghostwolf.steampunkrevolution.event;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.recipe.SolidifierRecipe;
import ghostwolf.steampunkrevolution.recipe.SolidifierRecipeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventRegisterSolidifierRecipe extends Event {
	
	public void registerRecipe(String modid, ItemStack item, FluidStack cost){
	   	 SolidifierRecipeRegistry.registerRecipe(modid, new SolidifierRecipe(cost, item));
	}
	
	public void registerRecipe(String modid, Fluid fluid, int fluidamount, Item item, int amount, int meta){
		
	   	 SolidifierRecipeRegistry.registerRecipe(modid, new SolidifierRecipe(fluid, fluidamount, item, amount, meta));
	}
}
