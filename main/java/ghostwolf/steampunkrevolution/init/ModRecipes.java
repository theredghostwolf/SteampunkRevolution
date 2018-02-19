package ghostwolf.steampunkrevolution.init;

import ghostwolf.steampunkrevolution.blocks.BlockOre;
import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import ghostwolf.steampunkrevolution.items.ItemMaterial;
import ghostwolf.steampunkrevolution.recipe.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	public static void init () {
		RecipeHelper.addShaped(ModBlocks.raintank, 3, 1, Blocks.PLANKS, Blocks.AIR ,Blocks.PLANKS);
		initSmeltingRecipes();
	}
	
	private static void initSmeltingRecipes () {

	}
}
