package ghostwolf.steampunkrevolution.blocks;

import ghostwolf.steampunkrevolution.tileentities.TileEntityAltar;
import ghostwolf.steampunkrevolution.tileentities.TileEntityPedestal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAltar extends BlockDisplay {

	public BlockAltar() {
		super("altar");
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAltar();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
			
			if (! world.isRemote) {
				if (player.isSneaking()) {
					
					TileEntity te = world.getTileEntity(pos);
					if (te instanceof TileEntityAltar) {
						((TileEntityAltar) te).Craft();
					}
				} else {
					super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
				}
			}
			
			return true;
	}

}
