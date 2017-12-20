package ghostwolf.steampunkrevolution.init;

import org.fusesource.jansi.Ansi.Color;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.entities.EntityMinecartTank;
import ghostwolf.steampunkrevolution.entities.EntityRobot;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Mode;

public class ModEntities {
	
	public static void init () {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":minecart_tank"), EntityMinecartTank.class, "tankcart", 0, SteampunkRevolutionMod.instance, 64, 1, true, 1,2);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":robot"), EntityRobot.class, "robot", 1, SteampunkRevolutionMod.instance, 64, 1, true, 4, 5);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
	}

}
