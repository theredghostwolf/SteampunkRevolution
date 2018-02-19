package ghostwolf.steampunkrevolution.tileentities;

import ghostwolf.steampunkrevolution.Config;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSpawnParticle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityPedestal extends TileEntityDisplay { 
	
	public void ConsumeItem () {
		IItemHandler inv = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		inv.extractItem(0, 1, false);
		
		PacketHandler.INSTANCE.sendToAllAround(new PacketSpawnParticle(this.pos.getX() + 0.5, this.pos.getY() + 0.8, this.pos.getZ() + 0.5,EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), 10, 0.5F,0F,0F,0F) ,  new TargetPoint(this.world.provider.getDimension() ,this.pos.getX(), this.getPos().getY(), this.pos.getZ(), Config.smokeRenderRange));
	}

}
