package ghostwolf.steampunkrevolution.items;

import java.util.List;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMaterial extends Item {
	
	public ItemMaterial () {
	 setRegistryName("material");
     setUnlocalizedName(Reference.MOD_ID + ":material");
     setHasSubtypes(true);
     setCreativeTab(ModItems.SteampunkItemsTab);
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return EnumMaterial.values()[itemStack.getItemDamage()].getFuelValue();
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		for (EnumMaterial i : EnumMaterial.values()) {
			ModelLoader.setCustomModelResourceLocation(this, i.ordinal(), new ModelResourceLocation(getRegistryName(), "type=" + i.getName()));
		}
	}
	
	public void addToOreDict () {
		for (EnumMaterial i : EnumMaterial.values()) {
			ItemStack stack = new ItemStack(this, 1, i.ordinal());
			if (i.getOredictEntry() != null && i.getOredictEntry() != "") {
				OreDictionary.registerOre(i.getOredictEntry(), stack);
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + getRegistryName() +  "." + EnumMaterial.values()[stack.getItemDamage()].name();
	}
	
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (itemIn == ModItems.SteampunkItemsTab) {
			for (EnumMaterial m : EnumMaterial.values()) {
				tab.add(new ItemStack(this,1,m.getMeta()));
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String t = EnumMaterial.values()[stack.getItemDamage()].getToolTip();
		if (t != null && t != "") {
			tooltip.add(t);
			//SteampunkRevolutionMod.logger.log(Level.INFO, "added tooltip : " + t);
		}
		
		
		
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
}
