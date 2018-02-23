package ghostwolf.steampunkrevolution.event;

import ghostwolf.steampunkrevolution.capabilities.CapabilityCosmeticArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CosmeticArmorHandler {

		ItemStackHandler localInv = new ItemStackHandler (4);
		
		private void SwapInv (EntityPlayer p) {
			if (p.hasCapability(CapabilityCosmeticArmor.Cosmetic_Armor_Capability, null) && p.inventory.armorInventory.size() <= 4 ) {
		    	   IItemHandler cosInv = p.getCapability(CapabilityCosmeticArmor.Cosmetic_Armor_Capability, null);
		    	   for (int i = 0; i < cosInv.getSlots(); i++) {
		    		   localInv.insertItem(i, cosInv.extractItem(i, 1, false), false);
		    	   }
		    	   
		    	   NonNullList<ItemStack> armor =  p.inventory.armorInventory;
		    	   for (int i = 0; i < armor.size(); i++) {
		    		   cosInv.insertItem(cosInv.getSlots() - 1 - i, armor.get(i), false);
		    	   }
		    	   
		    	   for (int i = 0; i < localInv.getSlots(); i++) {
		    		   armor.set(localInv.getSlots() - i - 1, localInv.extractItem(i, 1, false));
		    	   }
		       }
		}
		
		@SubscribeEvent
	    public void handleCanceledEvent(RenderPlayerEvent.Pre event)
	    {
			
	    }

	    @SubscribeEvent
	    public void handleEvent(RenderPlayerEvent.Post event)
	    {
			SwapInv(event.getEntityPlayer());

	        
	    }

	    @SubscribeEvent
	    public void handleEvent(RenderPlayerEvent.Pre event)
	    {
			SwapInv(event.getEntityPlayer());


	    }
	    
	
	
}
