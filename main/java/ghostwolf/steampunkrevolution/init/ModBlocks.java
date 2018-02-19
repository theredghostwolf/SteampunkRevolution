package ghostwolf.steampunkrevolution.init;


import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.blocks.BlockAltar;
import ghostwolf.steampunkrevolution.blocks.BlockBoiler;
import ghostwolf.steampunkrevolution.blocks.BlockFluidLoader;
import ghostwolf.steampunkrevolution.blocks.BlockFluidUnloader;
import ghostwolf.steampunkrevolution.blocks.BlockLoader;
import ghostwolf.steampunkrevolution.blocks.BlockMetal;
import ghostwolf.steampunkrevolution.blocks.BlockOre;
import ghostwolf.steampunkrevolution.blocks.BlockPedestal;
import ghostwolf.steampunkrevolution.blocks.BlockRainTank;
import ghostwolf.steampunkrevolution.blocks.BlockResinExtractor;
import ghostwolf.steampunkrevolution.blocks.BlockResinSolidifier;
import ghostwolf.steampunkrevolution.blocks.BlockSteamCrusher;
import ghostwolf.steampunkrevolution.blocks.BlockSteamFurnace;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import ghostwolf.steampunkrevolution.blocks.BlockUnloader;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":boiler")
	public static BlockBoiler boiler;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":raintank")
	public static BlockRainTank raintank;
	
	/*
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":crusher")
	public static BlockSteamCrusher crusher;
	*/
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":resinextractor")
	public static BlockResinExtractor resinextractor;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":resinsolidifier")
	public static BlockResinSolidifier resinsolidifier;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":steamfurnace")
	public static BlockSteamFurnace steamfurnace;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":oven")
	public static BlockSteamOven oven;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":unloader")
	public static BlockUnloader unloader;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":loader")
	public static BlockLoader loader;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":fluidloader")
	public static BlockFluidLoader fluidloader;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":fluidunloader")
	public static BlockFluidUnloader fluidunloader;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":ore")
	public static BlockOre ore;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":metalBlock")
	public static BlockMetal metalBlock;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":pedestal")
	public static BlockPedestal pedestal;
	
	@GameRegistry.ObjectHolder(Reference.MOD_ID + ":altar")
	public static BlockAltar altar;
	
	
	
	
	public static void init () {
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels () {
		oven.initModel();
		fluidunloader.initModel();
		raintank.initModel();
		boiler.initModel();
		resinextractor.initModel();
		resinsolidifier.initModel();
		steamfurnace.initModel();
		unloader.initModel();
		loader.initModel();
		fluidloader.initModel();
		ore.initModel();
		pedestal.initModel();
		altar.initModel();
		metalBlock.initModel();
		//crusher.initModel();
	}
	
	public static void initOreDict (){
		ore.initOreDict();
		metalBlock.initOreDict();
	}

}
