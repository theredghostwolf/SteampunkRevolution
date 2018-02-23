package ghostwolf.steampunkrevolution.init;

import java.util.Iterator;
import java.util.Set;

import org.fusesource.jansi.Ansi.Color;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.entities.EntityKabaneri;
import ghostwolf.steampunkrevolution.entities.EntityMinecartTank;
import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.models.ModelRobot;
import ghostwolf.steampunkrevolution.render.RenderKabaneri;
import ghostwolf.steampunkrevolution.render.RenderRobot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeRiver;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Mode;

public class ModEntities {
	
	public static void init () {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":minecart_tank"), EntityMinecartTank.class, ":tankcart", 0, SteampunkRevolutionMod.instance, 64, 1, true, 1,2);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":robot"), EntityRobot.class, Reference.MOD_ID + ":robot", 1, SteampunkRevolutionMod.instance, 64, 1, true, 4, 5);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":kabaneri"), EntityKabaneri.class, Reference.MOD_ID + ":kabaneri", 2, SteampunkRevolutionMod.instance, 64, 1, true, 6, 8);
		
		initSpawns();
	}
	
	public static void initSpawns () {
		
		BiomeDictionary.Type[] types = {
				BiomeDictionary.Type.BEACH,
				BiomeDictionary.Type.COLD,
				BiomeDictionary.Type.CONIFEROUS,
				BiomeDictionary.Type.DEAD,
				BiomeDictionary.Type.DENSE,
				BiomeDictionary.Type.DRY,
				BiomeDictionary.Type.FOREST,
				BiomeDictionary.Type.HILLS,
				BiomeDictionary.Type.HOT,
				BiomeDictionary.Type.JUNGLE,
				BiomeDictionary.Type.LUSH,
				BiomeDictionary.Type.MAGICAL,
				BiomeDictionary.Type.MESA,
				BiomeDictionary.Type.MOUNTAIN,
				BiomeDictionary.Type.OCEAN,
				BiomeDictionary.Type.PLAINS,
				BiomeDictionary.Type.RARE,
				BiomeDictionary.Type.RIVER,
				BiomeDictionary.Type.SANDY,
				BiomeDictionary.Type.SAVANNA,
				BiomeDictionary.Type.SNOWY,
				BiomeDictionary.Type.SPARSE,
				BiomeDictionary.Type.SPOOKY,
				BiomeDictionary.Type.SWAMP,
				BiomeDictionary.Type.VOID,
				BiomeDictionary.Type.WASTELAND,
				BiomeDictionary.Type.WATER,
				BiomeDictionary.Type.WET
		};
		
		SpawnListEntry spawnKabaneri = new SpawnListEntry(EntityKabaneri.class, Config.KabaneriSpawnChance, 1, 2);

		
		for (BiomeDictionary.Type t : types) {
			Set<Biome> biomes = BiomeDictionary.getBiomes(t);
			
			Iterator biomeIterator = biomes.iterator();
			
			while(biomeIterator.hasNext()) {
				Biome currentBiome = (Biome) biomeIterator.next();
				if (BiomeDictionary.hasType(currentBiome, BiomeDictionary.Type.END) || BiomeDictionary.hasType(currentBiome, BiomeDictionary.Type.NETHER)) {
					
				} else {
					currentBiome.getSpawnableList(EnumCreatureType.MONSTER).add(spawnKabaneri);		
				}	
			}
			
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {

		RenderingRegistry.registerEntityRenderingHandler(EntityKabaneri.class, new RenderKabaneri(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityRobot.class,new RenderRobot(Minecraft.getMinecraft().getRenderManager(),new ModelRobot(0), 0.4F) );


	}

}
 