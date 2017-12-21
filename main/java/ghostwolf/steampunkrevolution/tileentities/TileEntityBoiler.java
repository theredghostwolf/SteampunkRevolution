package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockBoiler;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import ghostwolf.steampunkrevolution.init.ModFluids;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSpawnParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityBoiler extends TileEntityMachineBase {

	public boolean isActive = false;
	
	public static final int size = 1;
	
	private int fuel = 0;
	private float fuelAmp = Config.boilerFuelAmp;
	
	private float heat = 0F;
	private float heatCap = Config.boilerMaxHeat;
	private float heatUpRate = Config.boilerHeatUpRate;
	private float cooldownRate = Config.boilerCooldownRate;
	
	private int baseSpeed =	Config.boilerBaseConversion;
	private float maxSpeedAmp = Config.boilerMaxConversionAmp;
	
	private int conversionPoint = Config.boilerConversionPoint;
	
	
	private FluidTank inputTank;
	private FluidTank outputTank;
	
	public TileEntityBoiler () {
		this.inputTank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		this.outputTank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
	}

	@Override
	public void update() {
		if (! getWorld().isRemote) {
			handleHeat();
			handleFuel();
			handleActive();
			
			FluidStack fluid = this.inputTank.getFluid();
			if (fluid != null && fluid.getFluid() == FluidRegistry.WATER) {
				if (this.fuel <= 0) {
					consumeFuel();
				}
				handleSteam();
				
			}
		}
	}
	
	private void handleHeat () {
		if (this.fuel > 0) {
			if (this.heat < this.heatCap) {
				this.heat += this.heatUpRate;
				TileEntityBoiler.this.markDirty();
			} else if (this.heat > this.heatCap) {
				this.heat = this.heatCap;
				TileEntityBoiler.this.markDirty();
			}
		} else {
			if ( this.heat > 0) {
				this.heat -= this.cooldownRate;
				TileEntityBoiler.this.markDirty();
			} else if (this.heat < 0) {
				this.heat = 0;
				TileEntityBoiler.this.markDirty();
			}
		}
	}
	
	private void handleFuel () {
		if (this.fuel > 0) {
			this.fuel--;
			TileEntityBoiler.this.markDirty();
		}
	}
	
	private void consumeFuel () {
		ItemStack input = this.itemStackHandler.getStackInSlot(0);
		if (!input.isEmpty()) {
			int fuelVal = this.getFuelValue(input);
			if (fuelVal > 0) {
				this.fuel += Math.round(fuelVal * this.fuelAmp);
				input.shrink(1);
			}
		}
	}
	
	private void handleActive () {
		if (this.heat > this.conversionPoint || this.fuel > 0) {
			if (! this.isActive) {
				this.isActive = true;
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockBoiler.enabled, true));
			}
		} else {
			if (this.isActive) {
				this.isActive = false;
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockBoiler.enabled, false));
			}
		}
	}
	
	private void handleSteam () {
		if (this.heat >= this.conversionPoint) {
			int rate = Math.round(this.baseSpeed * (1 + (this.maxSpeedAmp * ((this.heat - this.conversionPoint) / (this.heatCap - this.conversionPoint)))));
			
			FluidStack drained = this.inputTank.drain(new FluidStack(FluidRegistry.WATER, rate), false);
			int filled = this.outputTank.fill(new FluidStack(FluidRegistry.getFluid("steam"), drained.amount), false);
		
			this.inputTank.drain(new FluidStack(FluidRegistry.WATER, filled), true);
			this.outputTank.fill(new FluidStack(FluidRegistry.getFluid("steam"), filled), true);
			
			this.createSmoke(1 + Math.round(filled / 15), ((this.heat - this.conversionPoint) / (this.heatCap - this.conversionPoint)) * 0.3F);
			TileEntityBoiler.this.markDirty();
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			if (facing ==  EnumFacing.UP) {
				return (T) this.outputTank;
			} else {
				return (T) this.inputTank;
			}
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	// This item handler will hold our nine inventory slots
    private ItemStackHandler itemStackHandler = new ItemStackHandler(size) {
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileEntityBoiler.this.markDirty();
        }
    };
    

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        if (compound.hasKey("inputtank")) {
        	this.inputTank = this.inputTank.readFromNBT((NBTTagCompound) compound.getTag("inputtank"));
        }
        if (compound.hasKey("outputtank")) {
        	this.outputTank = this.outputTank.readFromNBT((NBTTagCompound) compound.getTag("outputtank"));
        }
        if (compound.hasKey("heat")) {
        	this.heat = compound.getFloat("heat");
        }
        if (compound.hasKey("fuel")) {
        	this.fuel = compound.getInteger("fuel");
        }
  
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inputtank", this.inputTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("outputtank", this.outputTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("items", itemStackHandler.serializeNBT());
        compound.setFloat("heat", this.heat);
        compound.setInteger("fuel", this.fuel);
        return compound;
    }
    
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
    
    public void dropItems () {
    	DropItemsFromInv(itemStackHandler);
    }
    
    public float getHeat () {
    	return this.heat;
    }
    
    public float getMaxHeat () {
    	return this.heatCap;
    }

	
}
