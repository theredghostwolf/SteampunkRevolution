package ghostwolf.steampunkrevolution.items.mech;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import ghostwolf.steampunkrevolution.init.ModItems;
import it.unimi.dsi.fastutil.Arrays;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMechanoidPart extends Item {
		
	public final Part[] parts = {
		//copper base parts
		new Part("copperleg", 0,1,EnumType.leg, 0, 0, 0, 0, 0, 0.1F),
		new Part("copperarm", 1,1,EnumType.arm, 0, 0, 0, 1, 0, 0),
		new Part("copperhead",2,1,EnumType.head, 0, 0, 0, 0, 0, 0),
		
		//cores
		new Part("kabanericore",3,1,EnumType.core, 0, 0, 0, 0, 0, 0),
	
		//basics
		new Part("basicsteamengine",4,1,EnumType.engine,0,0,0,1,0,0.2F),
		new Part("basicstorage",5,1,EnumType.storage,1,0,0,0,0,0),
		new Part("basicfueltank",6,1,EnumType.tank,0,4000,0,0,0,0)
		
	};
	
	
	public ItemMechanoidPart () {
		 setRegistryName("mechanoidpart");
	     setUnlocalizedName(Reference.MOD_ID + ":mechanoidpart");
	     setHasSubtypes(true);
	     setCreativeTab(ModItems.SteampunkItemsTab);
	     
	}
	
	public class Part {
		
		private String name;
		private int id;
		private EnumType type;
		private int tier;
		
		private int storageSlots;
		private int fuelStorage;
		private int fuelUsage;
		private int itemTransferRate;
		private int fluidTransferRate;
		private float speed;
		
		public Part (String name, int id, int tier, EnumType type, int slots, int fuelS, int fuelU, int itemT, int fluidT, float s) {
			this.name =  name;
			this.id = id;
			this.tier = tier;
			this.type = type;
			
			this.storageSlots = slots;
			this.fuelStorage = fuelS;
			this.fuelUsage =  fuelU;
			this.itemTransferRate = itemT;
			this.fluidTransferRate = fluidT;
			this.speed = s;
		}
		
		public String getName () {
			return this.name;
		}
		
		public int getId () {
			return this.id;
		}
		
		public EnumType getType () {
			return this.type;
		}
		
		public int getTier () {
			return this.tier;
		}
		
		public int getHp () {
			return this.type.getHp() * this.tier;
		}
		
		public int getFuelStorage () {
			return this.fuelStorage;
		}
		
		public int getInvSize () {
			return this.storageSlots;
		}
		
		public int getFuelUsage () {
			return this.fuelUsage;
		}
	}
	
	public enum EnumType {
		arm(1), leg(1), head(2), core(2), tank(0), storage(0), engine(2), upgrade(0);
		
		private int hp;
		
		EnumType(int hp) {
			this.hp = hp;
		}
		
		public int getHp() {
			return this.hp;
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int d = stack.getItemDamage();
		for (Part p : parts) {
			if (p.getId() == d) {
				return "item." + getRegistryName() +  "." + p.getName();
			}
		}
		return null;
	}
	
	public Part getPart (int id) {
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].getId() == id) {
				return parts[i];
			}
		}
		return null;
	}
	
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (itemIn == ModItems.SteampunkItemsTab) {
			for (Part p : parts) {
				tab.add(new ItemStack(this,1,p.getId()));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		for (Part p : parts) {
			ModelLoader.setCustomModelResourceLocation(this, p.getId(), new ModelResourceLocation(getRegistryName(), "type=" + p.getName()));
		}
	}
	
	
	
	
	
}
