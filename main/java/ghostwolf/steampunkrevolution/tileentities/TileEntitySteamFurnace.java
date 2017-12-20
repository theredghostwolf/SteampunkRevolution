package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockSteamFurnace;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntitySteamFurnace extends TileEntityMachineBase {
	
	private boolean isActive;
	
	private int progress;
	private int speed;
	private int usage;
	
	private ItemStackHandler InputItemStackHandler = new ItemStackHandler (1) {
    	protected void onContentsChanged(int slot) {
    		TileEntitySteamFurnace.this.markDirty();
    	};
    	
    };
    private ItemStackHandler OutputItemStackHandler = new ItemStackHandler (1) {
    	protected void onContentsChanged(int slot) {
    		TileEntitySteamFurnace.this.markDirty();
    	};
    	
    };
    
    private FluidTank tank;
	
	public TileEntitySteamFurnace() {
		super();
		this.tank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		
		this.usage = Config.steamFurnaceUsage;
		this.speed = Config.steamFurnaceUsage;
		this.progress = 0;
		
		this.isActive = false;
	}
	
	@Override
	public void update() {
		if (! getWorld().isRemote) {
			boolean isProgressing = false;
			if (this.itemIsSmeltAble(InputItemStackHandler.getStackInSlot(0))) {
				if (this.checkIfSlotHasSpace(OutputItemStackHandler, 0, FurnaceRecipes.instance().getSmeltingResult(InputItemStackHandler.getStackInSlot(0)))) {
					if (this.checkIfTankHasEnough(this.tank, new FluidStack(FluidRegistry.getFluid("steam"), this.usage))) {
						tank.drain(new FluidStack(FluidRegistry.getFluid("steam"), this.usage) , true);
						isProgressing = true;
						if (this.progress >= this.speed) {
							OutputItemStackHandler.insertItem(0,FurnaceRecipes.instance().getSmeltingResult(InputItemStackHandler.getStackInSlot(0)).copy() , false);
							InputItemStackHandler.getStackInSlot(0).shrink(1);
							this.progress = 0;
							this.createSmoke(25);
						} else {
							this.progress++;
						}
					}
				}
			}
			if (isProgressing) {
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockSteamFurnace.enabled, true));
			} else {
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockSteamFurnace.enabled, false));	
			}
		}
	}
	
	@Override
	 public void dropItems () {
		DropItemsFromInv(InputItemStackHandler);
		DropItemsFromInv(OutputItemStackHandler);
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
			return (T) this.tank;
		}  else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == EnumFacing.DOWN) {
				return  CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(OutputItemStackHandler);
			} else {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(InputItemStackHandler);
			}
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		 
		super.writeToNBT(compound);
		 compound.setTag("Initems", InputItemStackHandler.serializeNBT());
	     compound.setTag("Outitems", OutputItemStackHandler.serializeNBT());
	     compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
	     return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("Initems")) {
            InputItemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("Initems"));
		}
		if (compound.hasKey("Outitems")) {
            OutputItemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("Outitems"));

		}
		if (compound.hasKey("tank")) {
			this.tank = this.tank.readFromNBT(compound.getCompoundTag("tank"));
		}
	}

}
