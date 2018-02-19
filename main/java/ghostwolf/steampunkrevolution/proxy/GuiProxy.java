package ghostwolf.steampunkrevolution.proxy;

import ghostwolf.steampunkrevolution.containers.ContainerLoader;
import ghostwolf.steampunkrevolution.containers.ContainerOven;
import ghostwolf.steampunkrevolution.gui.GuiLoader;
import ghostwolf.steampunkrevolution.gui.GuiSteamOven;
import ghostwolf.steampunkrevolution.tileentities.TileEntityLoader;
import ghostwolf.steampunkrevolution.tileentities.TileEntitySteamOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class GuiProxy implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntitySteamOven) {
            return new ContainerOven ((TileEntitySteamOven) te, player.inventory);
        } else if (te instanceof TileEntityLoader) {
        	return new ContainerLoader((TileEntityLoader) te, player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntitySteamOven) {
        	TileEntitySteamOven containerTileEntity = (TileEntitySteamOven) te;
            return new GuiSteamOven(containerTileEntity, new ContainerOven(containerTileEntity, player.inventory));
        } else if (te instanceof TileEntityLoader) {
        	TileEntityLoader containerTileEntity = (TileEntityLoader) te;
        	return new GuiLoader(containerTileEntity, new ContainerLoader(containerTileEntity, player.inventory));
        }
        return null;
    }
}
