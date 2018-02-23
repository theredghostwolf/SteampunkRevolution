package ghostwolf.steampunkrevolution.tileentities;

import java.util.ArrayList;
import java.util.List;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoidChassis;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoidChassis.Chassis;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoidPart;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoidPart.EnumType;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoidPart.Part;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityAltar extends TileEntityDisplay {
	
	private int pedestalRange = Config.pedestalRange;
	private int pedestalHeightRange = Config.pedestalHeightRange;
	
	public void Craft () {
		ItemStack stack = this.getStack();
		
		if (stack.getItem() instanceof ItemMechanoidChassis) {
			
			ItemMechanoidChassis i = (ItemMechanoidChassis) stack.getItem();
			Chassis c = i.getChassis(stack.getItemDamage());
			
			
			
			List<ItemStack> l = getItems(c.getTier(), false);
			
			int arms = 0;
			int legs = 0;
			int heads = 0;
			
			int cores = 0;
			int engines = 0;
			
			int tanks = 0;
			int storages = 0;
			int upgrades = 0;
			
			for (int j = 0; j < l.size(); j++) {
				ItemStack s = l.get(j);
				if (s.getItem() instanceof ItemMechanoidPart) {
					ItemMechanoidPart p = (ItemMechanoidPart) s.getItem();
					Part part = p.getPart(s.getItemDamage());
					
					switch (part.getType()) {
					case arm:
						arms++;
						break;
					case leg:
						legs++;
						break;
					case head:
						heads++;
						break;
					case core:
						cores++;
						break;
					case engine:
						engines++;
						break;
					case tank:
						tanks++;
						break;
					case storage:
						storages++;
						break;
					case upgrade:
						upgrades++;
						break;
					default:
						break;
					}
				}
			}
			
			if (
					arms > c.getArms() ||
					legs > c.getLegs() ||
					heads > c.getHeads() ||
					cores > c.getCores() ||
					engines > c.getEngines() ||
					tanks > c.getTanks() ||
					storages > c.getStorages() ||
					upgrades > c.getUpgrades() ||
					
					
					arms < c.getMinArms() ||
					legs < c.getMinLegs() ||
					heads < c.getMinHeads() ||
					cores < c.getMinCores() ||
					engines < c.getMinEngines() ||
					tanks < c.getMinTanks() ||
					storages < c.getMinStorages() ||
					upgrades < c.getMinUpgrades() ) {
				//too many components or not enough
			} else {
				//assemble
				EntityLightningBolt bolt = new EntityLightningBolt(getWorld(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), true);
				bolt.setPosition(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ());
				getWorld().addWeatherEffect(bolt);
				
				getItems(c.getTier(), true);
				IItemHandler inv = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				inv.extractItem(0, 1, false);
				ItemStack output = new ItemStack(ModItems.mechanoid,1);
				output.setTagCompound(createCompound(l, c));
				inv.insertItem(0, output, false);
			}
			
		}
	}
	
	
	private NBTTagCompound createCompound(List<ItemStack> l, Chassis c) {
		NBTTagCompound tag = new NBTTagCompound();
		
		int tankSize = c.getTier() * 1000;
		int invSize = 0;
		int steamcost = 0;
		int hp = c.getTier() * 5;
		String core = "";
		
		for (int i = 0; i < l.size(); i++) {
			
			ItemStack s = l.get(i);
			ItemMechanoidPart item = (ItemMechanoidPart) s.getItem();
			Part part = item.getPart(s.getItemDamage());
			
			tankSize += part.getFuelStorage();
			invSize += part.getInvSize();
			steamcost += part.getFuelUsage();
			hp += part.getHp();
			
			if (part.getType() == EnumType.core) {
				core = part.getName();
			}
		}
		
		tag.setInteger("hp", hp);
		tag.setInteger("inv", invSize);
		tag.setInteger("tank", tankSize);
		tag.setInteger("cost", steamcost);
		tag.setString("core", core);
		
		return tag;
	}
	
	private List<ItemStack> getItems (int tier, boolean remove) {
		List<ItemStack> l = new ArrayList<ItemStack>();
		
		BlockPos p = this.getPos();
		BlockPos startPos = new BlockPos (p.getX() - pedestalRange, p.getY() - pedestalHeightRange, p.getZ() - pedestalRange);
		BlockPos endPos = new BlockPos(p.getX() + pedestalRange, p.getY() + pedestalHeightRange, p.getZ() + pedestalRange);
		
		//y axis
		for (int Y = 0; Y < endPos.getY() - startPos.getY(); Y++) {
			//z axis
			for (int Z = 0; Z < endPos.getZ() - startPos.getZ(); Z++) {
				//x axis
				for (int X = 0; X < endPos.getX() - startPos.getX(); X++) {
					TileEntity te = getWorld().getTileEntity(new BlockPos(startPos.getX() + X, startPos.getY() + Y, startPos.getZ() + Z));
					if (te != null && te instanceof TileEntityPedestal) {
						ItemStack s  = ((TileEntityPedestal) te).getStack();
						if (s.getItem() instanceof ItemMechanoidPart) {
							Part part = ((ItemMechanoidPart) s.getItem()).getPart(s.getItemDamage());
							if (part.getTier() <= tier) {
								l.add(s);
								if (remove) {
									IItemHandler inv =  te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
									((TileEntityPedestal) te).ConsumeItem();
								}
							}
						}
					}
				}
			}
		}
		
		return l;
	}
	
}
