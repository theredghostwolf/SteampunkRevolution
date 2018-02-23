package ghostwolf.steampunkrevolution.network;

import ghostwolf.steampunkrevolution.capabilities.CapabilityCosmeticArmor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class PacketSyncCosmeticArmor implements IMessage {
	
	ItemStack armor;
	byte slot;
	int playerId;
	
	public PacketSyncCosmeticArmor() {}
	
	public PacketSyncCosmeticArmor (ItemStack stack, byte slot , int playerId ) {
		this.armor = stack;
		this.slot = slot;
		this.playerId = playerId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		playerId = buf.readInt();
		slot = buf.readByte();
		armor = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(playerId);
		buf.writeByte(slot);
		ByteBufUtils.writeItemStack(buf, armor);
	}
	
	  public static class Handler implements IMessageHandler<PacketSyncCosmeticArmor, IMessage> {
	        @Override
	        public IMessage onMessage(PacketSyncCosmeticArmor message, MessageContext ctx) {
	            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
	            return null;
	        }

	        @SideOnly(Side.CLIENT)
	        private void handle(PacketSyncCosmeticArmor message, MessageContext ctx) {
	        	Entity p = Minecraft.getMinecraft().world.getEntityByID(message.playerId);
	        	if (p instanceof EntityPlayer && p.hasCapability(CapabilityCosmeticArmor.Cosmetic_Armor_Capability, null)) {
	        		IItemHandler pInv = p.getCapability(CapabilityCosmeticArmor.Cosmetic_Armor_Capability, null);
	        		
	        		pInv.extractItem(message.slot, pInv.getStackInSlot(message.slot).getCount(), false);
	        		pInv.insertItem(message.slot, message.armor, false);
	        	}
	        }
	}

}
