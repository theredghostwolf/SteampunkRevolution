package ghostwolf.steampunkrevolution.tileentities;

import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.blocks.BlockSteamOven;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSpawnParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntitySteamOven extends TileEntityMachineBase {

	
	private FluidTank tank;
	private progressBar[] bars;
	
	private class progressBar {
		private int slot;
		private int max;
		private int current = 0;
		
		public progressBar (int slot, int max) {
			this.slot = slot;
			this.max = max;
		}
		
		public boolean updateBar () {
			if (this.current >= this.max) {
				this.current = 0;
				return true;
			} else {
				this.current++;
				return false;
			}
		}
	}
	
	private int usage;
	private int speed;
	private int progress;
	
	public boolean isActive = false;
	
	public static final int inputSlots = 4;
	public static final int outputSlots = inputSlots;

	public TileEntitySteamOven () {
		this.tank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		//amount of ticks it takes to proccess items
		this.speed = Config.steamOvenSpeed;
		//current amount of ticks progressed
		this.progress = 0;
		//amount of steam drained per tick per slot
		this.usage = Config.steamOvenUsage;
		
		this.isActive = false;
		
		this.bars = new progressBar[inputSlots];
		for (int i = 0; i < inputSlots; i++) {
			this.bars[i] = new progressBar(i, this.speed);
		}
		
	}
	
	@Override
	public void update() {	
		if (!getWorld().isRemote) {
			this.isActive = false;
			for (int i = 0; i < InputItemStackHandler.getSlots(); i++) {
				ItemStack input = InputItemStackHandler.getStackInSlot(i);
				if (this.ItemIsFood(input) || ItemIsFood(FurnaceRecipes.instance().getSmeltingResult(input)) && this.itemIsSmeltAble(input) ) {
					if (this.checkIfSlotHasSpace(OutputItemStackHandler, i, FurnaceRecipes.instance().getSmeltingResult(input))) {
						if (this.tank.getFluid() != null && this.tank.getFluid().getFluid() == FluidRegistry.getFluid("steam") && this.tank.getFluidAmount() >= this.usage ) {
							this.tank.drain(new FluidStack(FluidRegistry.getFluid("steam"), this.usage), true);
							if (this.bars[i].updateBar()) {
								smeltItem(i);
							}
							this.isActive = true;
						} 
					} 
				} 
			}
			if (this.isActive) {
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockSteamOven.enabled, true));
			} else {
				getWorld().setBlockState(this.getPos(), getWorld().getBlockState(this.getPos()).withProperty(BlockSteamOven.enabled, false));
			}
			
		} 
	}
	
	private void smeltItem (int slot) {
		ItemStack input = InputItemStackHandler.getStackInSlot(slot);
		if (! input.isEmpty()) {
			ItemStack res = FurnaceRecipes.instance().getSmeltingResult(input);
			if (! res.isEmpty()) {
				ItemStack remainder = OutputItemStackHandler.insertItem(slot, res, true);
				if (remainder.isEmpty()) {		
					OutputItemStackHandler.insertItem(slot, res.copy(), false);
					InputItemStackHandler.getStackInSlot(slot).shrink(1);
					this.createSmoke(20);
				}
			}
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
	
	
	// This item handler will hold our nine inventory slots
    private ItemStackHandler InputItemStackHandler = new ItemStackHandler(inputSlots) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntitySteamOven.this.markDirty();
        }
        
    };
    
    private ItemStackHandler OutputItemStackHandler = new ItemStackHandler (outputSlots) {
    	protected void onContentsChanged(int slot) {
    		TileEntitySteamOven.this.markDirty();
    	};
    	
    	
    };
    
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
        	this.tank = this.tank.readFromNBT((NBTTagCompound) compound.getTag("tank"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Initems", InputItemStackHandler.serializeNBT());
        compound.setTag("Outitems", OutputItemStackHandler.serializeNBT());
        compound.setTag("tank", this.tank.writeToNBT(new NBTTagCompound()));
        return compound;
    }
     

 
    public void dropItems () {
    	DropItemsFromInv(InputItemStackHandler);
    	DropItemsFromInv(OutputItemStackHandler);
    }
    
  
}
