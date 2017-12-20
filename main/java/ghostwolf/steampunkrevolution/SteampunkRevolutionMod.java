package ghostwolf.steampunkrevolution;





import org.apache.logging.log4j.Logger;

import ghostwolf.steampunkrevolution.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.Name, version = Reference.Version)
public class SteampunkRevolutionMod {
	
	@SidedProxy(clientSide = Reference.ClientProxy, serverSide = Reference.ServerProxy)
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	@Mod.Instance
    public static SteampunkRevolutionMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

}
