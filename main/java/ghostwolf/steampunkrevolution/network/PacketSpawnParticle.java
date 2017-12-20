package ghostwolf.steampunkrevolution.network;

import java.util.Random;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSpawnParticle  implements IMessage {
	
	private double x;
	private double y;
	private double z;
	
	  private int id;
	  private int amount;
	  private double range;
	  
	  private double mx;
	  private double my;
	  private double mz;
	  
	    @Override
	    public void fromBytes(ByteBuf buf) {
	        x = buf.readDouble();
	        y = buf.readDouble();
	        z = buf.readDouble();
	        id = buf.readInt();
	        amount = buf.readInt();
	        range = buf.readDouble();
	        mx = buf.readDouble();
	        my = buf.readDouble();
	        mz = buf.readDouble();
	    }

	    @Override
	    public void toBytes(ByteBuf buf) {
	        buf.writeDouble(x);
	        buf.writeDouble(y);
	        buf.writeDouble(z);
	        buf.writeInt(id);
	        buf.writeInt(amount);
	        buf.writeDouble(range);
	        buf.writeDouble(mx);
	        buf.writeDouble(my);
	        buf.writeDouble(mz);
	    }
	    
	    public PacketSpawnParticle() {};

	    public PacketSpawnParticle(double x, double y, double z, int id, int amount, double range, double movementx, double movementy, double movementz) {
	    	this.x = x;
	    	this.y = y;
	    	this.z = z;
	    	this.id = id;
	    	this.amount = amount;
	    	this.range = range;
	    	this.mx = movementx;
	    	this.my = movementy;
	    	this.mz = movementz;
	    }

	    public static class Handler implements IMessageHandler<PacketSpawnParticle, IMessage> {
	        @Override
	        public IMessage onMessage(PacketSpawnParticle message, MessageContext ctx) {
	            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
	            return null;
	        }

	        @SideOnly(Side.CLIENT)
	        private void handle(PacketSpawnParticle message, MessageContext ctx) {
	        	
	        	if (ctx.side == Side.CLIENT) {
	        	double x = message.x;
	        	double y = message.y;
	        	double z = message.z;
	        	World world = Minecraft.getMinecraft().world;
	        	EnumParticleTypes particle = EnumParticleTypes.getParticleFromId(message.id);
	        	for (int i = 0; i < message.amount; i++) {
	        		//spawn coords calculation
	        		Random xr = new Random();
	        		Random zr = new Random();
	        		double randomValuex = 0 + (message.range) * xr.nextDouble();
	        		double randomValuez = 0 + (message.range) * zr.nextDouble();
	        		if (Math.random() > 0.5) {
	        			randomValuex *= -1;			
	        		}
	        		if (Math.random() > 0.5) {
	        			randomValuez *= -1;
	        		}
	        		
	        		//movement calculations
	        		Random mxr = new Random();
	        		Random mzr = new Random();
	        		double randomValuemx = 0 + (message.mx) * mxr.nextDouble();
	        		double randomValuemz = 0 + (message.mz) * mzr.nextDouble();
	        		if (Math.random() > 0.5) {
	        			randomValuemx *= -1;			
	        		}
	        		if (Math.random() > 0.5) {
	        			randomValuemz *= -1;
	        		}
	        		//checks if the distance is in range
	        		double distance = Math.sqrt((randomValuez * randomValuez) + (randomValuex * randomValuex));
	        		
	        		if (distance < message.range ) {
		        		world.spawnParticle(particle, x + randomValuex, y + (Math.random() / 2), z + randomValuez, randomValuemx, message.my, randomValuemz);    
		        		
	        		}
	        	}
	        }
	        }
	    }


}
