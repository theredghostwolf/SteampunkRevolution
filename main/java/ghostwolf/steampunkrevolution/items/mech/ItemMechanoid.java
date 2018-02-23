package ghostwolf.steampunkrevolution.items.mech;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ItemMechanoid extends Item {
	
	public ItemMechanoid() {
		setRegistryName("mechanoid");
	     setUnlocalizedName(Reference.MOD_ID + ":mechanoid");
	     setCreativeTab(ModItems.SteampunkItemsTab);
	     this.maxStackSize = 1;
	}
	
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	 private NBTTagCompound getTagCompoundSafe(ItemStack stack) {
	        NBTTagCompound tagCompound = stack.getTagCompound();
	        if (tagCompound == null) {
	            tagCompound = new NBTTagCompound();
	            stack.setTagCompound(tagCompound);
	        }
	        return tagCompound;
	    }
	 
	 
	 @Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		 if (! worldIn.isRemote) {
			 
			 NBTTagCompound tag = player.getHeldItem(hand).getTagCompound();
			 
			 int tankSize = 1000;
			 int invSize = 0;
			 int cost = 1;
			 int hp = 5; 
			 String core = "";
		
			 
			 if (tag.hasKey("tank")) {
				 tankSize = tag.getInteger("tank");
			 }
			 if (tag.hasKey("inv")) {
				 invSize = tag.getInteger("inv");
			 }
			 if (tag.hasKey("cost")) {
				 cost = tag.getInteger("cost");
			 }
			 if (tag.hasKey("hp")) {
				 hp = tag.getInteger("hp");
			 }
			 if (tag.hasKey("core")) {
				 core = tag.getString("core");
			 }
			 
			 
			 IFluidHandler f =  player.getHeldItem(hand).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			 EntityRobot r = new EntityRobot(worldIn,pos, tankSize, invSize, cost, hp, core,player.getHeldItem(hand).copy());
			 r.setCustomNameTag(player.getHeldItem(hand).getDisplayName());
			 r.setPosition(pos.getX(), pos.getY() + 1.2, pos.getZ());
			 
			 IFluidHandler rf = r.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			 rf.fill(f.drain(new FluidStack(FluidRegistry.getFluid("steam"), 1000), false), true);
			 worldIn.spawnEntity(r);
			 
			 player.getHeldItem(hand).shrink(1);
		 }
		 
		 return  EnumActionResult.SUCCESS;
	 }
	 
	    @Override
	    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt)
	    {
	 
	    	return new FluidHandlerMechanoid(stack, 1000);
	    }
	    
	    @Override
	    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	    	
	    	NBTTagCompound tag =  stack.getTagCompound();
	    	
	    	 if (tag.hasKey("tank")) {
	    		 tooltip.add("FuelStorage: " + Integer.toString(tag.getInteger("tank")) + " mb");
			 }
			 if (tag.hasKey("inv")) {
				 tooltip.add("Storage Slots: " + Integer.toString(tag.getInteger("inv")));
			 }
			 if (tag.hasKey("cost")) {
				 tooltip.add("FuelUsage: " + Integer.toString(tag.getInteger("cost")) + " mb/t");
			 }
			 if (tag.hasKey("hp")) {
				 tooltip.add("Hp: " + Integer.toString(tag.getInteger("hp")));
			 }
			 if (tag.hasKey("core")) {
				 tooltip.add("Core: " + tag.getString("core"));
			 }
	    	
	    	
	    	super.addInformation(stack, worldIn, tooltip, flagIn);
	    }
}
