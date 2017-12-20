package ghostwolf.steampunkrevolution.blocks;

import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSteamFurnace extends BlockMachineBase {
	
	public BlockSteamFurnace() {
		super("steamfurnace", 10);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySteamFurnace();
	}
}
