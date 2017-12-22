package ghostwolf.steampunkrevolution.proxy;

import java.io.File;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.blocks.BlockBoiler;
import ghostwolf.steampunkrevolution.blocks.BlockFluidLoader;
import ghostwolf.steampunkrevolution.blocks.BlockFluidUnloader;
import ghostwolf.steampunkrevolution.blocks.BlockLoader;
import ghostwolf.steampunkrevolution.blocks.BlockOre;
import ghostwolf.steampunkrevolution.blocks.BlockRainTank;
import ghostwolf.steampunkrevolution.blocks.BlockResinExtractor;
import ghostwolf.steampunkrevolution.blocks.BlockResinSolidifier;
import ghostwolf.steampunkrevolution.blocks.BlockSteamCrusher;
import ghostwolf.steampunkrevolution.blocks.BlockSteamFurnace;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import ghostwolf.steampunkrevolution.blocks.BlockUnloader;
import ghostwolf.steampunkrevolution.event.EventRegisterSolidifierRecipe;
import ghostwolf.steampunkrevolution.init.ModBlocks;
import ghostwolf.steampunkrevolution.init.ModCapabilities;
import ghostwolf.steampunkrevolution.init.ModEntities;
import ghostwolf.steampunkrevolution.init.ModFluids;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.init.ModRecipes;
import ghostwolf.steampunkrevolution.init.ModTileEntities;
import ghostwolf.steampunkrevolution.init.ModWorldgen;
import ghostwolf.steampunkrevolution.items.ItemBrassGoggles;
import ghostwolf.steampunkrevolution.items.ItemCart;
import ghostwolf.steampunkrevolution.items.ItemMaterial;
import ghostwolf.steampunkrevolution.items.ItemMetaBlock;
import ghostwolf.steampunkrevolution.items.ItemRobotWrench;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.render.RenderWorldLastEventHandler;
import ghostwolf.steampunkrevolution.tileentities.TileEntityBoiler;
import ghostwolf.steampunkrevolution.tileentities.TileEntityFluidLoader;
import ghostwolf.steampunkrevolution.tileentities.TileEntityFluidUnloader;
import ghostwolf.steampunkrevolution.tileentities.TileEntityLoader;
import ghostwolf.steampunkrevolution.tileentities.TileEntityRainTank;
import ghostwolf.steampunkrevolution.tileentities.TileEntityResinExtractor;
import ghostwolf.steampunkrevolution.tileentities.TileEntityResinSolidifier;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamCrusher;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamFurnace;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamOven;
import ghostwolf.steampunkrevolution.tileentities.TileEntityUnloader;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityTracker;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.BiomeEvent.GetWaterColor;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class CommonProxy {
	
		public static Configuration config;
		public static File configFolder;
	
	
	    public void preInit(FMLPreInitializationEvent e) {
	    	configFolder = new File(e.getSuggestedConfigurationFile().getParentFile(), Reference.MOD_ID);
	        config = new Configuration(new File(configFolder, Reference.MOD_ID + ".cfg"), Reference.Version, true);
	        Config.readConfig();
			PacketHandler.registerMessages(Reference.MOD_ID.toLowerCase());
			ModEntities.init();
	    }
	    
	  

	    public void init(FMLInitializationEvent e) {
	    	ModFluids.init();
	    	ModBlocks.initOreDict();
	    	ModItems.initOreDict();
	    	MinecraftForge.EVENT_BUS.post(new EventRegisterSolidifierRecipe());
	    }

	    public void postInit(FMLPostInitializationEvent e) {
	    	  if (config.hasChanged()) {
	              config.save();
	          }
	    }

	    @SubscribeEvent
	    public static void registerBlocks(RegistryEvent.Register<Block> event) {
	    	   event.getRegistry().register(new BlockBoiler());
		       GameRegistry.registerTileEntity(TileEntityBoiler.class, Reference.MOD_ID + ":TileEntityBoiler");
		        
		        event.getRegistry().register(new BlockRainTank());
		        GameRegistry.registerTileEntity(TileEntityRainTank.class, Reference.MOD_ID + ":TileEntityRainTank");
		        
		        /*
		        event.getRegistry().register(new BlockSteamCrusher());
		        GameRegistry.registerTileEntity(TileEntitySteamCrusher.class, Reference.MOD_ID + ":TileEntitySteamCrusher");
		   		*/
		        
		        event.getRegistry().register(new BlockResinExtractor());
		        GameRegistry.registerTileEntity(TileEntityResinExtractor.class, Reference.MOD_ID + ":TileEntityResinExtractor");
		   
		        event.getRegistry().register(new BlockResinSolidifier());
		        GameRegistry.registerTileEntity(TileEntityResinSolidifier.class, Reference.MOD_ID + ":TileEntityResinSolidifier");
		        
		        event.getRegistry().register(new BlockSteamFurnace());
		        GameRegistry.registerTileEntity(TileEntitySteamFurnace.class, Reference.MOD_ID + ":TileEntitySteamFurnace");
		        
		        event.getRegistry().register(new BlockSteamOven());
		        GameRegistry.registerTileEntity(TileEntitySteamOven.class, Reference.MOD_ID + ":TileEntitySteamOven");
		        
		        event.getRegistry().register(new BlockUnloader());
		        GameRegistry.registerTileEntity(TileEntityUnloader.class, Reference.MOD_ID + ":TileEntityUnloader");
		        
		        event.getRegistry().register(new BlockLoader());
		        GameRegistry.registerTileEntity(TileEntityLoader.class, Reference.MOD_ID + ":TileEntityLoader");
		        
		        event.getRegistry().register(new BlockFluidLoader());
		        GameRegistry.registerTileEntity(TileEntityFluidLoader.class, Reference.MOD_ID + ":TileEntityFluidLoader");
		        
		        event.getRegistry().register(new BlockFluidUnloader());
		        GameRegistry.registerTileEntity(TileEntityFluidUnloader.class, Reference.MOD_ID + ":TileEntityFluidUnloader");
		        
		        event.getRegistry().register(new BlockOre());
	    }

	    @SubscribeEvent
	    public static void registerItems(RegistryEvent.Register<Item> event) {
	    	event.getRegistry().register(new ItemBlock(ModBlocks.boiler).setRegistryName(ModBlocks.boiler.getRegistryName()));
	        
	        event.getRegistry().register(new ItemBlock(ModBlocks.raintank).setRegistryName(ModBlocks.raintank.getRegistryName()));
	        
	        //event.getRegistry().register(new ItemBlock(ModBlocks.crusher).setRegistryName(ModBlocks.crusher.getRegistryName()));
	 
	        event.getRegistry().register(new ItemBlock(ModBlocks.oven).setRegistryName(ModBlocks.oven.getRegistryName()));
	   	 	
	        event.getRegistry().register(new ItemBlock(ModBlocks.resinextractor).setRegistryName(ModBlocks.resinextractor.getRegistryName()));
		   	 
	        event.getRegistry().register(new ItemBlock(ModBlocks.resinsolidifier).setRegistryName(ModBlocks.resinsolidifier.getRegistryName()));
		 
	        event.getRegistry().register(new ItemBlock(ModBlocks.steamfurnace).setRegistryName(ModBlocks.steamfurnace.getRegistryName()));
		   	 
	        event.getRegistry().register(new ItemBlock(ModBlocks.unloader).setRegistryName(ModBlocks.unloader.getRegistryName()));
		   	 
	        event.getRegistry().register(new ItemBlock(ModBlocks.loader).setRegistryName(ModBlocks.loader.getRegistryName()));
		   	 
	        event.getRegistry().register(new ItemBlock(ModBlocks.fluidloader).setRegistryName(ModBlocks.fluidloader.getRegistryName()));
		   	 
	        event.getRegistry().register(new ItemBlock(ModBlocks.fluidunloader).setRegistryName(ModBlocks.fluidunloader.getRegistryName()));
	        
	        event.getRegistry().register(new ItemRobotWrench());
	        
	        event.getRegistry().register(new ItemMetaBlock(ModBlocks.ore).setRegistryName(ModBlocks.ore.getRegistryName()));
	    
	        event.getRegistry().register(new ItemMaterial());
	        
	        event.getRegistry().register(new ItemCart());
	    
	        event.getRegistry().register(new ItemBrassGoggles());
	    }
	
	

}
