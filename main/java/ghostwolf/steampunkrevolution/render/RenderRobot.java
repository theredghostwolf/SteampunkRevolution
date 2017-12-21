package ghostwolf.steampunkrevolution.render;

import ghostwolf.steampunkrevolution.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderRobot extends RenderBiped<EntityLiving> {

	

	public RenderRobot(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
		super(renderManagerIn, modelBipedIn, shadowSize);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		
		return new ResourceLocation(Reference.MOD_ID + ":textures/blocks/parts/copper.png");
	}
	




}
