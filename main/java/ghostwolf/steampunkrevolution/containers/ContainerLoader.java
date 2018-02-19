package ghostwolf.steampunkrevolution.containers;

import ghostwolf.steampunkrevolution.tileentities.TileEntityLoader;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLoader extends Container {
	
	private TileEntityLoader te;
	
	public ContainerLoader(TileEntityLoader te, IInventory playerInventory) {
		this.te = te;
		addPlayerSlots(playerInventory);
		
		addSlots();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}
	
	  private void addSlots () {
		   IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
	   
	       

	        // Add our own slots
	        int slotIndex = 0;
	        for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					int x = (j * 18) + 62;
					int y = (i * 18) + 17;
					this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex,x,y));
					slotIndex++;
				}
			}
	   }
	
	   private void addPlayerSlots(IInventory playerInventory) {
	       
	        for (int row = 0; row < 3; ++row) {
	            for (int col = 0; col < 9; ++col) {
	                int x = 8 + col * 18;
	                int y = row * 18 + 84;
	                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
	            }
	        }

	        for (int row = 0; row < 9; ++row) {
	            int x = 8 + row * 18;
	            int y = 58 + 84;
	            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
	        }
	    }

}
