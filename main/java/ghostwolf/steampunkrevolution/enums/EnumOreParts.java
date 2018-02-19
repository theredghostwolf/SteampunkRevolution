package ghostwolf.steampunkrevolution.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumOreParts implements IStringSerializable{
	Chunks(1,"chunksbase");
	
	int id = 1;
	String basePart =  "";
	
	EnumOreParts (int id, String part) {
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
