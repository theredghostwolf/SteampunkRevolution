package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockResinExtractor;
import ghostwolf.steampunkrevolution.blocks.BlockResinSolidifier;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSpawnParticle;
import ghostwolf.steampunkrevolution.recipe.SolidifierRecipe;
import ghostwolf.steampunkrevolution.recipe.SolidifierRecipeRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityResinSolidifier extends TileEntityMachineBase {
	
	FluidTank resintank;
	FluidTank steamtank;
	
	private int progress;
	private int speed;
	private int usage;
	
	//this will have to be replaced with a recipe system at some point, maybe
	private static ItemStack output = new ItemStack(Items.COAL);
	
	public boolean isActive;
	
	private static int cost = 100;
	
	public TileEntityResinSolidifier() {
		this.resintank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		this.steamtank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		
		this.isActive = false;
		this.cost = Config.resinSolidifierConversionRate;
		this.progress = 0;
		this.speed = Config.resinSolidifierSpeed;
		this.usage = Config.resinSolidifierUsage;
	}
	
	@Override
	public void update() {
		if (!getWorld().isRemote) {
		FluidStack ss = this.steamtank.getFluid();
		FluidStack rs = this.resintank.getFluid();
		boolean isProcessing = false;
		if (ss != null && rs != null) {
			SolidifierRecipe recipe = SolidifierRecipeRegistry.findRecipeForStack(rs);
			if (recipe != null && ss.getFluid() == FluidRegistry.getFluid("steam") && ss.amount >= this.usage && checkIfSlotHasSpace(ItemStackHandler, 0, recipe.outputItemStack())) {
				isProcessing = true;
				this.steamtank.drain(new FluidStack(FluidRegistry.getFluid("steam"), this.usage), true);
				if (this.progress >= this.speed) {
					ItemStackHandler.insertItem(0, recipe.outputItemStack(), false);
					this.resintank.drain(recipe.getCost(), true);
					this.createSmoke(20);
					this.progress = 0;
				} else {
					this.progress++;
				}
				
			} 
		}
		if (isProcessing) {
			setActive(true);
		} else {
			setActive(false);
		}
		}
	}
	
	private void setActive(boolean a) {
		if (a) {
			this.isActive = true;
			getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockResinSolidifier.enabled, true));
		} else {
			this.isActive = false;
			getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockResinSolidifier.enabled, false));
		}
	}
	
	 private ItemStackHandler ItemStackHandler = new ItemStackHandler (1) {
	    	protected void onContentsChanged(int slot) {
	    		TileEntityResinSolidifier.this.markDirty();
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
			if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
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
	 
	 public void dropItems () {
	    	DropItemsFromInv(ItemStackHandler);
	    	
	    }

}
