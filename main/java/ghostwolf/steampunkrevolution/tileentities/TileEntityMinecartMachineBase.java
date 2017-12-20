package ghostwolf.steampunkrevolution.tileentities;

import java.util.List;

import ghostwolf.steampunkrevolution.blocks.BlockMachineBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityMinecartMachineBase extends TileEntityMachineBase {
	
	public EntityMinecart getCartInfront (Class<? extends EntityMinecart> type) {
		
		
		EnumFacing f = getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.facing);
		AxisAlignedBB bb = new AxisAlignedBB(getPos());
		
		switch (f) {
		case UP:
			bb = new AxisAlignedBB(this.pos, this.pos.add(1, 2, 1));
			break;
		case DOWN:
			bb = new AxisAlignedBB(this.pos, this.pos.add(1, -1, 1));
			break;
		case NORTH:
			bb = new AxisAlignedBB(this.pos, this.pos.add(1, 1, -1));
			break;
		case SOUTH:
			bb = new AxisAlignedBB(this.pos, this.pos.add(1, 1, 2));
			break;
		case WEST:
			bb = new AxisAlignedBB(this.pos, this.pos.add(-1, 1, 1));
			break;
		case EAST:
			bb = new AxisAlignedBB(this.pos, this.pos.add(2, 1, 1));
			break;
		}
		
		 List<EntityMinecart> l =  getWorld().getEntitiesWithinAABB(type, bb);
		 if (! l.isEmpty()) {
			 return l.get(0);
		 }
		 return null;
	}
	
	public EnumFacing getPullFacing () {
		return getWorld().getBlockState(getPos()).getValue(BlockMachineBase.facing).getOpposite();
	}
	
	public IItemHandler getCartInv (EntityMinecart cart) {
		
		if (cart != null) {
			if (cart.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))  {
				return cart.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			}
		}
		return null;
	}
	
}
	

