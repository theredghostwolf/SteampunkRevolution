package ghostwolf.steampunkrevolution.colors;

import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IMaterialColor implements IItemColor {

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return EnumMaterial.values()[stack.getItemDamage()].getColor();
		
	}
	
	

}
