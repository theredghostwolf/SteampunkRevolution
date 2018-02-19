package ghostwolf.steampunkrevolution.network;

import ghostwolf.steampunkrevolution.tileentities.TileEntityLoader;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSetLoaderConfig implements IMessage {
	
	private int x;
	private int y;
	private int z;
	
	private boolean emitwhenempty;
	private boolean emitwhencartfull;
	private boolean emitwhencartnotmatch;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		
		emitwhenempty = buf.readBoolean();
		emitwhencartfull = buf.readBoolean();
		emitwhencartnotmatch = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		
		buf.writeBoolean(emitwhenempty);
		buf.writeBoolean(emitwhencartfull);
		buf.writeBoolean(emitwhencartnotmatch);
		
	}
	
	public PacketSetLoaderConfig () {}
	
	public PacketSetLoaderConfig (int x, int y, int z, boolean empty, boolean cart, boolean wrongcart) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.emitwhencartfull = cart;
		this.emitwhenempty = empty;
		this.emitwhencartnotmatch = wrongcart;
	}
	
	 public static class Handler implements IMessageHandler<PacketSetLoaderConfig, IMessage> {
	        @Override
	        public IMessage onMessage(PacketSetLoaderConfig message, MessageContext ctx) {
	            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
	            return null;
	        }

	       
	        private void handle(PacketSetLoaderConfig message, MessageContext ctx) {
	        	 EntityPlayerMP playerEntity = ctx.getServerHandler().player;
	             World world = playerEntity.getEntityWorld();
	             BlockPos p = new BlockPos(message.x, message.y, message.z);
	             TileEntity te = world.getTileEntity(p);
	             if (te instanceof TileEntityLoader) {
	            	 TileEntityLoader loader = (TileEntityLoader) te;
	            	 loader.emitWhenCartFull = message.emitwhencartfull;
	            	 loader.emitWhenLoaderEmpty = message.emitwhenempty;
	            	 loader.markDirty();
	             }
	        }
	 	}

}
