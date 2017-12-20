package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSpawnParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineBase extends TileEntity implements ITickable {
	
	public void createSmoke (int amount) {
		if (Config.enableSmoke) {
			PacketHandler.INSTANCE.sendToAll(new PacketSpawnParticle(this.pos.getX() + 0.5, this.pos.getY() + 1.1, this.pos.getZ() + 0.5, EnumParticleTypes.CLOUD.getParticleID(), amount, 0.3F,0F,0.3F,0F));
		}
	}
	
	public boolean checkIfTankHasEnough (FluidTank tank, FluidStack resource) {
		if (tank.getFluid() != null) {
			if (tank.getFluid().isFluidEqual(resource)) {
				if (tank.getFluidAmount() >= resource.amount) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean tankHasSpace (FluidTank tank, FluidStack stack) {
		int filled = tank.fill(stack, false);
		if (filled == stack.amount) {
			return true;
		}
		return false;
	}
	
	public boolean checkIfSlotHasSpace (IItemHandler inventory, int slot, ItemStack stack) {
		if (! stack.isEmpty()) {
			ItemStack remainder = inventory.insertItem(slot, stack, true);
			if (remainder.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean itemIsSmeltAble (ItemStack item) {
		if (! item.isEmpty()) {
			ItemStack res = FurnaceRecipes.instance().getSmeltingResult(item);
			if (! res.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean ItemIsFood (ItemStack item) {
		if (item.getItem() instanceof ItemFood) {
			return true;
		}
		return false;
	}
	
	public TileEntityMachineBase() {
		
	}
	
	@Override
	public void update() {
		
	}
	
	public void dropItems () {
		
	}
	
	public boolean isFuel (ItemStack stack) {
		if (! stack.isEmpty()) {
			int fuelVal = TileEntityFurnace.getItemBurnTime(stack);
			int oreFuelVal = ForgeEventFactory.getItemBurnTime(stack);
			if (fuelVal > 0 || oreFuelVal > 0) {
				return true;
			}
		}
		return false;
	}
	
	public int getFuelValue (ItemStack stack) {
		if (! stack.isEmpty()) {
			int burnTime =  ForgeEventFactory.getItemBurnTime(stack);
			if (burnTime > 0) {
				return burnTime;
			}
			burnTime = TileEntityFurnace.getItemBurnTime(stack);
			if (burnTime > 0) {
				return burnTime;
			}
		}
		return 0;
	}
	
	  @Override
		public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
			return (oldState.getBlock() != newState.getBlock());
		}
	  
		public boolean hasOreDictEntry (ItemStack input, String name) {
			if (! input.isEmpty()) {
				int[] ids = OreDictionary.getOreIDs(input);
				for (int i = 0; i < ids.length; i++) {
					if (OreDictionary.getOreName(ids[i]) == name) {
						return true;
					}
				}
			}
			
			return false;
		}
		
		public boolean invIsEmpty (IItemHandler inv) {
			for (int i = 0; i < inv.getSlots(); i++) {
				if (! inv.getStackInSlot(i).isEmpty()) {
					return false;
				}
			}
			return true;
		}
		
		public boolean checkIfInventoryHasSpace (IItemHandler inv, ItemStack stack) {
			for (int i = 0; i < inv.getSlots(); i++) {
				ItemStack remainder = inv.insertItem(i, stack, true);
				if (remainder.isEmpty()) {
					return true;
				}
			}
			return false;
		}
		
		
		public ItemStack getFirstStackInInv (IItemHandler inv) {
			for (int i = 0; i < inv.getSlots(); i++) {
				if (! inv.getStackInSlot(i).isEmpty()) {
					return inv.getStackInSlot(i);
				}
			}
			return ItemStack.EMPTY;
		}
		
		public ItemStack extractFirstStackFromInv (IItemHandler inv, int amount) {
			for (int i = 0; i < inv.getSlots(); i++) {
			 ItemStack extracted = inv.extractItem(i, amount, true);
			 if (! extracted.isEmpty()) {
				 return inv.extractItem(i, amount, false);
			 }
			}
			return ItemStack.EMPTY;
		}
		
		public ItemStack insertStackInInvetory (IItemHandler inv, ItemStack stack) {
			for (int i = 0; i < inv.getSlots(); i++) {
				stack = inv.insertItem(i, stack, false);
			}
			return stack;
		}
		
		public void DropItemsFromInv (IItemHandler inv) {
			for (int i = 0; i < inv.getSlots(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				if (!s.isEmpty()) {
					getWorld().spawnEntity(new EntityItem(getWorld(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), s));
				}
			}
		}
		
		 @Override
		    public void handleUpdateTag(NBTTagCompound tag) {
		    	readFromNBT(tag);
		   	  }
		    
		    @Override
		    public NBTTagCompound getUpdateTag() {
		    	return writeToNBT(new NBTTagCompound());
		    }
		    
		    @Override
		    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		    	super.onDataPacket(net, pkt);
		    	handleUpdateTag(pkt.getNbtCompound());
		    }
		 
		    @Override
		    public SPacketUpdateTileEntity getUpdatePacket() {
		         return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
		    }
	

}
