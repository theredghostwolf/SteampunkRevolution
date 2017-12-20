package ghostwolf.steampunkrevolution.util;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class AccessPoint {
	
	public BlockPos pos;
	public EnumFacing facing;
	
	public AccessPoint() {}
	
	public AccessPoint (BlockPos pos, EnumFacing facing) {
		this.pos = pos;
		this.facing = facing;
	}
	
	public ByteBuf writeAccessPointListToBuf (List<AccessPoint> l, ByteBuf buf ) {
		if (l != null) {
			for (int i = 0; i < l.size(); i++) {
				AccessPoint p = l.get(i);
				buf.writeInt(p.pos.getX());
				buf.writeInt(p.pos.getY());
				buf.writeInt(p.pos.getZ());
				buf.writeInt(p.facing.ordinal());
			}
		}
		return buf;
		
	}
	
	public List<AccessPoint> readAccessPointListFromBuf (ByteBuf buf, int size) {
		List<AccessPoint> l = new ArrayList<AccessPoint>();
		for (int i = 0; i < size; i++) {
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			int facing = buf.readInt();
			
			l.add(new AccessPoint(new BlockPos(x,y,z), EnumFacing.VALUES[facing] ));
		}
		
		return l;
	}
	
}