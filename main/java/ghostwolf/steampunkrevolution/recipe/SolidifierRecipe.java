package ghostwolf.steampunkrevolution.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class SolidifierRecipe {
	
	protected final FluidStack fluidCost;
	protected final ItemStack item;
	
	public SolidifierRecipe (FluidStack f, ItemStack i) {
		this.fluidCost = f;
		this.item = i;
	}
	
	public boolean isValid(FluidStack stack){
		if (stack != null) {
			if (stack.getFluid() == fluidCost.getFluid() && stack.amount >= fluidCost.amount) {
				return true;
			}
		}
		return false;
	}
	
	public FluidStack getCost(){
		return fluidCost;
	}
	
	public ItemStack outputItemStack(){
		return item.copy();
	}
	
	public ItemStack getItem(){
		return item;
}
}
