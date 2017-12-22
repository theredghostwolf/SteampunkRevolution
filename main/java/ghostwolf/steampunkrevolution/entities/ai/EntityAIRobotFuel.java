package ghostwolf.steampunkrevolution.entities.ai;

import java.util.ArrayList;

import ghostwolf.steampunkrevolution.entities.EntityRobot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class EntityAIRobotFuel extends EntityAIRobotBase {
	
	private boolean isRefueling = false;
	
	public EntityAIRobotFuel(EntityRobot robot, World world) {
		super(robot, world);
		setMutexBits(1);
	}
	
	@Override
	public void updateTask() {
		if (this.refuelTarget != null) {
			if (this.robot.getDistance(this.refuelTarget.pos.getX(), this.refuelTarget.pos.getY(), this.refuelTarget.pos.getZ()) <= this.robot.interactRange) {
				this.isRefueling = true;
				IFluidHandler tank = this.refuelTarget.tank;
				FluidStack drained = tank.drain(new FluidStack(FluidRegistry.getFluid("steam"), this.robot.getFluidTransferRate()), false);
				int filled = this.robot.steamTank.fill(drained, false);
				
				FluidStack drained1 = tank.drain(new FluidStack(FluidRegistry.getFluid("steam"), filled), true);
				this.robot.steamTank.fill(drained1, true);
			
			} else {
				this.isRefueling = false;
				BlockPos p = findAirBlockNearRefuelTarget();
				this.robot.getNavigator().tryMoveToXYZ(p.getX(),p.getY(),p.getZ(), 1F);
			}
		}
	}
	
	@Override
	public boolean shouldExecute() {
		this.getRefuelTanks();
		FluidTank tank = this.robot.steamTank;
		if (tank.getFluid() == null || ((float) tank.getFluidAmount() /  (float)tank.getCapacity()) <= this.robot.refuelTreshhold) {

			for (int i = 0; i < this.refuelTargetList.size(); i++) {
				FluidStack s = this.refuelTargetList.get(i).tank.drain(new FluidStack(FluidRegistry.getFluid("steam"),this.robot.getFluidTransferRate() ), false);
				if (s != null) {
					if (s.amount > 0) {
					return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		this.findRefuelTarget();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		getRefuelTanks();
		findRefuelTarget();
		if (this.refuelTarget == null) {
			return false;
		}
		if (this.robot.steamTank.getFluidAmount() >= this.robot.steamTank.getCapacity() * 0.95) {
			return false;
		}
		if (this.refuelTarget.tank.drain(new FluidStack(FluidRegistry.getFluid("steam"),this.robot.getFluidTransferRate() ), false ).amount <= 0) {
			return false;
		}
		TileEntity te = world.getTileEntity(this.refuelTarget.pos);
		if (te == null) {
			return false;
		}
		if (! te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this.refuelTarget.facing)) {
			return false;
		}
		
		return true;
		
	}
	
	@Override
	public boolean isInterruptible() {
		return !this.isRefueling;
	}
	
	@Override
	public void resetTask() {
		this.refuelTarget = null;
		this.refuelTargetList = new ArrayList<refuelTarget>();
	}

}
