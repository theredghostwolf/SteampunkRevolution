package ghostwolf.steampunkrevolution.fluids;

import java.awt.Color;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidResin extends Fluid {
	public FluidResin() {
		super("resin", new ResourceLocation("minecraft:blocks/lava_still"), new ResourceLocation("minecraft:blocks/lava_flow"), Color.ORANGE);
	}
}
