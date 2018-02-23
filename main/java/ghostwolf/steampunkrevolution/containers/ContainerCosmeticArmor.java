package ghostwolf.steampunkrevolution.containers;

import javax.annotation.Nullable;

import ghostwolf.steampunkrevolution.capabilities.CapabilityCosmeticArmor;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSyncCosmeticArmor;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCosmeticArmor extends Container {
    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};

	private EntityPlayer player;
	
	public ContainerCosmeticArmor (EntityPlayer player) {
		this.player = player;
		addPlayerArmorSlots(this.player.inventory);
		addCosmeticArmorSlots(this.player);
		addPlayerSlots(this.player.inventory);
	}

	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	private void addPlayerArmorSlots (IInventory playerInv) {
		 for (int k = 0; k < 4; ++k) {
	            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
	            this.addSlotToContainer(new Slot(playerInv, 36 + (3 - k), 8, 8 + k * 18)
	            		{

	          
	                public int getSlotStackLimit()
	                {
	                    return 1;
	                }
	                
	                public boolean isItemValid(ItemStack stack)
	                {
	                    return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
	                }
	                
	                public boolean canTakeStack(EntityPlayer playerIn)
	                {
	                    ItemStack itemstack = this.getStack();
	                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
	                }
	                @Nullable
	                @SideOnly(Side.CLIENT)
	                public String getSlotTexture()
	                {
	                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
	                }
	            	
	            });
		 }
	}
	
	private void addCosmeticArmorSlots (EntityPlayer player) {
		if (player.hasCapability(CapabilityCosmeticArmor.Cosmetic_Armor_Capability, null)) {
			IItemHandler inv = player.getCapability(CapabilityCosmeticArmor.Cosmetic_Armor_Capability, null);
			
			for (int i = 0; i < 4; ++i) {
				  final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[i];
		            this.addSlotToContainer(new SlotItemHandler( inv, i, 77, 8 + i * 18)
		            		{
		            	
		            			@Override
		            			public void onSlotChanged() {
		            			PacketHandler.INSTANCE.sendToAll(new PacketSyncCosmeticArmor(this.getStack(), (byte) this.getSlotIndex(), player.getEntityId() ));
		            			super.onSlotChanged();
		            		}

		          
		                public int getSlotStackLimit()
		                {
		                    return 1;
		                }
		                
		                public boolean isItemValid(ItemStack stack)
		                {
		                    return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
		                }
		                
		                public boolean canTakeStack(EntityPlayer playerIn)
		                {
		                    ItemStack itemstack = this.getStack();
		                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
		                }
		                @Nullable
		                @SideOnly(Side.CLIENT)
		                public String getSlotTexture()
		                {
		                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
		                }
		            	
		            });
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
