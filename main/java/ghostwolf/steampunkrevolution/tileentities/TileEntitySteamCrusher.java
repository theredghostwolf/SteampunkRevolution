package ghostwolf.steampunkrevolution.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpectralArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

public class TileEntitySteamCrusher extends TileEntity implements ITickable, IFluidHandler {

	private FluidTank tank;
	private int BreakCost = 2000;
	private int outputAmount = 2;
	private int speed;
	private int current;
	private String[] acceptedOres = {"Iron", "Gold", "Copper", "Tin", "Silver", "Nickel", "Platinum", "Aluminum", "Lead"};
	
	public  TileEntitySteamCrusher () {
		this.tank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
		this.speed = 20;
		this.current = 0;
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
			if (this.current >= this.speed) {
				this.current = 0;
			if (this.tank.getFluid() != null) {
				if (this.tank.getFluid().getFluid() != null && this.tank.getFluid().getFluid() ==  FluidRegistry.getFluid("steam")) {
					BlockPos t = new BlockPos (this.pos.getX(),this.pos.getY() - 1,this.pos.getZ());
					if (this.tank.getFluidAmount() >= BreakCost && !getWorld().isAirBlock(t)) {
						IBlockState ts = getWorld().getBlockState(t);
						Block tb = ts.getBlock();
						int m = ts.getBlock().getMetaFromState(ts);
						int[] ids =  OreDictionary.getOreIDs(new ItemStack(tb, 1 , m));
						
						Boolean brokeBlock = false;
						
						for (int i = 0; i < ids.length; i++) {
							String name = OreDictionary.getOreName(ids[i]);
							
							for (int x = 0; x < acceptedOres.length; x++) {
								if (name.equals("ore" + acceptedOres[x])) {
									TileEntity te = getWorld().getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ()));
									if (te != null) {
										if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
											IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
											NonNullList<ItemStack> l = OreDictionary.getOres("dust" + acceptedOres[x]);
										
											if (l != null) {
												for (int j = 0; j < ih.getSlots(); j++) {
													ItemStack returnStack = l.get(0);
													returnStack.setCount(outputAmount);
													ItemStack remainder = ih.insertItem(j, returnStack, true);
													if (remainder.isEmpty()) {
														
															ih.insertItem(j, returnStack, false);
															getWorld().destroyBlock(t, false);
															brokeBlock = true;
													break;
													}
												}
											
											}
										}
										
									}
								}
							}
						}
						if (brokeBlock) {
							this.tank.drain(new FluidStack(FluidRegistry.getFluid("steam"), BreakCost), true);
						}
					}
				}
			}
			} else {
				this.current++;
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
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			if (facing != EnumFacing.DOWN && facing != EnumFacing.UP) {
				return (T) this.tank;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.tank.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.tank.readFromNBT(compound);
	}

    @Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock() != newState.getBlock());
	}
}
