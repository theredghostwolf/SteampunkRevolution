package ghostwolf.steampunkrevolution.render;

import ghostwolf.steampunkrevolution.Reference;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.util.ResourceLocation;

public class RenderKabaneri extends RenderZombie {

	public RenderKabaneri(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	public void bindTexture(ResourceLocation location) {
		super.bindTexture(new ResourceLocation(Reference.MOD_ID + ":textures/entities/kabaneri.png"));
	}
	

}
