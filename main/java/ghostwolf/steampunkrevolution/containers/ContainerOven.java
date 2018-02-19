package ghostwolf.steampunkrevolution.containers;

import javax.annotation.Nullable;

import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerOven extends Container {

	private TileEntitySteamOven te;
	
	public ContainerOven(TileEntitySteamOven te, IInventory playerInventory) {
		this.te = te;
		addPlayerSlots(playerInventory);
		
		addInputSlots();
		addOutputSlots();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}
	
	   private void addInputSlots() {
	        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	        int x = 9;
	        int y = 6;

	        // Add our own slots
	        int slotIndex = 0;
	        for (int i = 0; i < itemHandler.getSlots(); i++) {
	            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
	            slotIndex++;
	            x += 18;
	        }
	    }
	   
	   private void addOutputSlots () {
		   IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
	        int x = 9;
	        int y = 24;

	        // Add our own slots
	        int slotIndex = 0;
	        for (int i = 0; i < itemHandler.getSlots(); i++) {
	            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
	            slotIndex++;
	            x += 18;
	        }
	   }
	
	   private void addPlayerSlots(IInventory playerInventory) {
	        // Slots for the main inventory
	        for (int row = 0; row < 3; ++row) {
	            for (int col = 0; col < 9; ++col) {
	                int x = 9 + col * 18;
	                int y = row * 18 + 70;
	                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
	            }
	        }

	        // Slots for the hotbar
	        for (int row = 0; row < 9; ++row) {
	            int x = 9 + row * 18;
	            int y = 58 + 70;
	            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
	        }
	    }
	   
	   

	    @Override
	    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
	    	 ItemStack previous = null;
	    	    Slot slot = (Slot) this.inventorySlots.get(index);

	    	    if (slot != null && slot.getHasStack()) {
	    	        ItemStack current = slot.getStack();
	    	        previous = current.copy();

	    	        // [...] Custom behaviour

	    	        if (current.getCount() == 0)
	    	            slot.putStack((ItemStack) null);
	    	        else
	    	            slot.onSlotChanged();

	    	        if (current.getCount() == previous.getCount())
	    	            return null;
	    	         slot.onTake(playerIn, current);
	    	    }
	    	    return previous;
	    }
	
}
