package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockResinExtractor;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSpawnParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityResinExtractor extends TileEntityMachineBase {
	
	private FluidTank steamtank;
	private FluidTank resintank;
	
	private boolean isActive;
	
	private int speed;
	private int usage;
	private int progress;
	
	private int convertRate;
	
	public TileEntityResinExtractor () {
		this.resintank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		this.steamtank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		
		this.progress = 0;
		this.speed = Config.resinExtractorSpeed;
		this.usage = Config.resinExtractorUsage;
		this.convertRate = Config.resinExtractorConversionRate;
		
		this.isActive = false;
	}
	
	  private ItemStackHandler ItemStackHandler = new ItemStackHandler (1) {
	    	protected void onContentsChanged(int slot) {
	    		TileEntityResinExtractor.this.markDirty();
	    	};
	    	
	    };
	
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
			if (facing == EnumFacing.UP) {
				return (T) this.resintank;
			} else {
				return (T) this.steamtank;
			}
		
		}  else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return  CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ItemStackHandler);
		
			
			
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		 compound.setTag("resintank", this.resintank.writeToNBT(new NBTTagCompound()));
		 compound.setTag("steamtank", this.steamtank.writeToNBT(new NBTTagCompound()));
		 compound.setTag("items", ItemStackHandler.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("resintank")) {
			this.resintank = this.resintank.readFromNBT(compound.getCompoundTag("resintank"));
		}
		if (compound.hasKey("steamtank")) {
			this.steamtank = this.steamtank.readFromNBT(compound.getCompoundTag("steamtank"));
		}
		if (compound.hasKey("items")) {
			ItemStackHandler.deserializeNBT(compound.getCompoundTag("items"));

		}
	}
	

	@Override
	public void update() {
		if (! getWorld().isRemote) {
			boolean isProgressing = false;
			FluidStack inputF = steamtank.getFluid();
			if (inputF != null) {
				if (inputF.getFluid() == FluidRegistry.getFluid("steam") && inputF.amount >= this.usage && this.tankHasSpace(this.resintank, new FluidStack(FluidRegistry.getFluid("resin"), this.convertRate))) {
					ItemStack inputItem = ItemStackHandler.getStackInSlot(0);
					if (this.hasOreDictEntry(inputItem, "logWood")) {
						isProgressing = true;
						this.steamtank.drain(new FluidStack(FluidRegistry.getFluid("steam"), this.usage), true);
						if (this.progress >= this.speed) {
							this.resintank.fill(new FluidStack(FluidRegistry.getFluid("resin"), this.convertRate), true);
							ItemStackHandler.getStackInSlot(0).shrink(1);
							this.createSmoke(20);
							this.progress = 0;
						} else {
							this.progress++;
						}
					}
				}
			}
			if (isProgressing) {
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockResinExtractor.enabled, true));
			} else {
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockResinExtractor.enabled, false));
			}
		}
	}
	
	  public void dropItems () {
		  DropItemsFromInv(ItemStackHandler);
	    }

}
