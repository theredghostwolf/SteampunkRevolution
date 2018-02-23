package ghostwolf.steampunkrevolution.items.mech;

import java.util.List;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMechanoidChassis extends Item {
	
	//id, name,tier, arm, leg, head, core, engine, tank, storage, upgrade
	public final Chassis[] chassisList = {
			new Chassis(0,"copperchassis",1,
					2,2,
					2,2,
					1,1,
					1,1,
					1,1,
					1,2,
					1,2,
					0,2)
	};

	public ItemMechanoidChassis() {
		 
		setRegistryName("mechanoidchassis");
	     setUnlocalizedName(Reference.MOD_ID + ":mechanoidchassis");
	     setHasSubtypes(true);
	     setCreativeTab(ModItems.SteampunkItemsTab);
	     
	}
	
	public class Chassis {
		
		private String name;
		private int tier;
		private int id;
		
		//max and minimum amount of slots
		private int armSlots;
		private int armMin;
		
		private int legMin;
		private int legSlots;
		
		private int headMin;
		private int headSlots;
		
		private int coreMin;
		private int coreSlots;
		
		private int engineMin;
		private int engineSlots;
		
		private int tankMin;
		private int tankSlots;
		
		private int storageMin;
		private int storageSlots;
		
		private int upgradeMin;
		private int upgradeSlots;
		
		public Chassis (int id, String name, int tier,
				int minArms, int arms,
				int minLegs,int legs,
				int minHeads, int heads,
				int minCores, int cores,
				int minEngines, int engines,
				int minTanks, int tanks,
				int minStorages, int storages,
				int minUpgrades, int upgrades) {
			
			this.id = id;
			this.name = name;
			this.tier = tier;
			
			this.armSlots = arms;
			this.armMin = minArms;
			
			this.legMin = minLegs;
			this.legSlots = legs;
			
			this.headSlots = heads;
			this.headMin = minHeads;
			
			this.coreSlots = cores;
			this.coreMin = minCores;
			
			this.engineSlots = engines;
			this.engineMin = minEngines;
			
			this.tankSlots = tanks;
			this.tankMin = minTanks;
			
			this.storageSlots = storages;
			this.storageMin = minStorages;
			
			this.upgradeSlots = upgrades;
			this.upgradeMin = minUpgrades;
			
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getId () {
			return this.id;
		}
		
		public int getTier () {
			return this.tier;
		}
		
		public int getArms () {
			return this.armSlots;
		}
		
		public int getLegs() {
			return this.legSlots;
		}
		
		public int getHeads () {
			return this.headSlots;
		}
		
		public int getCores () {
			return this.coreSlots;
		}
		
		public int getEngines () {
			return this.engineSlots;
		}
		
		public int getTanks () {
			return this.tankSlots;
		}
		
		public int getStorages () {
			return this.storageSlots;
		}
		
		public int getUpgrades () {
			return this.upgradeSlots;
		}
		
		public int getMinArms () {
			return this.armMin;
		}
		
		public int getMinLegs() {
			return this.legMin;
		}
		
		public int getMinHeads () {
			return this.headMin;
		}
		
		public int getMinCores () {
			return this.coreMin;
		}
		
		public int getMinEngines () {
			return this.engineMin;
		}
		
		public int getMinTanks () {
			return this.tankMin;
		}
		
		public int getMinStorages () {
			return this.storageMin;
		}
		
		public int getMinUpgrades () {
			return this.upgradeMin;
		}
	}
	
	public Chassis getChassis (int id) {
		for (int i = 0; i < chassisList.length; i++) {
			if (chassisList[i].getId() == id) {
				return chassisList[i];
			}
		}
		return null;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int d = stack.getItemDamage();
		for (Chassis p : chassisList) {
			if (p.getId() == d) {
				return "item." + getRegistryName() +  "." + p.getName();
			}
		}
		return null;
	}
	
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (itemIn == ModItems.SteampunkItemsTab) {
			for (Chassis p : chassisList) {
				tab.add(new ItemStack(this,1,p.getId()));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		for (Chassis p : chassisList) {
			ModelLoader.setCustomModelResourceLocation(this, p.getId(), new ModelResourceLocation(getRegistryName(), "type=" + p.getName()));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		Chassis c = getChassis(stack.getItemDamage());
		
		tooltip.add("Tier: " + Integer.toString(c.getTier()));
		
		tooltip.add("Arms: " + Integer.toString(c.getMinArms()) + "-" + Integer.toString(c.getArms()));
		tooltip.add("Legs: " + Integer.toString(c.getMinLegs()) + "-" + Integer.toString(c.getLegs()));
		tooltip.add("Heads: " + Integer.toString(c.getMinHeads()) + "-" + Integer.toString(c.getHeads()));
		tooltip.add("Cores: " + Integer.toString(c.getMinCores()) + "-" + Integer.toString(c.getCores()));
		tooltip.add("Engines: " + Integer.toString(c.getMinEngines()) + "-" + Integer.toString(c.getEngines()));
		tooltip.add("Storages: " + Integer.toString(c.getMinStorages()) + "-" + Integer.toString(c.getStorages()));
		tooltip.add("Tanks: " + Integer.toString(c.getMinTanks()) + "-" + Integer.toString(c.getTanks()));
		tooltip.add("Upgrades: " + Integer.toString(c.getMinUpgrades()) + "-" + Integer.toString(c.getUpgrades()));
	
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
}
