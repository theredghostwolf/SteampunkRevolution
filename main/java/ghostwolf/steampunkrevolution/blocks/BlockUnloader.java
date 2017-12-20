package ghostwolf.steampunkrevolution.blocks;

import ghostwolf.steampunkrevolution.tileentities.TileEntityUnloader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockUnloader extends BlockMachineBase {

	public BlockUnloader() {
		super("unloader", 0);
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
				return new TileEntityUnloader();
		}
	
	@Override
	public EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
	            (float) (entity.posX - clickedBlock.getX()),
	            (float) (entity.posY - clickedBlock.getY()),
	            (float) (entity.posZ - clickedBlock.getZ()));
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (blockState.getValue(enabled)) {
			return 15;
		}
		return 0;
	}
}
