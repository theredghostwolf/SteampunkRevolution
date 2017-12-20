package ghostwolf.steampunkrevolution.items;

import ghostwolf.steampunkrevolution.blocks.IMetaBlockName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMetaBlock extends ItemBlock {
	public ItemMetaBlock(Block block) {
		super(block);
		if (!(block instanceof IMetaBlockName)) {
			throw new IllegalArgumentException(String.format("The given Block %s is not an instance of IMetaBlockName!", block.getUnlocalizedName()));
		}
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + '.' + ((IMetaBlockName) this.block).getSpecialName(stack);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
