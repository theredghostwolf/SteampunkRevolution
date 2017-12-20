package ghostwolf.steampunkrevolution.items;

import java.util.List;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrassGoggles extends ItemArmor {
	

	public ItemBrassGoggles() {
		super(ModItems.Brass_Goggles_armor, 0,EntityEquipmentSlot.HEAD);
		setUnlocalizedName(Reference.MOD_ID + ":brassgoggles");
		setRegistryName("brassgoggles");
	}
	
	@Override
	public boolean isDamageable() {
		return false;
	}
	
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));

	}
	
	 @SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("♫ ~ Brass Goggles .. goggles .. goggles ~ ♫");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
}
