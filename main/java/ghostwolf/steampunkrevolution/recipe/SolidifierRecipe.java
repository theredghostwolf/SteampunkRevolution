package ghostwolf.steampunkrevolution.recipe;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class SolidifierRecipe {
	
	protected final FluidStack fluidCost;
	protected final ItemStack item;
	
	public SolidifierRecipe (FluidStack f, ItemStack i) {
		this.fluidCost = f;
		this.item = i;
	}
	
	public SolidifierRecipe (Fluid f, int famount, Item i, int iamount, int m) {
		this.fluidCost = new FluidStack(f, famount);
		this.item = new ItemStack(i, iamount, m);
		SteampunkRevolutionMod.logger.log(Level.INFO, i);
		SteampunkRevolutionMod.logger.log(Level.INFO, iamount);
		SteampunkRevolutionMod.logger.log(Level.INFO, m);
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
