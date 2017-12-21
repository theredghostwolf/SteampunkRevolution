package ghostwolf.steampunkrevolution.models;

import org.lwjgl.opengl.GL11;

import ghostwolf.steampunkrevolution.entities.EntityRobot;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelRobot extends ModelBiped {
	
	public ModelRenderer engine;
	public ModelRenderer engineExaust1;
	public ModelRenderer engineExaust2;
	
	
	public ModelRobot(float scale) {
		super(scale);
		
		this.engine = new ModelRenderer(this,16,16);
		this.engine.addBox(-3, 0, 2, 6, 8, 6,scale);
		this.engine.setRotationPoint(0, 0, 0);
		this.bipedBody.addChild(this.engine);
		
		this.engineExaust1 = new ModelRenderer(this,16,16);
		this.engineExaust1.addBox(-3F, -4,4F, 2, 5, 3, scale);
		this.engineExaust1.setRotationPoint(0, 0, 0);
		this.engine.addChild(engineExaust1);
		
		this.engineExaust2 = new ModelRenderer(this,16,16);
		this.engineExaust2.addBox(1F, -4, 4F, 2, 5, 3, scale);
		this.engineExaust2.setRotationPoint(0, 0, 0);
		 
		
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale) {
		
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		 GlStateManager.pushMatrix();
		 this.engine.render(scale);
		 this.engineExaust1.render(scale);
		 this.engineExaust2.render(scale);
		 GlStateManager.popMatrix();
	}

	
}

	

