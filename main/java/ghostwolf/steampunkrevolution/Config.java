package ghostwolf.steampunkrevolution;

import ghostwolf.steampunkrevolution.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class Config {
	

    private static final String category_general = "general";
    private static final String category_boiler = "boiler";
    private static final String category_machines = "machines";
    private static final String category_mechanoids = "mechanoids";
    private static final String category_rails = "rails";
    private static final String category_ores = "ores";

    // This values below you can access elsewhere in your mod:
    
    //general stuff
    public static boolean enableSmoke = true;
    public static int smokeRenderRange = 40;
    
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
    
    //altar
    public static int pedestalRange = 7;
    public static int pedestalHeightRange = 3;
    
    //ores
    public static int CopperMinVein = 3;
    public static int CopperMaxVein = 6;
    public static int CopperMinYGen = 30;
    public static int CopperMaxYGen = 70;
    public static int CopperSpawnChance = 15;
    
    public static int TinMinVein = 3;
    public static int TinMaxVein = 6;
    public static int TinMinYGen = 30;
    public static int TinMaxYGen = 60;
    public static int TinSpawnChance = 15;
    
    public static int LeadMinVein = 2;
    public static int LeadMaxVein = 6;
    public static int LeadMinYGen = 20;
    public static int LeadMaxYGen = 50;
    public static int LeadSpawnChance = 15;
    
    public static int ZincMinVein = 2;
    public static int ZincMaxVein = 4;
    public static int ZincMinYGen = 0;
    public static int ZincMaxYGen = 45;
    public static int ZincSpawnChance = 13;
    
    public static int SilverMinVein = 2;
    public static int SilverMaxVein = 4;
    public static int SilverMinYGen = 0;
    public static int SilverMaxYGen = 40;
    public static int SilverSpawnChance = 11;
    
    public static int TitaniumMinVein = 1;
    public static int TitaniumMaxVein = 3;
    public static int TitaniumMinYGen = 0;
    public static int TitaniumMaxYGen = 30;
    public static int TitaniumSpawnChance = 8;
    
    public static int TungstenMinVein = 1;
    public static int TungstenMaxVein = 3;
    public static int TungstenMinYGen = 0;
    public static int TungstenMaxYGen = 30;
    public static int TungstenSpawnChance = 8;
    
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
            initOreConfig(cfg);
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
        smokeRenderRange = cfg.getInt("smokeRenderRange", category_general, smokeRenderRange, 0, 200, "range of which the player has to be near an entity or tile entity before smoke particles start spawning");
        
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
         
        pedestalRange = cfg.getInt("pedestalRange", category_machines, pedestalRange, 1, 20, "range within the altar detects pedestals, on the X and Z axis");
        pedestalHeightRange = cfg.getInt("pedestalHeightRange", category_machines, pedestalHeightRange, 0, 10, "range within the altar detects pedestals, on the Y axis");
    }
    
    private static void initOreConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(category_ores, "Ore generation");
        
        //copper
        CopperMinVein = cfg.getInt("CopperMinVeinSize", category_ores, CopperMinVein, 0, 1000, "minimum vein size");
        CopperMaxVein = cfg.getInt("CopperMaxVein", category_ores, CopperMaxVein, 0, 1000, "maximum vein size");
        
        CopperMinYGen = cfg.getInt("CopperMinYGen", category_ores, CopperMinYGen, 0, 255, "minimum Y Ore can spawn at");
        CopperMaxYGen = cfg.getInt("CopperMaxYGen", category_ores, CopperMaxYGen, 0, 255, "maximum Y Ore can spawn at");
        
        CopperSpawnChance = cfg.getInt("CopperSpawnChance", category_ores, CopperSpawnChance, 0, 1000, "Chance ore has to spawn");
        
        //tin
        TinMinVein = cfg.getInt("TinMinVeinSize", category_ores, TinMinVein, 0, 1000, "minimum vein size");
        TinMaxVein = cfg.getInt("TinMaxVein", category_ores, TinMaxVein, 0, 1000, "maximum vein size");
        
        TinMinYGen = cfg.getInt("TinMinYGen", category_ores, TinMinYGen, 0, 255, "minimum Y Ore can spawn at");
        TinMaxYGen = cfg.getInt("TinMaxYGen", category_ores, TinMaxYGen, 0, 255, "maximum Y Ore can spawn at");
        
        TinSpawnChance = cfg.getInt("TinSpawnChance", category_ores, TinSpawnChance, 0, 1000, "Chance ore has to spawn");
        
        //lead
        LeadMinVein = cfg.getInt("LeadMinVeinSize", category_ores, LeadMinVein, 0, 1000, "minimum vein size");
        LeadMaxVein = cfg.getInt("LeadMaxVein", category_ores, LeadMaxVein, 0, 1000, "maximum vein size");
        
        LeadMinYGen = cfg.getInt("LeadMinYGen", category_ores, LeadMinYGen, 0, 255, "minimum Y Ore can spawn at");
        LeadMaxYGen = cfg.getInt("LeadMaxYGen", category_ores, LeadMaxYGen, 0, 255, "maximum Y Ore can spawn at");
        
        LeadSpawnChance = cfg.getInt("LeadSpawnChance", category_ores, LeadSpawnChance, 0, 1000, "Chance ore has to spawn");
        
        //zinc
        ZincMinVein = cfg.getInt("ZincMinVeinSize", category_ores, ZincMinVein, 0, 1000, "minimum vein size");
        ZincMaxVein = cfg.getInt("ZincMaxVein", category_ores, ZincMaxVein, 0, 1000, "maximum vein size");
        
        ZincMinYGen = cfg.getInt("ZincMinYGen", category_ores, ZincMinYGen, 0, 255, "minimum Y Ore can spawn at");
        ZincMaxYGen = cfg.getInt("ZincMaxYGen", category_ores, ZincMaxYGen, 0, 255, "maximum Y Ore can spawn at");
        
        ZincSpawnChance = cfg.getInt("ZincSpawnChance", category_ores, ZincSpawnChance, 0, 1000, "Chance ore has to spawn");
        
        //silver
        SilverMinVein = cfg.getInt("SilverMinVeinSize", category_ores, SilverMinVein, 0, 1000, "minimum vein size");
        SilverMaxVein = cfg.getInt("SilverMaxVein", category_ores, SilverMaxVein, 0, 1000, "maximum vein size");
        
        SilverMinYGen = cfg.getInt("SilverMinYGen", category_ores, SilverMinYGen, 0, 255, "minimum Y Ore can spawn at");
        SilverMaxYGen = cfg.getInt("SilverMaxYGen", category_ores, SilverMaxYGen, 0, 255, "maximum Y Ore can spawn at");
        
        SilverSpawnChance = cfg.getInt("SilverSpawnChance", category_ores, SilverSpawnChance, 0, 1000, "Chance ore has to spawn");
        
        //titanium
        TitaniumMinVein = cfg.getInt("TitaniumMinVeinSize", category_ores, TitaniumMinVein, 0, 1000, "minimum vein size");
        TitaniumMaxVein = cfg.getInt("TitaniumMaxVein", category_ores, TitaniumMaxVein, 0, 1000, "maximum vein size");
        
        TitaniumMinYGen = cfg.getInt("TitaniumMinYGen", category_ores, TitaniumMinYGen, 0, 255, "minimum Y Ore can spawn at");
        TitaniumMaxYGen = cfg.getInt("TitaniumMaxYGen", category_ores, TitaniumMaxYGen, 0, 255, "maximum Y Ore can spawn at");
        
        TitaniumSpawnChance = cfg.getInt("TitaniumSpawnChance", category_ores, TitaniumSpawnChance, 0, 1000, "Chance ore has to spawn");
        
        //tungsten
        TungstenMinVein = cfg.getInt("TungstenMinVeinSize", category_ores, TungstenMinVein, 0, 1000, "minimum vein size");
        TungstenMaxVein = cfg.getInt("TungstenMaxVein", category_ores, TungstenMaxVein, 0, 1000, "maximum vein size");
        
        TungstenMinYGen = cfg.getInt("TungstenMinYGen", category_ores, TungstenMinYGen, 0, 255, "minimum Y Ore can spawn at");
        TungstenMaxYGen = cfg.getInt("TungstenMaxYGen", category_ores, TungstenMaxYGen, 0, 255, "maximum Y Ore can spawn at");
        
        TungstenSpawnChance = cfg.getInt("TungstenSpawnChance", category_ores, TungstenSpawnChance, 0, 1000, "Chance ore has to spawn");
        
    }

}
