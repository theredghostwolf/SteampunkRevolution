package ghostwolf.steampunkrevolution.blocks;

import java.util.List;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.tesr.DisplayTesr;
import ghostwolf.steampunkrevolution.tileentities.TileEntityDisplay;
import ghostwolf.steampunkrevolution.tileentities.TileEntityDisplay;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockDisplay extends Block implements ITileEntityProvider {
	
	public BlockDisplay(String name) {
		super(Material.IRON);
		setUnlocalizedName(Reference.MOD_ID + ":" + name);
		setRegistryName(name);
		setCreativeTab(ModItems.SteampunkItemsTab);
		setHardness(1.5F);
		setResistance(5F);
		setHarvestLevel("pickaxe", 1);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override
	public boolean hasTileEntity() {
		return false;
	}
	
	 @SideOnly(Side.CLIENT)
	    public void initModel() {
		 ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplay.class, new DisplayTesr());
	 }
	 
	 
	 @Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	 
	 @Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		 return false;
	}
	 
	 @Override
		public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
		List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.2,0,0.2,0.8,0.8,0.8));	
		}
	 
	
	 
	 @Override
	public int getLightValue(IBlockState state) {
		return 10;
	 }
	 
	 @Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 10;
	}
	 
		@Override
		public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityDisplay) {
				((TileEntityDisplay) te).dropItems();
			}
			super.breakBlock(worldIn, pos, state);
		}
		
		 private TileEntityDisplay getTE(World world, BlockPos pos) {
		        return (TileEntityDisplay) world.getTileEntity(pos);
		    }

		    @Override
		    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
		            EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		        if (!world.isRemote) {
		            TileEntityDisplay te = getTE(world, pos);
		            ItemStack heldStack = player.getHeldItemMainhand(); 
		            
		            te.getStack();
		           
		           IItemHandler inv = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
		           
		           ItemStack pedestalStack =  inv.getStackInSlot(0);
		           
		           if ( pedestalStack.isEmpty()) {
		        	  ItemStack remainder =  inv.insertItem(0, heldStack.copy(), false);
		        	  if (remainder.isEmpty()) { 
		        		  heldStack.setCount(0);
		        	  } else {
		        		  heldStack.shrink(heldStack.getCount() - remainder.getCount());
		        	  }
		           } else {
		        	   te.dropItems();
		        	   inv.extractItem(0, inv.getStackInSlot(0).getCount(), false);
		           }
		           
		        }

		  
		        return true;
		    }
	 

}
