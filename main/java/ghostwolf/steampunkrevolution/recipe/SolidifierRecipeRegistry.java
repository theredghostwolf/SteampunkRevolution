package ghostwolf.steampunkrevolution.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SolidifierRecipeRegistry {
	
private static HashMap<SolidifierRecipe, String> REGISTRY = new HashMap<SolidifierRecipe, String>();
	
	public static SolidifierRecipe findRecipeForStack(FluidStack stack){
		for(SolidifierRecipe rec : REGISTRY.keySet()){
			if(rec.isValid(stack))return rec;
		}
		return null;
	}
	
	public static SolidifierRecipe registerRecipe(String mod_id, SolidifierRecipe recipe){
		for(SolidifierRecipe rec : REGISTRY.keySet()){
			if(ItemStack.areItemsEqual(rec.getItem(), recipe.getItem())){
				SteampunkRevolutionMod.logger.info("Mod " + mod_id + " is overritting a Solidifier recipe for " + recipe.getItem().getUnlocalizedName() + " from " + REGISTRY.get(rec));
				REGISTRY.remove(rec);
				break;
			}
		}
		if(recipe.fluidCost.amount > 0){
			REGISTRY.put(recipe, mod_id);
		}
		return recipe;
	}
	
	public static List<SolidifierRecipe> getRecipes(){
		Set<SolidifierRecipe> recipesSet = ((HashMap<SolidifierRecipe, String>)REGISTRY.clone()).keySet();
		List<SolidifierRecipe> recipes = new ArrayList<SolidifierRecipe>(recipesSet);
		Collections.sort(recipes, new Comparator<SolidifierRecipe>() {

			@Override
			public int compare(SolidifierRecipe o1, SolidifierRecipe o2) {
				int c1 = o1.getCost().amount;
				int c2 = o2.getCost().amount;
				return c1 == c2 ? 0 : c1 < c2 ? -1 : 1;
			}
		});
		return recipes;
}

}
