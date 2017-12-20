package ghostwolf.steampunkrevolution.entities.ai;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.entities.ai.EntityAIRobotBase.Target;
import ghostwolf.steampunkrevolution.util.AccessPoint;
import ghostwolf.steampunkrevolution.util.InventoryHelper;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EntityAIRobotInsertItem extends EntityAIRobotBase {
	
	private boolean isInserting = false;
	
	public EntityAIRobotInsertItem (EntityRobot robot, World world) {
		super(robot, world);
		setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		getInsertInventories();
		IItemHandler robotInv = this.robot.getInventory();
		if (invHelper.inventoryHasItem(robotInv)) {
			
			for (int i = 0; i < this.targetList.size(); i++) {
				IItemHandler inv = this.targetList.get(i).inv;
				for (int j = 0; j < robotInv.getSlots(); j++) {
					
					if (! robotInv.getStackInSlot(j).isEmpty()) {
						if (invHelper.InventoryHasRoomForItem(inv,robotInv.getStackInSlot(j) )) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public void updateTask () {


		if (this.target != null) {

			if (this.robot.getDistance(this.target.pos.getX(), this.target.pos.getY(), this.target.pos.getZ()) <= this.robot.interactRange) {
				this.isInserting = true;
				IItemHandler robotInv = this.robot.getInventory();
				boolean transferedItem = false;
				for (int i = 0; i < robotInv.getSlots(); i++) {
					
					if (! robotInv.getStackInSlot(i).isEmpty()) {
						ItemStack extracted = robotInv.extractItem(i, this.robot.getItemTransferSpeed(), true);
						for (int j = 0; j < this.target.inv.getSlots(); j++) {
							ItemStack remainder = this.target.inv.insertItem(j, extracted, true);
							if (remainder.isEmpty()) {
								ItemStack extracted1 = robotInv.extractItem(i, this.robot.getItemTransferSpeed(), false);
								ItemStack remainder1 = this.target.inv.insertItem(j, extracted1, false);
								transferedItem = true;
								break;
							} else if (remainder.getCount() < extracted.getCount()) {
								ItemStack extracted1 =robotInv.extractItem(i, remainder.getCount(), false);
								ItemStack remainder1 = this.target.inv.insertItem(j, extracted1,false );
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
				this.isInserting = false;
				BlockPos p = findAirBlockNearTarget();
				this.robot.getNavigator().tryMoveToXYZ(p.getX(),p.getY(),p.getZ(), 1F);
				}
			}
		
	}
	
	@Override
	 public void startExecuting() {
		findInsertTarget();
		
	}
	
	@Override
	 public void resetTask()  {
	     this.target = null;
	     this.isInserting = false;
	     this.targetList = new ArrayList<Target>();
	 }
	
	@Override
	 public boolean shouldContinueExecuting() {
		getInsertInventories();
		findInsertTarget();
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
		return !this.isInserting;
		
	}

}
