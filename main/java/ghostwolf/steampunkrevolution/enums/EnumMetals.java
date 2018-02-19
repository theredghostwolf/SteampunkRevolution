package ghostwolf.steampunkrevolution.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumMetals implements IStringSerializable{
	
	//ores
	Copper(0,0xe56b51,1,1.5F, true),
	Tin(1,0xcdcbc9,1,1.5F, true),
	Lead(2,0xa3a6c3,1,1.5F, true),
	Zinc(3,0xb8b3ad,1,1.5F, true),
	Silver(4,0xe8e8e8,2,1.5F, true),
	Titanium(5,0xb1c5c4,3,2F, true),
	Tungsten(6,0x07005d,3,2F, true),
	
	//alloys
	Brass(7,0xf9d352,1,1.5F),
	Bronze(8,0xecb977,1,1.5F),
	Steel(9,0x64635f,2,1.7F),
	Electrum(10,0xfff182,1,1.5F),
	Blacksteel(11,0x555970,3,3F);
	
	int color = 0xffffff;
	boolean hasOre = false;
	float hardness = 1;
	int harvestLevel = 1;
	int id = 0;
	
	EnumMetals (int id) {
		this.id = id;
	};
	
	EnumMetals (int id, int color, int harvestLevel, float hardness) {
		this.color = color;
		this.harvestLevel = harvestLevel;
		this.hardness = hardness;
		this.id = id;
	}
	
	EnumMetals (int id, int color, int harvestLevel, float hardness, boolean hasOre) {
		this.color = color;
		this.harvestLevel = harvestLevel;
		this.hardness = hardness;
		this.hasOre = hasOre;
		this.id = id;
	}
	
	@Override
	public String getName() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
	
	public boolean hasOre () {
		return this.hasOre;
	}
	
	public float getHardness () {
		return this.hardness;
	}
	
	public int getHarvestLevel () {
		return this.harvestLevel;
	}
	
	public int getColor () {
		return this.color;
	}
	
	public int getId () {
		return this.id;
	}

}
