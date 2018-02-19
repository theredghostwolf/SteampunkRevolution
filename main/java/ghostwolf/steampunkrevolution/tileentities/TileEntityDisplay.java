package ghostwolf.steampunkrevolution.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDisplay extends TileEntity {
	
	 private ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
	        @Override
	        protected void onContentsChanged(int slot) {
	            TileEntityDisplay.this.markDirty();
	            TileEntityDisplay.this.getWorld().notifyBlockUpdate(pos, getState(), getState(), 3);
	         }
	        
	        @Override
	        protected int getStackLimit(int slot, ItemStack stack) {
	        	       return 1;
	        }
	    };
	    
	    private IBlockState getState() {
			return getWorld().getBlockState(pos);
	}
	    
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			
			if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
			} else {
				return super.getCapability(capability, facing);
			}
		}
		
	    @Override
	    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
	    	
	    	 super.writeToNBT(compound);
	    	 compound.setTag("items", itemStackHandler.serializeNBT());
	    	 
	    	 return compound;
	    }
	    
	    @Override
	    public void readFromNBT(NBTTagCompound compound) {
	    	super.readFromNBT(compound);
	    	  if (compound.hasKey("items")) {
	              itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
	          }
	    }
	    
		public void DropItemsFromInv (IItemHandler inv) {
			for (int i = 0; i < inv.getSlots(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				if (!s.isEmpty()) {
					getWorld().spawnEntity(new EntityItem(getWorld(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), s));
				}
			}
		}

		public void dropItems() {
			DropItemsFromInv(itemStackHandler);
		}
		
		public ItemStack getStack () {
			return itemStackHandler.getStackInSlot(0);
		}
		
		   @Override
		    public NBTTagCompound getUpdateTag() {
		    
		        return writeToNBT(new NBTTagCompound());
		    }

		    @Override
		    public SPacketUpdateTileEntity getUpdatePacket() {
		   
		        NBTTagCompound nbtTag = new NBTTagCompound();
		        this.writeToNBT(nbtTag);
		        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
		    }

		    @Override
		    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		        this.readFromNBT(packet.getNbtCompound());
		    }
		    
		   
		    

}
