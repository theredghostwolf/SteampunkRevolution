package ghostwolf.steampunkrevolution.colors;

import ghostwolf.steampunkrevolution.enums.EnumMetals;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class IItemBlockMetalColor implements IItemColor {

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return EnumMetals.values()[stack.getItemDamage()].getColor();
	}

}
