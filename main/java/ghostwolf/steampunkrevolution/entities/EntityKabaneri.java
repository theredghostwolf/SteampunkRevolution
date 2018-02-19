package ghostwolf.steampunkrevolution.entities;

import ghostwolf.steampunkrevolution.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityKabaneri extends EntityZombie {

	public EntityKabaneri(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		
		super.onDeath(cause);
		if (! getEntityWorld().isRemote) {
			getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), new ItemStack(ModItems.mechanoidpart,1,3)));
		}
	}
}
