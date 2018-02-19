package ghostwolf.steampunkrevolution.init;


import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.items.ItemBrassGoggles;
import ghostwolf.steampunkrevolution.items.ItemCart;
import ghostwolf.steampunkrevolution.items.ItemMaterial;
import ghostwolf.steampunkrevolution.items.ItemMetal;
import ghostwolf.steampunkrevolution.items.ItemRobotWrench;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoid;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoidChassis;
import ghostwolf.steampunkrevolution.items.mech.ItemMechanoidPart;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

	
public class ModItems {
	
	public static ArmorMaterial Brass_Goggles_armor = EnumHelper.addArmorMaterial("brass_goggles", Reference.MOD_ID + ":brass_goggles", 200, new int[] {0, 0, 0, 0}, 40, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0);
	
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":robotwrench")
    public static ItemRobotWrench robotwrench;
    
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":material")
    public static ItemMaterial material;
    
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":cart")
    public static ItemCart cart;
    
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":brassgoggles")
    public static ItemBrassGoggles brassgoggles;
    
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":mechanoidpart")
    public static ItemMechanoidPart mechanoidpart;
    
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":mechanoidchassis")
    public static ItemMechanoidChassis mechanoidchassis;
    
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":mechanoid")
    public static ItemMechanoid mechanoid;
    
    @GameRegistry.ObjectHolder(Reference.MOD_ID + ":metal")
    public static ItemMetal metal;
	
	public static void init () {

	}
	
	 @SideOnly(Side.CLIENT)
	 public static void initModels() {
		 material.initModel();
		 robotwrench.initModel();
		 cart.initModel();
		 brassgoggles.initModel();
		 mechanoidpart.initModel();
		 mechanoidchassis.initModel();
		 mechanoid.initModel();
		 metal.initModel();
	 }
	 
	 
	 public static final CreativeTabs SteampunkItemsTab = new CreativeTabs("SteampunkItemsTab") {
		    
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ModItems.robotwrench);
		};
	};
	
	public static void initOreDict () {
		material.addToOreDict();
		metal.addToOreDict();
	};
	
	
	


}
