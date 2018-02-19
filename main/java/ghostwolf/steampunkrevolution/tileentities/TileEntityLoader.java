package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockBoiler;
import ghostwolf.steampunkrevolution.blocks.BlockLoader;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityLoader extends TileEntityMinecartMachineBase {
	
	public static final int size = 9;
	
	public boolean cartIsFull = false;
	public int speed = Config.minecartLoaderSpeed;
	public boolean emitWhenLoaderEmpty = true;
	public boolean emitWhenCartFull = true;
	public boolean isEmittingRedstone = false;
	
	@Override
	public void update() {
		if (! getWorld().isRemote) {
			EntityMinecart cart = this.getCartInfront(EntityMinecart.class);
			if (cart != null) {
			IItemHandler cartInv = cart.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, getPullFacing());
				if (cartInv != null) {
					if (! this.invIsEmpty(itemStackHandler)) {
						this.moveItems(cartInv);
					}
					handleRedstone();
				} 
			}
		}
	}
	
	public void moveItems(IItemHandler cartinv) {
	 	ItemStack stack = this.getFirstStackInInv(itemStackHandler);
    	if (! stack.isEmpty()) {	
    		if (checkIfInventoryHasSpace(cartinv, stack)) {
    			this.cartIsFull = false;
    			ItemStack ex = extractFirstStackFromInv(itemStackHandler, speed);
    			if (! ex.isEmpty()) {
    				insertStackInInvetory(cartinv, ex);
    			}
    		} else {
    			this.cartIsFull = true;
    		}
    	}
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
            TileEntityLoader.this.markDirty();
        }
    };
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	
    	 super.writeToNBT(compound);
    	 compound.setTag("items", itemStackHandler.serializeNBT());
    	 compound.setBoolean("emitOnInvEmpty", this.emitWhenLoaderEmpty);
    	 compound.setBoolean("emitCartFull", this.emitWhenCartFull);
    	 return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	  if (compound.hasKey("items")) {
              itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
          }
    	  if (compound.hasKey("emitOnInvEmpty")) {
    		  this.emitWhenLoaderEmpty = compound.getBoolean("emitOnInvEmpty");
    	  }
    	  if (compound.hasKey("emitCartFull")) {
    		  this.emitWhenCartFull = compound.getBoolean("emitCartFull");
    	  }
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
    	// TODO Auto-generated method stub
    	NBTTagCompound tag =  super.getUpdateTag();
    		tag.setBoolean("emitWhenEmpty", this.emitWhenLoaderEmpty);
    		tag.setBoolean("emitwhenfull", this.emitWhenCartFull);
    	return tag;
    }
    
    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
    	if (tag.hasKey("emitWhenEmpty")) {
    		this.emitWhenLoaderEmpty = tag.getBoolean("emitWhenEmpty");
    	}
    	if (tag.hasKey("emitwhenfull")) {
    		this.emitWhenCartFull = tag.getBoolean("emitwhenfull");
    	}
    	super.handleUpdateTag(tag);
    }
    
    private void handleRedstone () {
    	System.out.println(this.emitWhenLoaderEmpty);
		this.isEmittingRedstone = false;
		if (invIsEmpty(itemStackHandler) && this.emitWhenLoaderEmpty) {
			this.isEmittingRedstone = true;
		}
		if (this.cartIsFull && this.emitWhenCartFull) {
			this.isEmittingRedstone = true;
		}
		getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockLoader.enabled, this.isEmittingRedstone));
	}
    
    @Override
    public void dropItems() {
    	DropItemsFromInv(itemStackHandler);
    }
}
