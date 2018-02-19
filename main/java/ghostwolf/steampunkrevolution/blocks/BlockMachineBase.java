package ghostwolf.steampunkrevolution.blocks;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.SteampunkRevolutionMod;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.tileentities.TileEntityBoiler;
import ghostwolf.steampunkrevolution.tileentities.TileEntityLoader;
import ghostwolf.steampunkrevolution.tileentities.TileEntityMachineBase;
import ghostwolf.steampunkrevolution.tileentities.TileEntityResinExtractor;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamOven;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachineBase extends Block implements ITileEntityProvider {
	
	public static final PropertyDirection facing = PropertyDirection.create("facing");
	public static final PropertyBool enabled = PropertyBool.create("enabled");
	
	public int light;
	
	public BlockMachineBase(String name, int l) {
		super(Material.IRON);
		setUnlocalizedName(Reference.MOD_ID + ":" + name);
		setRegistryName(name);
		setCreativeTab(ModItems.SteampunkItemsTab);
		this.light = l;
		setHardness(1.5F);
		setResistance(5F);
		setHarvestLevel("pickaxe", 1);
		
        setDefaultState(blockState.getBaseState().withProperty(facing, EnumFacing.NORTH));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(facing, getFacingFromEntity(pos, placer)), 2);
   }
  
	public EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
     
		return EnumFacing.getFacingFromVector(
            (float) (entity.posX - clickedBlock.getX()),
            (float) (0),
            (float) (entity.posZ - clickedBlock.getZ()));
    }
	
	@SideOnly(Side.CLIENT)
	   public void initModel() {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	 }
	
	
	   @Override
	    public IBlockState getStateFromMeta(int meta) {
	        return getDefaultState()
	                .withProperty(facing, EnumFacing.getFront(meta & 7))
	                .withProperty(enabled, (meta & 8) != 0);
	    }
	
	   @Override
	    public int getMetaFromState(IBlockState state) {
	        return state.getValue(facing).getIndex() + (state.getValue(enabled) ? 8 : 0);
	    }

	    @Override
	    protected BlockStateContainer createBlockState() {
	        return new BlockStateContainer(this, facing, enabled);
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
			public int getLightValue(IBlockState state) {
				if (state.getValue(this.enabled)) {
					return this.light;
				} else {
					return 0;
				}
			}
			
			@Override
			public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
				if (state.getValue(this.enabled)) {
					return this.light;
				} else {
					return 0;
				}
			}
			
			@Override
			public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
				TileEntity te = worldIn.getTileEntity(pos);
				if (te instanceof TileEntityMachineBase) {
					((TileEntityMachineBase) te).dropItems();
				}
				super.breakBlock(worldIn, pos, state);
			}
			
			@Override
			public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
					EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			      if (worldIn.isRemote) {
			            return true;
			        }
			        TileEntity te = worldIn.getTileEntity(pos);
			        if (te instanceof TileEntityMachineBase) {
			        	openUI(playerIn, worldIn, pos, te);
			            return true;
			        }
			        return false;
			}
			
		
		
			 public void openUI (EntityPlayer player, World world, BlockPos pos, TileEntity te) {
				 if (te instanceof TileEntitySteamOven) {
					 player.openGui(SteampunkRevolutionMod.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
				 }
			       
				 if (te instanceof TileEntityLoader) {
					 player.openGui(SteampunkRevolutionMod.instance, 2, world, pos.getX(), pos.getY(), pos.getZ());
				 }

			 }
			

}
