package ghostwolf.steampunkrevolution.entities.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.util.AccessPoint;
import ghostwolf.steampunkrevolution.util.InventoryHelper;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EntityAIRobotExtractItem extends EntityAIRobotBase {
	
	private boolean isExtracting;
	
	public EntityAIRobotExtractItem (EntityRobot robot, World world) {
		super(robot, world);
		setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		getExtractInventories();
		for (int i = 0; i < this.targetList.size(); i++) {
			IItemHandler inv = this.targetList.get(i).inv;
			for (int j = 0; j < inv.getSlots(); j++) {
				ItemStack item = inv.getStackInSlot(j);
				if (invHelper.InventoryHasRoomForItem(this.robot.getItemStackHandler(), item)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void updateTask () {
		if (this.target != null) {
		if (this.robot.getDistance(this.target.pos.getX(), this.target.pos.getY(), this.target.pos.getZ()) <= this.robot.interactRange) {
			this.isExtracting = true;
			for (int i = 0; i < this.target.inv.getSlots(); i++) {
				boolean transferedItem = false;
				ItemStack stack = this.target.inv.getStackInSlot(i);
				if (! stack.isEmpty()) {
					ItemStack extracted = this.target.inv.extractItem(i, this.robot.getItemTransferSpeed(), true);
					for (int j = 0; j < this.robot.getInventory().getSlots(); j++) {
						ItemStack remainder = this.robot.getInventory().insertItem(j, extracted, true);
						if (remainder.isEmpty()) {
							ItemStack extracted1 = this.target.inv.extractItem(i, this.robot.getItemTransferSpeed(), false);
							ItemStack remainder1 = this.robot.getInventory().insertItem(j, extracted1,false );
							transferedItem = true;
							break;
						} else if (remainder.getCount() < extracted.getCount()) {
							ItemStack extracted1 = this.target.inv.extractItem(i, remainder.getCount(), false);
							ItemStack remainder1 = this.robot.getInventory().insertItem(j, extracted1,false );
							transferedItem = true;
							break;
						}
						
						
					}
					if (transferedItem) {
						break;
					}
				}
			}
			
		} else {
				this.isExtracting = false;
				BlockPos p = findAirBlockNearTarget();
				this.robot.getNavigator().tryMoveToXYZ(p.getX(),p.getY(),p.getZ(), 1F);
			
			}
		}
	}
	
	@Override
	 public void startExecuting() {
		findExtractTarget();
		
	}
	 
	@Override
	 public void resetTask()  {
	     this.target = null;
	     this.targetList = new ArrayList<Target>();
	     this.isExtracting = false;
	 }
	 
	@Override
	 public boolean shouldContinueExecuting() {
		getExtractInventories();
		findExtractTarget();
		if (this.target != null) {
			 TileEntity e = world.getTileEntity(target.pos);
			 if (e != null) {
				 if (e.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this.target.facing)) {
					 return true;
				 } else {
					return false;
				 }
			 } else {
				 return false;
			 }
		} else {
			return false;
		}
	
	 }
	
	@Override
	public boolean isInterruptible() {
		return !this.isExtracting;
	}
	
	
}
