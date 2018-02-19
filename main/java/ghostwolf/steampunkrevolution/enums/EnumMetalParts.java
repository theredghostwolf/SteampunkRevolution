package ghostwolf.steampunkrevolution.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumMetalParts implements IStringSerializable{
	Ingot(1, "ingotbase"),
	Dust(2, "dustbase"),
	Plate(3, "platebase"),
	Gear(4, "gearbase"),
	Tinygear(5, "tinygearbase"),
	Stick(6, "rodbase"),
	Nugget(7, "nuggetbase");

	int id = 1;
	String basePart =  "";
	
	EnumMetalParts (int id, String part) {
		this.id = id;
		this.basePart = part;
	}
	
	@Override
	public String getName() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
	
	public int getId () {
		return this.id;
	}
	
	public String getBasePart () {
		return this.basePart;
	}
	
	

}
