package ghostwolf.steampunkrevolution.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum EnumMaterial implements IStringSerializable {
	//ingots
	ingotCopper("ingotCopper"),
	ingotTin("ingotTin"),
	ingotLead("ingotLead"),
	ingotZinc("ingotZinc"),
	ingotSilver("ingotSilver"),
	ingotTungsten("ingotTungsten"),
	ingotTitanium("ingotTitanium"),
	
	ingotSteel("ingotSteel"),
	ingotBrass("ingotBrass"),
	ingotElectrum("ingotElectrum"),
	ingotBronze("ingotBronze"),
	ingotBlacksteel("ingotBlacksteel"),
	//dusts
	dustCopper("dustCopper"),
	dustTin("dustTin"),
	dustLead("dustLead"),
	dustSilver("dustSilver"),
	dustZinc("dustZinc"),
	dustTitanium("dustTitanium"),
	dustTungsten("dustTungsten"),
	
	dustSteel("dustSteel"),
	dustBrass("dustBrass"),
	dustElectrum("dustElectrum"),
	dustBronze("dustBronze"),
	dustBlacksteel("dustBlacksteel"),
	//chunks
	chunkCopper("chunkCopper"),
	chunkTin("chunkTin"),
	chunkLead("chunkLead"),
	chunkSilver("chunkSilver"),
	chunkZinc("chunkZinc"),
	chunkTitanium("chunkTitanium"),
	chunkTungsten("chunkTungsten"),
	
	steamBowl("","As I faced my death, steam was whirling around me. I saw it, felt it, and then I knew, science is power."),
	amberCrystal(3200),
	driedResin(1600);
		
	String oredictEntry = "";
	int fuelValue = 0;
	String tooltip = "";
	
	EnumMaterial () {
	}
	
	EnumMaterial(String oreName) {
		this.oredictEntry = oreName;
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

}
