package ghostwolf.steampunkrevolution.items;

import java.util.HashMap;
import java.util.Map;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.enums.EnumMaterial;
import ghostwolf.steampunkrevolution.enums.EnumMetalParts;
import ghostwolf.steampunkrevolution.enums.EnumMetals;
import ghostwolf.steampunkrevolution.enums.EnumOreParts;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMetal extends Item {
	
	private int id = 0;
	Map<String, mp> idMap = new HashMap<String, mp>();
	
	public void initIdMap () {
		for (EnumMetals m : EnumMetals.values()) {
			for (EnumMetalParts p : EnumMetalParts.values()) {
				idMap.put(Integer.toString(id), new mp (id, m, p, null));
				id++;
			}
			if (m.hasOre()) {
				for (EnumOreParts o : EnumOreParts.values()) {
					idMap.put(Integer.toString(id), new mp (id, m, null, o));
					id++;
				}
			}
		}
	}
	
	public int getColor (int i) {
		return idMap.get(Integer.toString(i)).getMetal().getColor();
	}
	
	public int getIdFor (EnumMetals m, EnumMetalParts p) {
		for (mp v : idMap.values()) {
			if (v.getMetal() == m && v.getMetalPart() == p) {
				return v.getId();
			}
		}
		return 0;
	}
	
	public int getIdFor (EnumMetals m, EnumOreParts o) {
		for (mp v : idMap.values()) {
			if (v.getMetal() == m && v.getOrePart() == o) {
				return v.getId();
			}
		}
		return 0;
	}
	
	private class mp {
		
		private int id = 0;
		private EnumMetals metal = EnumMetals.Copper;
		private EnumMetalParts metalPart;
		private EnumOreParts orePart;
		
		public mp (int id, EnumMetals metal, EnumMetalParts metalPart, EnumOreParts orePart) {
			this.id = id;
			this.metal = metal;
			this.metalPart = metalPart;
			this.orePart = orePart;
		}
		
		public int getId () {
			return this.id;
		}
		
		public EnumMetals getMetal() {
			return this.metal;
		}
		
		public EnumMetalParts getMetalPart () {
			return this.metalPart;
		}
		
		public EnumOreParts getOrePart () {
			return this.orePart;
		}
		
		public String getName () {
			if (this.metalPart != null) {
				return this.metalPart.getName() + this.metal.getName();
			} else {
				return this.orePart.getName() + this.metal.getName();
			}
		}
		
		public String getOreDictName () {
			String m = this.metal.getName().substring(0,1).toUpperCase() + this.metal.getName().substring(1).toLowerCase();
			if (this.metalPart != null) {
				return this.metalPart.getName() + m;
			} else {
				return this.orePart.getName() + m;
			}
		}
		
		public String getBasePart () {
			if (this.metalPart != null) {
				return this.metalPart.getBasePart();
			} else {
				return this.orePart.getBasePart();
			}
		}
	}
	
	public ItemMetal () {
		initIdMap();
		setRegistryName("metal");
		setUnlocalizedName(Reference.MOD_ID + ":metal");
		setHasSubtypes(true);
		setCreativeTab(ModItems.SteampunkItemsTab);
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
		for (mp p : idMap.values()) {
			ModelLoader.setCustomModelResourceLocation(this, p.getId() , new ModelResourceLocation(getRegistryName(), "type=" + p.getBasePart() ));

		}
		
	}
	
	
	
	public void addToOreDict () {
		for (mp p : idMap.values()) {
			ItemStack stack = new ItemStack(this, 1, p.getId());
			OreDictionary.registerOre(p.getOreDictName(), stack);				;
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + getRegistryName() +  "." + idMap.get(Integer.toString(stack.getItemDamage())).getName();
	}
	
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
		if (itemIn == ModItems.SteampunkItemsTab) {
			for (mp v : idMap.values() ) {
				tab.add(new ItemStack(this,1, v.getId()));
			}		
		}
	}

}
