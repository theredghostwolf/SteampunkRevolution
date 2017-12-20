package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import scala.collection.parallel.BucketCombiner;

public class TileEntityRainTank extends TileEntity implements ITickable, IFluidHandler {
	
	private FluidTank tank;
	private int rainStrengthAmp;
	private int size = Config.rainTankBuckets;
	
	public TileEntityRainTank () {
		this.tank = new FluidTank(size * Fluid.BUCKET_VOLUME);
		this.rainStrengthAmp = Config.rainTankAmp;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return this.tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return this.tank.drain(maxDrain, doDrain);
	}

	@Override
	public void update() {
		if (! getWorld().isRemote) {
			if (getWorld().isRaining() && getWorld().canBlockSeeSky(new BlockPos(this.pos.getX(),this.pos.getY() + 1,this.pos.getZ()))) {
				
				
				
				int filled = this.tank.fill(new FluidStack(FluidRegistry.WATER, (int) (getWorld().rainingStrength * this.rainStrengthAmp)), false);
				
				this.tank.fill(new FluidStack(FluidRegistry.WATER, filled), true);
			}
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
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock() != newState.getBlock());
	}
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	super.writeToNBT(compound);
    	this.tank.writeToNBT(compound);
    	return compound;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	// TODO Auto-generated method stub
    	super.readFromNBT(compound);
    	this.tank.readFromNBT(compound);
    }
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			if (facing != EnumFacing.UP) {
				return (T) this.tank;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
