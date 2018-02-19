package ghostwolf.steampunkrevolution.worldgen;

import java.util.Random;
import java.util.function.IntPredicate;

import com.google.common.base.Predicate;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.enums.EnumMetals;
import ghostwolf.steampunkrevolution.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class GenerateOres implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimensionType() == DimensionType.OVERWORLD) {
			generateOverworld(world, random, chunkX, chunkZ);
		}
	}
	
	  public void generateOverworld(World world, Random rand, int x, int z)
	    {
		  
		  	//copper
	        generateOre(ModBlocks.ore.getDefaultState().withProperty(ModBlocks.ore.type, EnumMetals.Copper),world,rand,x,z,Config.CopperMinVein, Config.CopperMaxVein, Config.CopperSpawnChance, Config.CopperMinYGen, Config.CopperMaxYGen,BlockMatcher.forBlock(Blocks.STONE));
	       
	        //tin
	        generateOre(ModBlocks.ore.getDefaultState().withProperty(ModBlocks.ore.type, EnumMetals.Tin),world,rand,x,z,Config.TinMinVein, Config.TinMaxVein, Config.TinSpawnChance, Config.TinMinYGen, Config.TinMaxYGen,BlockMatcher.forBlock(Blocks.STONE));

	        //lead
	        generateOre(ModBlocks.ore.getDefaultState().withProperty(ModBlocks.ore.type, EnumMetals.Lead),world,rand,x,z,Config.LeadMinVein, Config.LeadMaxVein, Config.LeadSpawnChance, Config.LeadMinYGen, Config.LeadMaxYGen,BlockMatcher.forBlock(Blocks.STONE));

	        //silver
	        generateOre(ModBlocks.ore.getDefaultState().withProperty(ModBlocks.ore.type, EnumMetals.Silver),world,rand,x,z,Config.SilverMinVein, Config.SilverMaxVein, Config.SilverSpawnChance, Config.SilverMinYGen, Config.SilverMaxYGen,BlockMatcher.forBlock(Blocks.STONE));

	        //tungsten
	        generateOre(ModBlocks.ore.getDefaultState().withProperty(ModBlocks.ore.type, EnumMetals.Tungsten),world,rand,x,z,Config.TungstenMinVein, Config.TungstenMaxVein, Config.TungstenSpawnChance, Config.TungstenMinYGen, Config.TungstenMaxYGen,BlockMatcher.forBlock(Blocks.STONE));

	        //zinc
	        generateOre(ModBlocks.ore.getDefaultState().withProperty(ModBlocks.ore.type, EnumMetals.Zinc),world,rand,x,z,Config.ZincMinVein, Config.ZincMaxVein, Config.ZincSpawnChance, Config.ZincMinYGen, Config.ZincMaxYGen,BlockMatcher.forBlock(Blocks.STONE));

	        //titanium
	        generateOre(ModBlocks.ore.getDefaultState().withProperty(ModBlocks.ore.type, EnumMetals.Titanium),world,rand,x,z,Config.TitaniumMinVein, Config.TitaniumMaxVein, Config.TitaniumSpawnChance, Config.TitaniumMinYGen, Config.TitaniumMaxYGen,BlockMatcher.forBlock(Blocks.STONE));
	
	    }

	    public void generateOre(IBlockState block, World world, Random random, int chunkX, int chunkZ, int minVienSize, int maxVienSize, int chance, int minY, int maxY, Predicate generateIn)
	    {
	        int vienSize = minVienSize + random.nextInt(maxVienSize - minVienSize);
	        int heightRange = maxY - minY;
	        WorldGenMinable gen = new WorldGenMinable(block, vienSize, generateIn);

	        for (int i = 0; i < chance; ++i)
	        {
	            int xRand = chunkX * 16 + random.nextInt(16);
	            int yRand = random.nextInt(heightRange) + minY;
	            int zRand = chunkZ * 16 + random.nextInt(16);
	            gen.generate(world, random,	new BlockPos( xRand, yRand, zRand));
	        }
	    }

}
