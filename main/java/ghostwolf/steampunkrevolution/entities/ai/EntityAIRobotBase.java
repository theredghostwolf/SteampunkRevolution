package ghostwolf.steampunkrevolution.entities.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.util.AccessPoint;
import ghostwolf.steampunkrevolution.util.InventoryHelper;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EntityAIRobotBase extends EntityAIBase {
	
	public InventoryHelper invHelper = new InventoryHelper();
	
	public EntityRobot robot;
	public World world;
	
	public List<Target> targetList;
	
	public Target target;
	
	public EntityAIRobotBase (EntityRobot robot, World world) {
		this.robot = robot;
		this.world = world;
	}

	@Override
	public boolean shouldExecute() {
		return false;
	}
	
	public void getExtractInventories () {
		this.targetList = new ArrayList<Target>();
		for (int i = 0; i < this.robot.ExtractPoints.size(); i++) {
			AccessPoint p = this.robot.ExtractPoints.get(i);
			TileEntity te = this.world.getTileEntity(p.pos);
			if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, p.facing)) {
				IItemHandler inv = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, p.facing);
				this.targetList.add(new Target(p.pos, te, inv, p.facing, this.robot.getDistance(p.pos.getX(), p.pos.getY(), p.pos.getZ())));
			}
		}
		Collections.sort(this.targetList, new Comparator<Target>() {
		    @Override
		    public int compare(Target lhs, Target rhs) {
		        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
		        return lhs.distance < rhs.distance ? -1 : (lhs.distance > rhs.distance) ? 1 : 0;
		    }
		});
	}
	
	public void getInsertInventories () {
		this.targetList = new ArrayList<Target>();
		for (int i = 0; i < this.robot.InsertPoints.size(); i++) {
			AccessPoint p = this.robot.InsertPoints.get(i);
			TileEntity te = this.world.getTileEntity(p.pos);
			if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, p.facing)) {
				IItemHandler inv = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, p.facing);
				this.targetList.add(new Target(p.pos, te, inv, p.facing, this.robot.getDistance(p.pos.getX(), p.pos.getY(), p.pos.getZ())));
			}
		}
		Collections.sort(this.targetList, new Comparator<Target>() {
		    @Override
		    public int compare(Target lhs, Target rhs) {
		        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
		        return lhs.distance < rhs.distance ? -1 : (lhs.distance > rhs.distance) ? 1 : 0;
		    }
		});
	}
	
	public class Target {
		
		BlockPos pos;
		TileEntity te;
		IItemHandler inv;
		EnumFacing facing;
		double distance;
		
		public Target (BlockPos pos, TileEntity te, IItemHandler inv, EnumFacing facing, double distance) {
			this.pos = pos;
			this.te = te;
			this.inv = inv;
			this.facing = facing;
			this.distance = distance;
		}
	}
	
	public void findExtractTarget () {
		this.target = null;
		boolean foundTarget = false;
		for (int i = 0; i < this.targetList.size(); i++) {
			IItemHandler inv = this.targetList.get(i).inv;
			for (int j = 0; j < inv.getSlots(); j++) {
				ItemStack item = inv.getStackInSlot(j);
				if (invHelper.InventoryHasRoomForItem(this.robot.getItemStackHandler(), item)) {
					this.target = this.targetList.get(i);
					foundTarget = true;
					break;
				}
			}
			if (foundTarget) {
				break;
			}
		}
	}
	
	public void findInsertTarget () {
		this.target = null;
		IItemHandler robotInv = this.robot.getInventory();
		if (invHelper.inventoryHasItem(robotInv)) {
			for (int i = 0; i < this.targetList.size(); i++) {
				boolean foundTarget = false;
				IItemHandler inv = this.targetList.get(i).inv;
				for (int j = 0; j < robotInv.getSlots(); j++) {
					if (! robotInv.getStackInSlot(i).isEmpty()) {
						if (invHelper.InventoryHasRoomForItem(inv,robotInv.getStackInSlot(i) )) {
							this.target = this.targetList.get(i);
							foundTarget = true;
							break;
						}
					}
				}
				if (foundTarget) {
					break;
				}
			}
		}
	}
	
	public BlockPos findAirBlockNearTarget () {
		if (this.target != null) {
			List<BlockPos> airblocks = new ArrayList<BlockPos>();
			if (this.world.isAirBlock(this.target.pos.north())) {
				airblocks.add(this.target.pos.north());
			} if (this.world.isAirBlock(this.target.pos.south())) {
				airblocks.add(this.target.pos.south());
			}  if (this.world.isAirBlock(this.target.pos.west())) {
				airblocks.add(this.target.pos.west());
			}  if (this.world.isAirBlock(this.target.pos.east())) {
				airblocks.add(this.target.pos.east());
			} 
			if (airblocks.isEmpty()) {
				return this.target.pos;
			} else {
				Random rand = new Random();
				return airblocks.get(rand.nextInt(airblocks.size()));
			}
			
		}
		return null;
	}

}
