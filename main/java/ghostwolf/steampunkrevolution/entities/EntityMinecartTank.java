package ghostwolf.steampunkrevolution.entities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.init.ModBlocks;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class EntityMinecartTank extends EntityMinecart {
	
	FluidTank tank;
	private int size = Config.tankCartBuckets;
	
	public EntityMinecartTank(World worldIn) {
		super(worldIn);
		this.tank = new FluidTank(Fluid.BUCKET_VOLUME * size);
	}
	
	public EntityMinecartTank(World worldIn, double x, double y, double z) {
		super(worldIn,x,y,z);
		this.tank = new FluidTank(Fluid.BUCKET_VOLUME * size);
	}

	@Override
	public Type getType() {
		return null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) this.tank;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setTag("tank", this.tank.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
			super.readEntityFromNBT(compound);
			if (compound.hasKey("tank")) {
				this.tank = tank.readFromNBT((NBTTagCompound) compound.getTag("tank"));
			}
	}
	
	 public IBlockState getDefaultDisplayTile()
	    {
	        return ModBlocks.raintank.getDefaultState();
	    }
	
	

}
