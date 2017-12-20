package ghostwolf.steampunkrevolution.init;

import ghostwolf.steampunkrevolution.fluids.FluidResin;
import ghostwolf.steampunkrevolution.fluids.FluidSteam;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
	
	public static FluidSteam steam;
	public static FluidResin resin;
	
	
	public static void init() {
		steam = new FluidSteam();
		resin = new FluidResin();
		
		FluidRegistry.registerFluid(resin);
		FluidRegistry.registerFluid(steam);
	}

}
