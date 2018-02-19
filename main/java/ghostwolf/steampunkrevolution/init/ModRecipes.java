package ghostwolf.steampunkrevolution.init;

import ghostwolf.steampunkrevolution.blocks.BlockOre;
import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import ghostwolf.steampunkrevolution.enums.EnumMetalParts;
import ghostwolf.steampunkrevolution.enums.EnumMetals;
import ghostwolf.steampunkrevolution.items.ItemMaterial;
import ghostwolf.steampunkrevolution.recipe.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	public static void init () {
		initSmeltingRecipes();
	}
	
	private static void initSmeltingRecipes () {
		for (EnumMetals m : EnumMetals.values()) {
		}
	}
}
