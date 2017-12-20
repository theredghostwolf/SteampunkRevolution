package ghostwolf.steampunkrevolution.blocks;

import ghostwolf.steampunkrevolution.Reference;
import ghostwolf.steampunkrevolution.init.ModItems;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamCrusher;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSteamCrusher extends Block implements ITileEntityProvider {

	public BlockSteamCrusher() {
		super(Material.IRON);
		
		setUnlocalizedName(Reference.MOD_ID + ":crusher");
		setRegistryName("crusher");
		setCreativeTab(ModItems.SteampunkItemsTab);
		
		setHardness(1.5F);
		setResistance(5F);
		setHarvestLevel("pickaxe", 1);
		
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
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySteamCrusher();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	 @SideOnly(Side.CLIENT)
	   public void initModel() {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	 }
	

}
