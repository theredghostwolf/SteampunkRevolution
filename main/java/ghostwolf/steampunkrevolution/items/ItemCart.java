package ghostwolf.steampunkrevolution.items;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.enums.EnumCarts;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemCart extends Item {
	
	public ItemCart () {
		setRegistryName("cart");
	     setUnlocalizedName(Reference.MOD_ID + ":cart");
	     setHasSubtypes(true);
	     setCreativeTab(ModItems.SteampunkItemsTab);
	     this.maxStackSize = 1;
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		for (EnumCarts i : EnumCarts.values()) {
			ModelLoader.setCustomModelResourceLocation(this, i.ordinal(), new ModelResourceLocation(getRegistryName(), "type=" + i.getName()));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + getRegistryName() +  "." + EnumCarts.values()[stack.getItemDamage()].name();
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
	if (! worldIn.isRemote) {
		worldIn.spawnEntity(EnumCarts.values()[ player.getHeldItem(hand).getMetadata()].getCart(worldIn, hitX, hitY, hitZ));
		player.getHeldItem(hand).shrink(1);
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);

	}
	
	
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (itemIn == ModItems.SteampunkItemsTab) {
			for (EnumCarts m : EnumCarts.values()) {
				tab.add(new ItemStack(this,1,m.getMeta()));
			}
		}
	}
}
