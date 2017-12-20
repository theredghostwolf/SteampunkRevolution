package ghostwolf.steampunkrevolution.blocks;

import com.mojang.authlib.properties.Property;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.enums.EnumOres;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOre extends Block implements IMetaBlockName {

	public static final PropertyEnum type = PropertyEnum.create("type", EnumOres.class);
	
	public BlockOre() {
		super(Material.ROCK);
		setUnlocalizedName(Reference.MOD_ID + ":" + "ore");
		setRegistryName("ore");
		setCreativeTab(ModItems.SteampunkItemsTab);
		
		setHardness(1.5F);
		setResistance(5F);
		setHarvestLevel("pickaxe", 1);
	
        setDefaultState(blockState.getBaseState().withProperty(type, EnumOres.Copper));
	}
	
	@Override
	public int getHarvestLevel(IBlockState state) {
		return ((EnumOres) state.getValue(type)).getHarvestLevel();
	}
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return  ((EnumOres) blockState.getValue(type)).getHardness();
	}
	
	 @SideOnly(Side.CLIENT)
	    public void initModel() {
		 for (EnumOres o : EnumOres.values()) {
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), o.getMeta() , new ModelResourceLocation(getRegistryName(), "type=" + o.getName()));
		 }
		}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumOres) state.getValue(type)).getMeta();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return getDefaultState().withProperty(type, EnumOres.values()[meta]);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, type);
	}
	
	
	
	@Override
	public String getSpecialName(ItemStack stack) {
		return EnumOres.values()[stack.getItemDamage()].getName();
	}
	
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		
		return new ItemStack(Item.getItemFromBlock(this),1,getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	public void initOreDict () {
		for (EnumOres o : EnumOres.values()) {
			OreDictionary.registerOre("ore" + o.name(), new ItemStack(Item.getItemFromBlock(this),1,o.getMeta()));
		}
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		for (EnumOres o : EnumOres.values()) {
			tab.add(new ItemStack(Item.getItemFromBlock(this),1, o.getMeta()));
		}
	}

}
