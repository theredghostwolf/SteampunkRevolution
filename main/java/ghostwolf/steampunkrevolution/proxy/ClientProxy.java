package ghostwolf.steampunkrevolution.proxy;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import ghostwolf.steampunkrevolution.init.ModBlocks;
import ghostwolf.steampunkrevolution.init.ModEntities;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.render.RenderWorldLastEventHandler;
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
		super.init(e);
	}

	    @SubscribeEvent
	    public static void registerModels(ModelRegistryEvent event) {
	    	SteampunkRevolutionMod.logger.log(Level.INFO, "initializing models");
	 	   ModBlocks.initModels();
	 	   ModItems.initModels();
	 	  	
	    }
	    
	

	    

	    


}
