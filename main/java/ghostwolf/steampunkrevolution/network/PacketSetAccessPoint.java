package ghostwolf.steampunkrevolution.network;

import java.util.Random;

import ghostwolf.steampunkrevolution.entities.EntityRobot;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSetAccessPoint implements IMessage {
	
	//target block
	private int x;
	private int y;
	private int z;
	//facing ordinal
	private int facing;
	//id of target entity
	private int robotID;
	//type of point to set
	private int type; // 0 extract, 1 insert, 2 fuel
	
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		facing = buf.readInt();
		robotID = buf.readInt();
		type = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.facing);
		buf.writeInt(this.robotID);
		buf.writeInt(this.type);
		
	}
	
	public PacketSetAccessPoint () {};
	
	public PacketSetAccessPoint (BlockPos pos, int facingID, int robotID, int type) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		
		this.facing = facingID;
		
		this.robotID = robotID;
		
		this.type = type;
	}
	
	 public static class Handler implements IMessageHandler<PacketSetAccessPoint, IMessage> {
	        @Override
	        public IMessage onMessage(PacketSetAccessPoint message, MessageContext ctx) {
	            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
	            return null;
	        }

	       
	        private void handle(PacketSetAccessPoint message, MessageContext ctx) {
	        	 EntityPlayerMP playerEntity = ctx.getServerHandler().player;
	             World world = playerEntity.getEntityWorld();
	             BlockPos p = new BlockPos(message.x, message.y, message.z);
	             EnumFacing f = EnumFacing.VALUES[message.facing];
	             if (world.isBlockLoaded(p)) {
	            	 Entity bot = world.getEntityByID(message.robotID);
	            	 if (bot instanceof EntityRobot) {
	            		 EntityRobot robot = (EntityRobot) bot;
	            		 switch (message.type) {
						case 1:
							robot.addInsertPoint(p,f);
							PacketHandler.INSTANCE.sendTo(new PacketGetAccessPoints(robot.InsertPoints.size(), message.robotID, message.type, robot.InsertPoints) , playerEntity);
							
							break;
						case 0:
							robot.addExtractPoint(p,f);
							PacketHandler.INSTANCE.sendTo(new PacketGetAccessPoints(robot.ExtractPoints.size(), message.robotID, message.type, robot.ExtractPoints) , playerEntity);

							break;
						case 2:
							robot.addFuelPoint(p,f);
							PacketHandler.INSTANCE.sendTo(new PacketGetAccessPoints(robot.refuelPoints.size(), message.robotID, message.type, robot.refuelPoints) , playerEntity);
							break;
						case 3:
							robot.setHomePosAndDistance(p, robot.getRange());
							robot.setPosition(p.getX() + 0.5, p.getY() + 1.8, p.getZ() + 0.5);
							
						
							break;
						}
	            	 }
	             }
	        	
	        }
	 }


}
