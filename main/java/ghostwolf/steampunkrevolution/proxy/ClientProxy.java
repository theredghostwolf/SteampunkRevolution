package ghostwolf.steampunkrevolution.proxy;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import ghostwolf.steampunkrevolution.colors.IBlockMetalColor;
import ghostwolf.steampunkrevolution.colors.IItemBlockMetalColor;
import ghostwolf.steampunkrevolution.colors.IMaterialColor;
import ghostwolf.steampunkrevolution.colors.IMetalColor;
import ghostwolf.steampunkrevolution.init.ModBlocks;
import ghostwolf.steampunkrevolution.init.ModEntities;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.items.ItemMaterial;
import ghostwolf.steampunkrevolution.render.RenderWorldLastEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;


@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	   @Override
	    public void preInit(FMLPreInitializationEvent e) {
		 
	        super.preInit(e);
	    }
	   
	   @Override
	public void init(FMLInitializationEvent e) {
		ModEntities.initModels();
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IMaterialColor(), ModItems.material);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IMetalColor(), ModItems.metal);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemBlockMetalColor(), ItemBlock.getItemFromBlock(ModBlocks.metalBlock));
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemBlockMetalColor(), ItemBlock.getItemFromBlock(ModBlocks.ore));
		
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockMetalColor(), ModBlocks.metalBlock);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockMetalColor(), ModBlocks.ore);

		super.init(e);
	}

	    @SubscribeEvent
	    public static void registerModels(ModelRegistryEvent event) {
	    	SteampunkRevolutionMod.logger.log(Level.INFO, "initializing models");
	 	   ModBlocks.initModels();
	 	   ModItems.initModels();
	 	  	
	    }
	    
	

	    

	    


}
