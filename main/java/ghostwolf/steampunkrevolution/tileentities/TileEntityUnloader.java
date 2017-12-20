package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockBoiler;
import ghostwolf.steampunkrevolution.blocks.BlockMachineBase;
import ghostwolf.steampunkrevolution.blocks.BlockUnloader;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityUnloader extends TileEntityMinecartMachineBase {
	
	public static final int size = 9;
	public int speed = Config.minecartUnloaderSpeed;
	
	private boolean unloaderIsFull = false;
	public boolean emitWhenCartEmpty = true;
	public boolean emitWhenUnloaderFull = true;
	public boolean isEmittingRedstone = false;
	
	@Override
	public void update() {
		if (! getWorld().isRemote) {
			EntityMinecart cart = this.getCartInfront(EntityMinecart.class);
			if (cart != null) {
			IItemHandler cartInv = cart.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, getPullFacing());
				if (cartInv != null) {
					if (! this.invIsEmpty(cartInv)) {
						
						this.moveItems(cartInv);
					}
					handleRedstone(cartInv);
				} 
			}
			
		}
	}
	
	private void handleRedstone (IItemHandler inv) {
		this.isEmittingRedstone = false;
		if (invIsEmpty(inv) && this.emitWhenCartEmpty) {
			this.isEmittingRedstone = true;
		}
		if (this.unloaderIsFull && this.emitWhenUnloaderFull) {
			this.isEmittingRedstone = true;
		}
		
		getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockUnloader.enabled, this.isEmittingRedstone));
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
	
    private ItemStackHandler itemStackHandler = new ItemStackHandler(size) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityUnloader.this.markDirty();
        }
    };
    
    private void moveItems (IItemHandler cartInv) {
    	ItemStack stack = this.getFirstStackInInv(cartInv);
    	if (! stack.isEmpty()) {	
    		if (checkIfInventoryHasSpace(itemStackHandler, stack)) {
    			this.unloaderIsFull = false;
    			ItemStack ex = extractFirstStackFromInv(cartInv, speed);
    			if (! ex.isEmpty()) {
    				insertStackInInvetory(itemStackHandler, ex);
    			}
    		} else {
    			this.unloaderIsFull = true;
    		}
    	}
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	
    	 super.writeToNBT(compound);
    	 compound.setTag("items", itemStackHandler.serializeNBT());
    	 compound.setBoolean("emitOnInvFull", this.emitWhenUnloaderFull);
    	 compound.setBoolean("emitCartEmpty", this.emitWhenCartEmpty);
    	 return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	  if (compound.hasKey("items")) {
              itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
          }
    	  if (compound.hasKey("emitOnInvFull")) {
    		  this.emitWhenUnloaderFull = compound.getBoolean("emitOnInvFull");
    	  }
    	  if (compound.hasKey("emitCartEmpty")) {
    		  this.emitWhenCartEmpty = compound.getBoolean("emitCartEmpty");
    	  }
    }
    
    @Override
    public void dropItems() {
    	DropItemsFromInv(itemStackHandler);
    }
}
