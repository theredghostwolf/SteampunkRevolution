package ghostwolf.steampunkrevolution.enums;

import java.util.Locale;

import ghostwolf.steampunkrevolution.entities.EntityMinecartTank;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecart.Type;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public enum EnumCarts implements IStringSerializable{
	tankCart;
	
	@Override
	public String getName() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
	
	public int getMeta () {
		return this.ordinal();
	}
	
	public EntityMinecart getCart(World w, float x, float y, float z) {
		return new EntityMinecartTank(w); 
	}
	
	

}
