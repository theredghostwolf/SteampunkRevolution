package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
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

public class TileEntityFluidUnloader extends TileEntityMinecartMachineBase {
	
	public boolean cartIsEmpty = false;
	public int speed = Config.tankcartUnloaderSpeed;
	public boolean emitWhenUnloaderFull = true;
	public boolean emitWhenCartEmpty = true;
	public boolean emitWhenCartNotSupported = true; //when a chest cart enters a fluidloader
	public boolean isEmittingRedstone = false;
	public boolean wrongCart = false;
	
	private FluidTank tank;
	
	public TileEntityFluidUnloader() {
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
						
						transferFluid(cartTank);
						
						
					}
				} else {
					this.wrongCart = true;
				}
				handleRedstone();
			}
			
		}
	}
	
	private void transferFluid (IFluidHandler cartTank) {
		
		FluidStack drained = cartTank.drain(speed, false);
		if (drained != null) {
		int filled = this.tank.fill(drained, false);
		if (filled > 0) {
			cartIsEmpty = false;
			drained = cartTank.drain(new FluidStack(drained.getFluid(), filled),true);
			this.tank.fill(drained, true);
			}
			
		}
		if (drained == null || drained.amount <= 0) {
			cartIsEmpty = true;
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
    	 compound.setBoolean("emitOnInvFull", this.emitWhenUnloaderFull);
    	 compound.setBoolean("emitCartEmpty", this.emitWhenCartEmpty);
    	 return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	super.readFromNBT(compound);
    	  if (compound.hasKey("tank")) {
    		  this.tank = this.tank.readFromNBT(compound.getCompoundTag("tank"));
          }
    	  if (compound.hasKey("emitOnInvFull")) {
    		  this.emitWhenUnloaderFull = compound.getBoolean("emitOnInvFull");
    	  }
    	  if (compound.hasKey("emitCartEmpty")) {
    		  this.emitWhenCartEmpty = compound.getBoolean("emitCartEmpty");
    	  }
    }
    
    private void handleRedstone () {
		this.isEmittingRedstone = false;
		if (this.tank.getFluidAmount() >= this.tank.getCapacity() && this.emitWhenUnloaderFull) {
			this.isEmittingRedstone = true;
		}
		if (this.cartIsEmpty && this.emitWhenCartEmpty) {
			this.isEmittingRedstone = true;
		}
		if (this.wrongCart && this.emitWhenCartNotSupported) {
			this.isEmittingRedstone = true;
		}
		getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockFluidLoader.enabled, this.isEmittingRedstone));
	}
}
