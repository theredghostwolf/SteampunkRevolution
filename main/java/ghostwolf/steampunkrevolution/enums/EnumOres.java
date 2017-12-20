package ghostwolf.steampunkrevolution.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumOres implements IStringSerializable{
	Copper(1.5F, 1) , Tin(1.5F, 1), Lead(1.5F, 2), Zinc (2,2), Silver(2,2), Titanium(2.5F, 3), Tungsten(2.5F, 3);
	
	float hardness = 1;
	int harvestLevel = 1;
	
	 EnumOres (float hardness, int harvestLevel) {
		this.hardness = hardness;
		this.harvestLevel = harvestLevel;
	}
	
	@Override
	public String getName() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
	
	public int getMeta () {
		return this.ordinal();
	}
	
	public float getHardness () {
		return this.hardness;
	}
	
	public int getHarvestLevel () {
		return this.harvestLevel;
	}
	
	
}
