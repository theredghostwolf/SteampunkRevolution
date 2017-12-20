package ghostwolf.steampunkrevolution.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryHelper {
	
	//checks if and inventory contains a certain item
	public boolean inventoryContainsItem (IItemHandler inv, Item item) {
		for (int i = 0; i < inv.getSlots(); i++) {
			if (inv.getStackInSlot(i).getItem() == item) {
				return true;
			}
		}
		return false;
	}
	
	//gets the first stack that matches the item in the inventory
	public ItemStack inventoryGetItem (IItemHandler inv, Item item) {
		for (int i = 0; i < inv.getSlots(); i++) {
			if (inv.getStackInSlot(i).getItem() == item) {
				return inv.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
	
	//drops item from inventory into world
	public void DropItemsFromInv (IItemHandler inv, World world, BlockPos pos) {
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (!s.isEmpty()) {
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), s));
			}
		}
	}
	
	//checks if iventory has room for an item
	public boolean InventoryHasRoomForItem (IItemHandler inv, ItemStack stack) {
		if (inv != null && !stack.isEmpty()) {
			for (int i = 0; i < inv.getSlots(); i++) {
				ItemStack remainder =  inv.insertItem(i, stack, true);
				if (remainder.isEmpty() || remainder.getCount() < stack.getCount()) {
					return true;
				}
			}
		}
		return false;
	}
	
	//checks if there is an item present in the inventory
	public boolean inventoryHasItem (IItemHandler inv) {
		for (int i = 0; i < inv.getSlots(); i++) {
			if (! inv.getStackInSlot(i).isEmpty()) {
				return true;
			}
		}
		return false;
	}
	


}
