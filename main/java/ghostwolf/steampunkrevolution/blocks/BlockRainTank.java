package ghostwolf.steampunkrevolution.blocks;

import java.util.List;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.tileentities.TileEntityRainTank;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRainTank extends Block implements ITileEntityProvider {

	public BlockRainTank() {
		super(Material.WOOD);
		
		setUnlocalizedName(Reference.MOD_ID + ":raintank");
		setRegistryName("raintank");
		setCreativeTab(ModItems.SteampunkItemsTab);
		
		setHardness(1.5F);
		setResistance(5F);
		setHarvestLevel("axe", 1);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRainTank();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	 @SideOnly(Side.CLIENT)
	    public void initModel() {
		 ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	    }
	 
	 
	 @Override
	public boolean isNormalCube(IBlockState state) {
		
		return false;
		}
	 
	 @Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		
		return false;
		}
	 
	 @Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0,0,0,0.1,1,1));
		addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0,0,0,1,1,0.1));
		addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(1,0,0,0.9,1,1));
		addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0,0,1,1,1,0.9));
		//floor
		addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0,0,0,1,0.1,1));
		
	}
	 
	 
	 
	 
	 

}
