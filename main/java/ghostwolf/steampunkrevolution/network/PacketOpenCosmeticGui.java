package ghostwolf.steampunkrevolution.network;

import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenCosmeticGui implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
		
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		
	}
	
	 public static class Handler implements IMessageHandler<PacketOpenCosmeticGui, IMessage> {
	        @Override
	        public IMessage onMessage(PacketOpenCosmeticGui message, MessageContext ctx) {
	            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
	            return null;
	        }

	       
	        private void handle(PacketOpenCosmeticGui message, MessageContext ctx) {
	        	 EntityPlayerMP p = ctx.getServerHandler().player;
	             World w = p.getEntityWorld();
	             BlockPos pPos = p.getPosition();  
	             p.openGui(SteampunkRevolutionMod.instance, 3, w, pPos.getX(), pPos.getY(), pPos.getZ());
	        }
	 }

}
