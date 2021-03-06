package ghostwolf.steampunkrevolution.recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.logging.log4j.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import ghostwolf.steampunkrevolution.enums.EnumMetalParts;
import ghostwolf.steampunkrevolution.enums.EnumMetals;
import ghostwolf.steampunkrevolution.event.EventRegisterSolidifierRecipe;
import ghostwolf.steampunkrevolution.init.ModBlocks;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class RegisterRecipes {
	
	 public static Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
	
	@SubscribeEvent()
	public static void registerSolidifierRecipes(EventRegisterSolidifierRecipe e) {
		SteampunkRevolutionMod.logger.log(Level.INFO, "registering solidifier recipes");
		e.registerRecipe(Reference.MOD_ID, new ItemStack(ModItems.material,1,EnumMaterial.driedResin.getMeta()), new FluidStack(FluidRegistry.getFluid("resin"), 100));
	}
	
	
	@SubscribeEvent()
	public static void registerCraftingRecipes (RegistryEvent.Register<IRecipe> e)  {
		
		RecipeHelper helper = new RecipeHelper();
		
		//arm 
		helper.addShaped(new ItemStack(ModItems.mechanoidpart,1,1), 3, 2,
				
					"stickCopper",
					Blocks.AIR,
					"stickCopper",
					Blocks.AIR,
					"tinygearCopper",
					Blocks.AIR
				);
		
		
		//raintank
		helper.addShaped(ModBlocks.raintank, 3, 3, "plankWood", Blocks.AIR, "plankWood","plankWood", Blocks.AIR, "plankWood","plankWood", "slabWood", "plankWood");
		
		//alloys
		helper.addShapeless(new ItemStack(ModItems.metal,2,ModItems.metal.getIdFor(EnumMetals.Bronze, EnumMetalParts.Ingot)),
				"ingotCopper",
				"ingotTin"
				);
		
		helper.addShapeless(new ItemStack(ModItems.metal,2,ModItems.metal.getIdFor(EnumMetals.Brass, EnumMetalParts.Ingot)),
				"ingotCopper",
				"ingotZinc"
				);
		
		helper.addShapeless(new ItemStack(ModItems.metal,2,ModItems.metal.getIdFor(EnumMetals.Steel, EnumMetalParts.Ingot)),
				"coal",
				"coal",
				"ingotIron"
		);
		
		helper.addShapeless(new ItemStack(ModItems.metal,2,ModItems.metal.getIdFor(EnumMetals.Blacksteel, EnumMetalParts.Ingot)),
				"ingotSteel",
				"ingotTungsten"
			);
		
		helper.addShapeless(new ItemStack(ModItems.metal,2,ModItems.metal.getIdFor(EnumMetals.Electrum, EnumMetalParts.Ingot)),
				"ingotSilver",
				"ingotGold"
		);
		
		for (EnumMetals m : EnumMetals.values()) {
			for (EnumMetalParts p : EnumMetalParts.values()) {
				switch (p) {
				case Plate:
					helper.addShaped(new ItemStack(ModItems.metal,4,ModItems.metal.getIdFor(m, p)), 2, 2,
							  "ingot" + m.getNameCapital(),
							  "ingot" + m.getNameCapital(),
							  "ingot" + m.getNameCapital(),
							  "ingot" + m.getNameCapital()
							);
					break;
				case Stick:
					helper.addShaped(new ItemStack(ModItems.metal,2,ModItems.metal.getIdFor(m, p)), 1, 2,
							"ingot" + m.getNameCapital(),
							"ingot" + m.getNameCapital()
							);
					break;
				case Nugget:
					helper.addShapeless(new ItemStack(ModItems.metal,9, ModItems.metal.getIdFor(m, p)), "ingot" + m.getNameCapital());
					break;
				case Ingot:
					helper.addShaped(new ItemStack(ModItems.metal,1,ModItems.metal.getIdFor(m, p)),3, 3,
							"nugget" + m.getNameCapital(),
							"nugget" + m.getNameCapital(),
							"nugget" + m.getNameCapital(),
							
							"nugget" + m.getNameCapital(),
							"nugget" + m.getNameCapital(),
							"nugget" + m.getNameCapital(),
							
							"nugget" + m.getNameCapital(),
							"nugget" + m.getNameCapital(),
							"nugget" + m.getNameCapital()
							);
					break;
				case Gear:
					helper.addShaped(new ItemStack(ModItems.metal,1,ModItems.metal.getIdFor(m, p)), 3, 3, 
							Blocks.AIR,
							"ingot" + m.getNameCapital(),
							Blocks.AIR,
							"ingot" + m.getNameCapital(),
							"ingotIron",
							"ingot" + m.getNameCapital(),
							Blocks.AIR,
							"ingot" + m.getNameCapital(),
							Blocks.AIR
							);
					break;
				case Tinygear:
					helper.addShaped(new ItemStack(ModItems.metal,1,ModItems.metal.getIdFor(m, p)), 3, 3, 
							Blocks.AIR,
							"nugget" + m.getNameCapital(),
							Blocks.AIR,
							"nugget" + m.getNameCapital(),
							"nuggetIron",
							"nugget" + m.getNameCapital(),
							Blocks.AIR,
							"nugget" + m.getNameCapital(),
							Blocks.AIR
							);
					break;
				default:
					break;
				}
			}
			//ingots to blocks
			helper.addShaped(new ItemStack(ModBlocks.metalBlock,1,m.getId()), 3, 3,
					"ingot" + m.getNameCapital(),
					"ingot" + m.getNameCapital(),
					"ingot" + m.getNameCapital(),
					
					"ingot" + m.getNameCapital(),
					"ingot" + m.getNameCapital(),
					"ingot" + m.getNameCapital(),
					
					"ingot" + m.getNameCapital(),
					"ingot" + m.getNameCapital(),
					"ingot" + m.getNameCapital()
			);
			if (m.hasOre()) {
				//ore recipes
				GameRegistry.addSmelting(new ItemStack(ModBlocks.ore,1,m.getId()),new ItemStack(ModItems.metal,1,ModItems.metal.getIdFor(m, EnumMetalParts.Ingot)) , 1);

			}
			
			//blocks to ingots
			helper.addShapeless(new ItemStack(ModItems.metal,9,ModItems.metal.getIdFor(m, EnumMetalParts.Ingot)), "block" + m.getNameCapital());
			//dust to ingot smelting recipe
			GameRegistry.addSmelting(new ItemStack(ModItems.metal,1,ModItems.metal.getIdFor(m, EnumMetalParts.Dust)), new ItemStack(ModItems.metal,1,ModItems.metal.getIdFor(m, EnumMetalParts.Ingot) ), 0);
			
		}
		
		for (IRecipe r : helper.RECIPES) {
			e.getRegistry().register(r);
		}
		
	}
	
	

	@SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerConfigRecipes(EventRegisterSolidifierRecipe e) {
		SteampunkRevolutionMod.logger.log(Level.INFO, "registering solidifier recipes from config");
		registerSolidifierRecipes(e);
	}
	
	private static void registerSolidifierRecipesFromConfig (EventRegisterSolidifierRecipe e) {
		File recipesFolder = new File(CommonProxy.configFolder, "SolidifierRecipes");
		if(!recipesFolder.exists())recipesFolder.mkdir();
		for(File recipeFile : recipesFolder.listFiles()){
			try {
				JsonObject obj = gson.fromJson(new FileReader(recipeFile), JsonObject.class);
				JsonArray array = obj.getAsJsonArray("recipes");
				if(array == null){
					SteampunkRevolutionMod.logger.error("Error reading recipe config file " + recipeFile.getName() + "! Invalid format!");
					continue;
				}
				for(int i = 0; i < array.size(); i++){
					if(array.get(i).isJsonObject()){
						JsonObject recipeDef = array.get(i).getAsJsonObject();
						if(recipeDef.get("item") == null || recipeDef.isJsonArray()){
							SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! No item specified! Skipping...");
							continue;
						}
						String itemS = recipeDef.get("item").getAsString();
						Item item = Item.REGISTRY.getObject(new ResourceLocation(itemS));
						if(item == null){
							SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! Invalid item! Skipping...");
							continue;
						}
						int meta = 0;
						if(recipeDef.has("meta")){
							if(!recipeDef.get("meta").isJsonPrimitive()){
								SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! Invalid meta! Skipping...");
								continue;
							}
							meta = recipeDef.get("meta").getAsInt();
						}
						int amount = 0;
						if(recipeDef.has("amount")){
							if(!recipeDef.get("amount").isJsonPrimitive()){
								SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! Invalid meta! Skipping...");
								continue;
							}
							amount = recipeDef.get("amount").getAsInt();
						}
						int cost = -1;
						if(recipeDef.has("cost")){
							if(!recipeDef.get("cost").isJsonPrimitive()){
								SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! Invalid cost! Skipping...");
								continue;
							}
							cost = recipeDef.get("cost").getAsInt();
						}
						String fluidS = "";
						Fluid fluid = null;
						if(recipeDef.has("fluid")){
							if(!recipeDef.get("fluid").isJsonPrimitive()){
								SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! Invalid cost! Skipping...");
								continue;
							}
							fluidS = recipeDef.get("fluid").getAsString();
							fluid = FluidRegistry.getFluid(fluidS);
							if(fluid == null){
								SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! Invalid item! Skipping...");
								continue;
							}
						}
						e.registerRecipe("minecraft", fluid, cost, item, amount, meta);
						SteampunkRevolutionMod.logger.log(Level.INFO, "added recipe from config for solidifier with item: " + itemS);
					}else{
						SteampunkRevolutionMod.logger.error("Error reading recipe #"+(i+1)+" in config file " + recipeFile.getName() + "! Not an object! Skipping...");
					}
				}
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}
