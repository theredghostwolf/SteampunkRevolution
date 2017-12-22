package ghostwolf.steampunkrevolution.entities;

import java.util.ArrayList;
import java.util.List;

import ghostwolf.steampunkrevolution.entities.ai.EntityAIRobotExtractItem;
import ghostwolf.steampunkrevolution.entities.ai.EntityAIRobotFuel;
import ghostwolf.steampunkrevolution.entities.ai.EntityAIRobotInsertItem;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamOven;
import ghostwolf.steampunkrevolution.util.AccessPoint;
import ghostwolf.steampunkrevolution.util.InventoryHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class EntityRobot extends EntityCreature implements IEntityAdditionalSpawnData {
	
	public static final int interactRange = 2;
	private int range = 10;
	private int invSize = 4;
	private int itemTransferSpeed = 1;
	private int maxAccessPoints = 10;
	private float moveSpeed = 0.4F;
	private int attackDamage = 5;
	private int hp = 20;
	private int fluidTransferRate = 100;
	
	private int tankCapacity = 8;
	public FluidTank steamTank;
	public float refuelTreshhold = 0.1F;
	
	private int damageTick = 0;
	private int operateCost = 1;
	
	private BlockPos home;
	
	private InventoryHelper invHelper = new InventoryHelper();
	private AccessPoint accesspoint = new AccessPoint();
	
	public List<AccessPoint> ExtractPoints = new ArrayList<AccessPoint>();
	public List<AccessPoint> InsertPoints = new ArrayList<AccessPoint>();;
	public List<AccessPoint> refuelPoints = new ArrayList<AccessPoint>();;
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2F);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.8D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6);
		
	}
	
	public EntityRobot(World worldIn) {
		super(worldIn);	
		setupAI(worldIn); 
		initTank();
	}
	
	public EntityRobot(World worldIn, BlockPos home) {
			super(worldIn);
			setupAI(worldIn);
			initTank();
			if (home != null && home != BlockPos.ORIGIN) {
				this.setHomePosAndDistance(home, this.getRange());
			}		
	}
	
	protected void setupAI (World worldIn) {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIRobotFuel(this, worldIn));
		this.tasks.addTask(2, new EntityAIRobotExtractItem(this, worldIn));
		this.tasks.addTask(3, new EntityAIRobotInsertItem(this, worldIn));
		this.tasks.addTask(4, new EntityAIWander(this, 0.25));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6));
		this.tasks.addTask(6, new EntityAILookIdle(this));
	}
	
	protected void initTank () {
		this.steamTank = new FluidTank (Fluid.BUCKET_VOLUME * this.tankCapacity);
	}
	
    private ItemStackHandler ItemStackHandler = new ItemStackHandler(invSize) { };
	
	@Override
	public boolean hasHome() {
		if (this.home != null && home != BlockPos.ORIGIN) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public float getAIMoveSpeed() {
		return this.moveSpeed;
	}
	
	@Override
	public float getMaximumHomeDistance() {
			return getRange();
	}
	
	public void addInsertPoint (BlockPos p, EnumFacing f) {
		boolean exists = false;
		int id = 0;
		
		for (int i = 0; i < this.InsertPoints.size(); i++) {
			if (p.equals(this.InsertPoints.get(i).pos) && f == this.InsertPoints.get(i).facing) {
				exists = true;
				id = i;
			}
		}
		if (!exists) {
			if ( this.InsertPoints.size() < maxAccessPoints) {
				this.InsertPoints.add(new AccessPoint(p,f));
			}
		} else {
			this.InsertPoints.remove(id);
		}
		
	}
	
	public void addExtractPoint(BlockPos p, EnumFacing f) {
		boolean exists = false;
		int id = 0;
		
		for (int i = 0; i < this.ExtractPoints.size(); i++) {
			if (p.equals(this.ExtractPoints.get(i).pos) && f == this.ExtractPoints.get(i).facing) {
				exists = true;
				id = i;
			}
		}
		
		
		if (!exists ) {
			if ( this.ExtractPoints.size() < maxAccessPoints) {
				this.ExtractPoints.add(new AccessPoint(p,f));
			}
		} else {
			this.ExtractPoints.remove(id);
		}
	}
	
	public void addFuelPoint (BlockPos p, EnumFacing f) {
		boolean exists = false;
		int id = 0;
		
		for (int i = 0; i < this.refuelPoints.size(); i++) {
			if (p.equals(this.refuelPoints.get(i).pos) && f == this.refuelPoints.get(i).facing) {
				exists = true;
				id = i;
			}
		}
		
		if (!exists ) {
			if (this.refuelPoints.size() < maxAccessPoints) {
				this.refuelPoints.add(new AccessPoint(p,f));
			}
		} else {
			this.refuelPoints.remove(id);
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("insert")) {
			this.InsertPoints = readAccessPointListFromNBT((NBTTagCompound) compound.getTag("insert"));

		}
		if (compound.hasKey("extract")) {
			this.ExtractPoints = readAccessPointListFromNBT((NBTTagCompound) compound.getTag("extract"));

		}
		if (compound.hasKey("fuel")) {
			this.refuelPoints = readAccessPointListFromNBT((NBTTagCompound) compound.getTag("fuel"));

		}
		if (compound.hasKey("items")) {
			this.ItemStackHandler.deserializeNBT(compound.getCompoundTag("items"));
		}
		if (compound.hasKey("homeX") && compound.hasKey("homeY") && compound.hasKey("homeZ")) {
			this.setHomePosAndDistance(new BlockPos( compound.getInteger("homeX"), compound.getInteger("homeY"), compound.getInteger("homeZ") ), this.getRange()); 
		}
		if (compound.hasKey("tank")) {
			this.steamTank = steamTank.readFromNBT((NBTTagCompound) compound.getTag("tank"));
		}
		super.readEntityFromNBT(compound);
		
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setTag("tank", this.steamTank.writeToNBT(new NBTTagCompound()));

		compound.setTag("insert", writeAccessPointListToNBT(new NBTTagCompound (), this.InsertPoints));
		compound.setTag("extract", writeAccessPointListToNBT(new NBTTagCompound (), this.ExtractPoints));
		compound.setTag("fuel", writeAccessPointListToNBT(new NBTTagCompound (), this.refuelPoints));
		compound.setTag("items", this.ItemStackHandler.serializeNBT());
		
		BlockPos homepos = this.getHomePosition();
		compound.setInteger("homeX", homepos.getX());
		compound.setInteger("homeY", homepos.getY());
		compound.setInteger("homeZ", homepos.getZ());
		
		super.writeEntityToNBT(compound);
		
	}
	
	private NBTTagCompound writeAccessPointListToNBT (NBTTagCompound tag, List<AccessPoint> list) {
		tag.setInteger("size", list.size());
		for (int i = 0; i < list.size(); i++) {
			NBTTagCompound p = new NBTTagCompound();
			p.setInteger("facing", list.get(i).facing.ordinal());
			p.setInteger("x", list.get(i).pos.getX());
			p.setInteger("y", list.get(i).pos.getY());
			p.setInteger("z", list.get(i).pos.getZ());
			tag.setTag("point:" + Integer.toString(i), p);
		}
		
		return tag;
	}
	
	private List<AccessPoint> readAccessPointListFromNBT (NBTTagCompound tag) {
	  List<AccessPoint> l =  new ArrayList<AccessPoint>();
	  for (int i = 0; i < tag.getInteger("size"); i++) {
		NBTTagCompound t = tag.getCompoundTag("point:" + Integer.toString(i));
		BlockPos p = new BlockPos(t.getInteger("x"),t.getInteger("y"),t.getInteger("z"));
		EnumFacing f = EnumFacing.VALUES[t.getInteger("facing")];
		l.add(new AccessPoint(p,f));
	}
	  
	  return l;
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
 	
	@Override
	public void onDeath(DamageSource cause) {
		this.dropItems();
		super.onDeath(cause);
	}
	
	public List<IItemHandler> getInventories (List<AccessPoint> list) {
		List<IItemHandler> inventoryList = new ArrayList<IItemHandler>();
		for (int i = 0; i < list.size(); i++) {
			AccessPoint p = list.get(i);
			if (p != null) {
				if (p.pos != null && p.facing != null) {
					TileEntity te = world.getTileEntity(p.pos);
					if (te != null) {
						if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, p.facing)) {
							inventoryList.add(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, p.facing));
						}
					}
				} else {
					System.out.println("corrupted access point");
				}
			}
		}
		return inventoryList;
	}
	
	private List<TileEntity> getTileEntities (List<AccessPoint> list) {
		List<TileEntity> TileEntityList = new ArrayList<TileEntity>();
		for (int i = 0; i < list.size(); i++) {
			AccessPoint p = list.get(i);
			if (p != null) {
				if (p.pos != null && p.facing != null) {
					TileEntity te = world.getTileEntity(p.pos);
					if (te != null) {
						if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, p.facing)) {
							TileEntityList.add(te);
						}
					}
				} else {
					System.out.println("corrupted access point");
				}
			}
		}
		return TileEntityList;
	}

	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		handleSteam();
		if (invHelper.inventoryHasItem(this.getInventory())) {
			for (int i = 0; i < this.getInventory().getSlots(); i++) {
				if (! this.getInventory().getStackInSlot(i).isEmpty()) {
					this.setHeldItem(EnumHand.MAIN_HAND, this.getInventory().getStackInSlot(i));
				
					break;
				}
			}
		}
	
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		BlockPos pos = this.getHomePosition();
		buffer.writeInt(pos.getX());
		buffer.writeInt(pos.getY());
		buffer.writeInt(pos.getZ());
		
		buffer.writeInt(this.ExtractPoints.size());
		buffer.writeInt(this.InsertPoints.size());
		buffer.writeInt(this.refuelPoints.size());
		
		buffer = accesspoint.writeAccessPointListToBuf(this.ExtractPoints, buffer);
		buffer = accesspoint.writeAccessPointListToBuf(this.InsertPoints, buffer);
		buffer = accesspoint.writeAccessPointListToBuf(this.refuelPoints, buffer);
	}

	@Override
	public void readSpawnData(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		
		this.setHomePosAndDistance(new BlockPos(x,y,z), this.getRange());
		
		int size = buf.readInt();
		int size2 = buf.readInt();
		int size3 = buf.readInt();
		
		this.ExtractPoints = accesspoint.readAccessPointListFromBuf(buf, size );
		this.InsertPoints = accesspoint.readAccessPointListFromBuf(buf, size2);
		this.refuelPoints = accesspoint.readAccessPointListFromBuf(buf, size3);
	}
	
	@Override
	public boolean hitByEntity(Entity entityIn) {
		if (entityIn instanceof EntityPlayer) {
		EntityPlayer p = (EntityPlayer) entityIn;
			if (p.getHeldItemMainhand().getItem() == ModItems.robotwrench) {
				this.dropItems();
				this.setDead();
			}
		}
		return super.hitByEntity(entityIn);
	}
	
	private void dropItems () {
		invHelper.DropItemsFromInv(getItemStackHandler(), getEntityWorld(), this.getPosition());

	}
	
	public IItemHandler getInventory () {
		return this.getItemStackHandler();
	}

	public int getItemTransferSpeed() {
		return itemTransferSpeed;
	}

	public void setItemTransferSpeed(int itemTransferSpeed) {
		this.itemTransferSpeed = itemTransferSpeed;
	}

	public IItemHandler getItemStackHandler() {
		return ItemStackHandler;
	}

	public void setItemStackHandler(ItemStackHandler itemStackHandler) {
		ItemStackHandler = itemStackHandler;
	}
	
	@Override
	public boolean isAIDisabled() {
		return false;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getFluidTransferRate() {
		return fluidTransferRate;
	}

	public void setFluidTransferRate(int fluidTransferRate) {
		this.fluidTransferRate = fluidTransferRate;
	}
	
	private void handleSteam () {
		if (this.isServerWorld()) {
		FluidStack steam = this.steamTank.getFluid();
		if (steam != null) {
			this.steamTank.drain(this.operateCost, true);
		} else {
			if (this.damageTick <= 0) {
				//damage
				this.damageEntity(DamageSource.STARVE, 1);
				this.damageTick = 20;
			} else {
				this.damageTick--;
			}
		}
		}
	}
	
	
}
