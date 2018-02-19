package ghostwolf.steampunkrevolution.colors;

import ghostwolf.steampunkrevolution.blocks.BlockMetal;
import ghostwolf.steampunkrevolution.enums.EnumMetals;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class IBlockMetalColor implements IBlockColor {

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		if (tintIndex == 0) {
			EnumMetals m = (EnumMetals) state.getValue(BlockMetal.type);
			return m.getColor();
		} else {
			return 0xffffff;
		}
	}

}
