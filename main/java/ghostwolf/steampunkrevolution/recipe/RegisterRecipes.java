package ghostwolf.steampunkrevolution.recipe;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import ghostwolf.steampunkrevolution.event.EventRegisterSolidifierRecipe;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegisterRecipes {
	
	@SubscribeEvent()
	public static void registerRecipes(EventRegisterSolidifierRecipe e) {
		SteampunkRevolutionMod.logger.log(Level.INFO, "registering solidifier recipes");
		e.registerRecipe(Reference.MOD_ID, new ItemStack(ModItems.material,1,EnumMaterial.driedResin.getMeta()), new FluidStack(FluidRegistry.getFluid("resin"), 100));
	}
	
}
