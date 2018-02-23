package ghostwolf.steampunkrevolution.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum EnumMaterial implements IStringSerializable {
	
	steamBowl("","As I faced my death, steam was whirling around me. I saw it, felt it, and then I knew, science is power."),
	amberCrystal(3200),
	driedResin(1600),
	kabaneriCore;
		
	String oredictEntry = "";
	int fuelValue = 0;
	String tooltip = "";
	int color = 0xffffff;
	String type = "";
	
	EnumMaterial () {
	}
	
	EnumMaterial(String oreName) {
		this.oredictEntry = oreName;
	}
	
	EnumMaterial(String oreName, int color) {
		this.oredictEntry = oreName;
		this.color = color;
	}
	
	EnumMaterial(String oreName, int color, String type) {
		this.oredictEntry = oreName;
		this.color = color;
		this.type = type;
	}
	
	
	EnumMaterial(String oreName, String ToolTip) {
		this.tooltip = ToolTip;
	}
	
	
	EnumMaterial (int fuelVal) {
		this.fuelValue = fuelVal;
	}

	
	public String getOredictEntry () {
		return this.oredictEntry;
	}
	
	@Override
	public String getName() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
	
	public int getMeta () {
		return this.ordinal();
	}
	
	public int getFuelValue () {
		return this.fuelValue;
	}
	
	public String getToolTip () {
		return this.tooltip;
	}
	
	public int getColor () {
		return this.color;
	}
	
	public String getType() {
		if (this.type == "") {
			return this.getName();
		} else {
			return this.type;
		}
	}

}
