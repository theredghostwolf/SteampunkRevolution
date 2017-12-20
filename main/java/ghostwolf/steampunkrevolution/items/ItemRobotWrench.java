package ghostwolf.steampunkrevolution.items;

import org.lwjgl.opengl.GL11;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.entities.EntityRobot;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.network.PacketHandler;
import ghostwolf.steampunkrevolution.network.PacketSetAccessPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRobotWrench extends Item {
	
	private int mode = 0; 
	private static final int maxMode = 3;
	
    public ItemRobotWrench() {
        setRegistryName("robotwrench");        
        setUnlocalizedName(Reference.MOD_ID + ":robotwrench");   
    	setCreativeTab(ModItems.SteampunkItemsTab);
    	this.maxStackSize = 1;
    }
    
    @SideOnly(Side.CLIENT)
    public void initModel () {
    	 ModelResourceLocation insertModel = new ModelResourceLocation(getRegistryName(), "type=insert");
         ModelResourceLocation extractModel = new ModelResourceLocation(getRegistryName(), "type=extract");
         ModelResourceLocation fuelModel = new ModelResourceLocation(getRegistryName(), "type=fuel");
         ModelResourceLocation homeModel = new ModelResourceLocation(getRegistryName(), "type=home");
         ModelResourceLocation orangeModel = new ModelResourceLocation(getRegistryName(), "type=orange");
         ModelResourceLocation purpleModel = new ModelResourceLocation(getRegistryName(), "type=purple");
         
         ModelBakery.registerItemVariants(this, insertModel, extractModel, fuelModel, homeModel, orangeModel, purpleModel);

         ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
             @Override
             public ModelResourceLocation getModelLocation(ItemStack stack) {
            	 switch (getMode(stack)) {
				case 1:
					return insertModel;
				case 2:
					return fuelModel;
				case 3:
					return homeModel;
				case 4:
					return purpleModel;
				default:
					return extractModel;
				}
             }
         });
    }
    
    public int getMode(ItemStack stack) {
    	if (getTagCompoundSafe(stack).hasKey("mode")) {
    		return getTagCompoundSafe(stack).getInteger("mode");
    	}
    	return mode;
    }
    
    public int getRobot (ItemStack stack) {
    	if (getTagCompoundSafe(stack).hasKey("robotID")) {
    		return getTagCompoundSafe(stack).getInteger("robotID");
    	}
    	return 0;
    }
    
    public void setRobot (ItemStack stack, int robotID) {
    	getTagCompoundSafe(stack).setInteger("robotID", robotID);
    }
    
    public void setMode (ItemStack stack, int mode) {
    	getTagCompoundSafe(stack).setInteger("mode", mode);
    }
    
    private NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    	
    	Entity target= Minecraft.getMinecraft().objectMouseOver.entityHit;
    	if (target != null && target instanceof EntityRobot) {
    		if (getTagCompoundSafe(playerIn.getHeldItem(handIn)).hasKey("robotID")) {
    			Entity e = worldIn.getEntityByID(getRobot(playerIn.getHeldItem(handIn)));
    			if (e != null) {
    				e.setGlowing(false);
    			}
    		}
    		
    		setRobot(playerIn.getHeldItem(handIn), target.getEntityId());
    	}  else {
    		if (! playerIn.isSneaking()) {
    			nextMode(playerIn.getHeldItem(handIn));
    		}
    	}
    	 
    	return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
    		EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if (worldIn.isRemote) {
    	if (! worldIn.isAirBlock(pos)) {
    		if (player.isSneaking()) {
    				ItemStack stack = player.getHeldItem(hand);
    			 if	(getTagCompoundSafe(stack).hasKey("robotID")) {
    				 PacketHandler.INSTANCE.sendToServer(new PacketSetAccessPoint(pos, facing.ordinal(), getRobot(stack), getMode(stack)));
    				 if (getMode(stack) == 3) {
    					 Entity e = worldIn.getEntityByID(getRobot(stack));
    					 if (e != null && e instanceof EntityRobot) {
    						 EntityRobot bot = (EntityRobot) e;
    						 bot.setHomePosAndDistance(pos, bot.getRange());
    					 }
    				 }
    			 }
    			
    			}
    		}
    	}
    	return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    
    }
    
    private void nextMode (ItemStack stack) {
    	int mode = getMode(stack);
		mode++;
		if (mode > maxMode) {
			mode = 0;
		}
		setMode(stack, mode);
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
    	switch (getMode(stack)) {
		case 0:
			return super.getItemStackDisplayName(stack) + " - Extract";
		case 1:
			return super.getItemStackDisplayName(stack) + " - Insert";
		case 2:
			return super.getItemStackDisplayName(stack) + " - Fuel";
		case 3:
			return super.getItemStackDisplayName(stack) + " - Home";
		default:
			return super.getItemStackDisplayName(stack);
		}
    	
    	
    }
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	if (worldIn.isRemote) {
    	if (getTagCompoundSafe(stack).hasKey("robotID")) {
    		Entity e = worldIn.getEntityByID(getRobot(stack));
    		if (e != null && e instanceof EntityRobot) {
    			if (isSelected) {
    				e.setGlowing(true);
    			} else {
    				e.setGlowing(false);
    			}
    		}
    	}
    	
    	} 
    	super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
  
    

}
