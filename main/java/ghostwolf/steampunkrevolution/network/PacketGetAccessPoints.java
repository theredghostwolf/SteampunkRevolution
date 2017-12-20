package ghostwolf.steampunkrevolution.network;

import java.util.ArrayList;
import java.util.List;

import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.util.AccessPoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketGetAccessPoints implements IMessage {
		
		AccessPoint accesspoint = new AccessPoint();
	
		int size;
		int robotID;
		int mode;
		List<AccessPoint> points;
		
		@Override
		public void fromBytes(ByteBuf buf) {
			size = buf.readInt();
			robotID = buf.readInt();
			mode = buf.readInt();
			
			points = accesspoint.readAccessPointListFromBuf(buf, size);
			
			
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(this.size);
			buf.writeInt(this.robotID);
			buf.writeInt(this.mode);
			
			
			buf = accesspoint.writeAccessPointListToBuf(this.points, buf);
			
			
		}
		
		public PacketGetAccessPoints () {};
		
		public PacketGetAccessPoints (int amount, int robotID, int mode, List<AccessPoint> l) {
			this.size = amount;
			this.robotID = robotID;
			this.mode = mode;
			this.points = l;
	
		}
		
		 public static class Handler implements IMessageHandler<PacketGetAccessPoints, IMessage> {
		        @Override
		        public IMessage onMessage(PacketGetAccessPoints message, MessageContext ctx) {
		            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
		            return null;
		        }

		        @SideOnly(Side.CLIENT)
		        private void handle(PacketGetAccessPoints message, MessageContext ctx) {
		        	if (ctx.side == Side.CLIENT) {
		        		Minecraft mc = Minecraft.getMinecraft();
		        		Entity e = mc.world.getEntityByID(message.robotID);
		        		if (e != null && e instanceof EntityRobot) {
		        			EntityRobot bot = (EntityRobot) e;
		        			
		        			switch (message.mode) {
							case 0:
								bot.ExtractPoints = message.points;
								break;
							case 1:
								bot.InsertPoints = message.points;
								break;
							case 2:
								bot.refuelPoints = message.points;
								break;
							}
		        			
		        		}
		        	}
		           
		        	
		        }
		 }

}
