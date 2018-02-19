package ghostwolf.steampunkrevolution.items.mech;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMechanoidChassis extends Item {
	
	//id, name, arm, leg, head, core, engine, tank, storage, upgrade
	public final Chassis[] chassisList = {
			new Chassis(0,"copperchassis",1,2,2,1,1,1,2,2,2)
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
		
		private int armSlots;
		private int legSlots;
		private int headSlots;
		
		private int coreSlots;
		private int engineSlots;
		
		private int tankSlots;
		private int storageSlots;
		private int upgradeSlots;
		
		
		public Chassis (int id, String name, int tier, int arms, int legs, int heads, int cores, int engines, int tanks, int storages, int upgrades) {
			
			this.id = id;
			this.name = name;
			this.tier = tier;
			
			this.armSlots = arms;
			this.legSlots = legs;
			this.headSlots = heads;
			
			this.coreSlots = cores;
			this.engineSlots = engines;
			
			this.tankSlots = tanks;
			this.storageSlots = storages;
			this.upgradeSlots = upgrades;	
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
	
}
