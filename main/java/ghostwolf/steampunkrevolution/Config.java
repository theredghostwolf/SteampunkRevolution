package ghostwolf.steampunkrevolution;

import ghostwolf.steampunkrevolution.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class Config {
	

    private static final String category_general = "general";
    private static final String category_boiler = "boiler";
    private static final String category_machines = "machines";
    private static final String category_mechanoids = "mechanoids";
    private static final String category_rails = "rails";

    // This values below you can access elsewhere in your mod:
    
    //general stuff
    public static boolean enableSmoke = true;
    
    //boiler
    public static float boilerFuelAmp = 0.25F;
    public static int boilerMaxHeat = 300;
    public static float boilerHeatUpRate = 0.05F;
    public static float boilerCooldownRate = 0.03F;
    public static int boilerBaseConversion = 20;
    public static int boilerConversionPoint = 100;
    public static float boilerMaxConversionAmp = 2;
    
    //steamfurnace
    public static int steamFurnaceSpeed = 40;
    public static int steamFurnaceUsage = 80;
    
    //steamoven
    public static int steamOvenSpeed = 40;
    public static int steamOvenUsage = 40;
    
    //raintank
    public static int rainTankAmp = 200;
    public static int rainTankBuckets = 16;
    
    //resinextractor
    public static int resinExtractorUsage = 20;
    public static int resinExtractorSpeed = 100;
    public static int resinExtractorConversionRate = 200;
    
    //resinsolidifier
    public static int resinSolidifierSpeed = 100;
    public static int resinSolidifierUsage = 20;
    public static int resinSolidifierConversionRate = 100;
    
    //tankcart
    public static int tankCartBuckets = 16;
    
    //loaders and unloaders
    public static int minecartLoaderSpeed = 5;
    public static int minecartUnloaderSpeed = 5;
    public static int tankcartLoaderSpeed = 100;
    public static int tankcartUnloaderSpeed = 100;
    
    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initBoilerConfig(cfg);
            initMachineConfig(cfg);
            initMechanoidConfig(cfg);
        } catch (Exception e1) {
            //error
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(category_general, "General configuration");
        enableSmoke = cfg.getBoolean("enableSmoke", category_general, enableSmoke, "when set to true the machines will create smoke particles when active");
        
        rainTankAmp = cfg.getInt("rainbarrelRainStrengthAmp", category_general, rainTankAmp, 0, 100000, "amount the raining strength gets multiplied by to generate water while raining (rainstrength is between 0.2 and 1)");
        rainTankBuckets = cfg.getInt("rainbarrelBuckets", category_general, rainTankBuckets, 1, 1000, "amount of buckets the rainbarrel can hold");
    }

    private static void initBoilerConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(category_boiler, "Boiler configuration");
        boilerBaseConversion = cfg.getInt("boilerBaseConversion", category_boiler,boilerBaseConversion , 1, 4000, "amount of water turned to steam when the boilers heat is equal to the boilers conversion point");
        boilerConversionPoint = cfg.getInt("boilerConversionPoint", category_boiler, boilerConversionPoint, 0, 2000, "heat level the boiler starts turning water into steam");
        boilerMaxConversionAmp = cfg.getFloat("boilerMaxConversionAmp", category_boiler, boilerMaxConversionAmp, 0, 100, "amount of extra steam produced when on max heat, (base + (base * (amp * (heat / maxheat))))");
        boilerFuelAmp = cfg.getFloat("boilerFuelAmp", category_boiler, boilerFuelAmp, 0, 100, "amount the fuel value of items gets amplified by");
        boilerMaxHeat = cfg.getInt("boilerMaxHeat", category_boiler, boilerMaxHeat, 0, 2000, "max heat the boiler can reach");
        boilerHeatUpRate = cfg.getFloat("boilerHeatUpRate", category_boiler, boilerHeatUpRate, 0, 200, "amount of heat the boiler gains per tick while burning fuel");
        boilerCooldownRate = cfg.getFloat("boilerCooldownRate", category_boiler, boilerCooldownRate, 0, 200, "amount of heat loss per tick when out of fuel");
    }
    
    private static void initMechanoidConfig (Configuration cfg) {
        cfg.addCustomCategoryComment(category_mechanoids, "Mechanoid Configuration");

    }
    
    
    private static void initRailConfig (Configuration cfg) {
    	cfg.addCustomCategoryComment(category_rails, "Rail-related Configuration");
    	tankCartBuckets = cfg.getInt("tankCartBuckets", category_rails, tankCartBuckets, 1, 1000, "amount of buckets the tankcart can hold");
    	
    	minecartLoaderSpeed = cfg.getInt("loaderSpeed", category_rails, minecartLoaderSpeed, 1, 64, "amount of items the loader moves per tick");
    	minecartUnloaderSpeed = cfg.getInt("unloaderSpeed", category_rails, minecartUnloaderSpeed, 1, 64, "amount of items the unloader moves per tick");
    	
    	tankcartLoaderSpeed = cfg.getInt("fluidLoaderSpeed", category_rails, tankcartLoaderSpeed, 1, 10000, "amount of mb loaded per tick");
    	tankcartUnloaderSpeed  = cfg.getInt("fluidUnloaderSpeed", category_rails, tankcartUnloaderSpeed, 1, 10000, "amount of mb unloaded per tick");
    }
    
    private static void initMachineConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(category_machines, "Machine Configuration");
        steamFurnaceSpeed = cfg.getInt("steamFurnaceSpeed", category_machines, steamFurnaceSpeed, 0, 1000, "amount of ticks it takes for the steam furnace to process an item");
        steamFurnaceUsage = cfg.getInt("steamFurnaceUsage", category_machines, steamFurnaceUsage, 0, 10000, "amount of steam per/tick the steamfurnace uses");
        
        steamOvenSpeed = cfg.getInt("steamOvenSpeed", category_machines, steamOvenSpeed, 0, 1000, "amount of ticks it takes for the steamOven to process an item");
        steamOvenUsage = cfg.getInt("steamOvenUsage", category_machines, steamOvenUsage, 0, 10000, "Amount of steam per/tick the steamOven uses");
    
        resinExtractorSpeed = cfg.getInt("resinExtractorSpeed", category_machines, resinExtractorSpeed, 0, 1000, "amount of ticks it takes for the extractor to turn a log into resin");
        resinExtractorUsage = cfg.getInt("resinExtractorUsage", category_machines, resinExtractorUsage, 0, 10000, "amount of steam/tick the resin extractor uses");
        resinExtractorConversionRate = cfg.getInt("resinExtractorConversionRate", category_machines, resinExtractorConversionRate, 0, 100000, "amount of resin a single log generates");
    
        resinSolidifierSpeed = cfg.getInt("resinSolidifierSpeed", category_machines, resinSolidifierSpeed, 0, 1000, "amount of ticks it takes for the solidifier to process");
        resinSolidifierUsage = cfg.getInt("resinSolidifierUsage", category_machines, resinSolidifierUsage, 0, 10000, "amount of steam/tick the resinsolidifier uses");
        resinSolidifierConversionRate = cfg.getInt("resinSolidifierConversionRate", category_machines, resinSolidifierConversionRate, 0, 10000, "amount of resin it takes for the solidifier to create a single item");
        
        
        
    }

}
