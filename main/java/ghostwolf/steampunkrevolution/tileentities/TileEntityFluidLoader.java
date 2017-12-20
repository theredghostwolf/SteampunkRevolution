package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockBoiler;
import ghostwolf.steampunkrevolution.blocks.BlockFluidLoader;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityFluidLoader extends TileEntityMinecartMachineBase {
	
	public boolean cartIsFull = false;
	public int speed = Config.tankcartLoaderSpeed;
	public boolean emitWhenLoaderEmpty = true;
	public boolean emitWhenCartFull = true;
	public boolean emitWhenCartNotSupported = true;
	public boolean isEmittingRedstone = false;
	public boolean wrongCart = false;
	
	
	private FluidTank tank;
	
	public TileEntityFluidLoader() {
		super();
		this.tank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
	}
	
	@Override
	public void update() {
		if (! getWorld().isRemote) {
			EntityMinecart cart = this.getCartInfront(EntityMinecart.class);
			if (cart != null) {
				if (cart.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, getPullFacing())) {
					this.wrongCart = false;
					IFluidHandler cartTank = cart.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, getPullFacing());
					if (cartTank != null) {
						if ( this.tank.getFluidAmount() > 0) {
							transferFluid(cartTank);
						}
						
					}
				} else {
					this.wrongCart = true;
				}
				handleRedstone();
			}
			
		}
	}
	
	private void transferFluid (IFluidHandler cartTank) {
		FluidStack drained = this.tank.drain(speed, false);
		int filled = cartTank.fill(drained, false);
		if (filled > 0) {
			cartIsFull = false;
			drained = this.tank.drain(new FluidStack(drained.getFluid(), filled),true);
			cartTank.fill(drained, true);
		} else {
			cartIsFull = true;
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) this.tank;
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	
    	 super.writeToNBT(compound);
    	 compound.setTag("tank", this.tank.writeToNBT(new NBTTagCompound()));
    	 compound.setBoolean("emitOnInvEmpty", this.emitWhenLoaderEmpty);
    	 compound.setBoolean("emitCartFull", this.emitWhenCartFull);
    	 return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	  if (compound.hasKey("tank")) {
    		  this.tank = this.tank.readFromNBT(compound.getCompoundTag("tank"));
          }
    	  if (compound.hasKey("emitOnInvEmpty")) {
    		  this.emitWhenLoaderEmpty = compound.getBoolean("emitOnInvEmpty");
    	  }
    	  if (compound.hasKey("emitCartFull")) {
    		  this.emitWhenCartFull = compound.getBoolean("emitCartFull");
    	  }
    }
    
    private void handleRedstone () {
		this.isEmittingRedstone = false;
		if (this.tank.getFluidAmount() <= 0 && this.emitWhenLoaderEmpty) {
			this.isEmittingRedstone = true;
		}
		if (this.cartIsFull && this.emitWhenCartFull) {
			this.isEmittingRedstone = true;
		}
		if (this.wrongCart && this.emitWhenCartNotSupported) {
			this.isEmittingRedstone = true;
		}
		getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockFluidLoader.enabled, this.isEmittingRedstone));
	}
	
}
