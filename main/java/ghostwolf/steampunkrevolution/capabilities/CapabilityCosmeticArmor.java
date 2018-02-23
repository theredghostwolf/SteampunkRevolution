package ghostwolf.steampunkrevolution.capabilities;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import com.jcraft.jogg.Packet;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.containers.ContainerCosmeticArmor;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSyncCosmeticArmor;
import ghostwolf.steampunkrevolution.util.InventoryHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityCosmeticArmor extends ItemStackHandler {
	
	public static ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "CapabilityCosmeticArmor");
	
	@CapabilityInject(CapabilityCosmeticArmor.class)
	public static Capability<IItemHandler> Cosmetic_Armor_Capability = null;
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
	            event.addCapability(ID, new Provider() {
	            
	            });
	            
	            
	       }
	  }
	
	@SubscribeEvent
	public void handleDeath (PlayerEvent.Clone ev) {
		if (ev.getOriginal().hasCapability(Cosmetic_Armor_Capability, null) && ev.getEntityPlayer().hasCapability(Cosmetic_Armor_Capability, null) ) {
			IItemHandler oldInv = ev.getOriginal().getCapability(Cosmetic_Armor_Capability, null);
			IItemHandler newInv = ev.getEntityPlayer().getCapability(Cosmetic_Armor_Capability, null);
			
			for (int i = 0; i < oldInv.getSlots(); i++) {
				newInv.insertItem(i, oldInv.extractItem(i, 1, false), false);
			}
		
		}
		
	}
	
	public class Provider implements ICapabilityProvider, ICapabilitySerializable<NBTBase> {
		
		private ItemStackHandler inv = new ItemStackHandler (4);
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			if (capability == Cosmetic_Armor_Capability) {
				return true;
			}
			return false;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (capability == Cosmetic_Armor_Capability) {
				return (T) inv;
			}
			return null;
		}
		
		public ItemStackHandler getInv () {
			return inv;
		}
		
		public void setInv (ItemStackHandler inventory) {
			inv = inventory;
		}

		@Override
		public NBTBase serializeNBT() {
			return inv.serializeNBT();
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			inv.deserializeNBT((NBTTagCompound) nbt);
		}
		
	}
}
