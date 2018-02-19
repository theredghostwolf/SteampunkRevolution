package ghostwolf.steampunkrevolution.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	
    private static int packetId = 0;

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {
    }

    public static int nextID() {
        return packetId++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        // Register messages which are sent from the client to the server here:
        INSTANCE.registerMessage(PacketSpawnParticle.Handler.class, PacketSpawnParticle.class, nextID(), Side.CLIENT);
        
        INSTANCE.registerMessage(PacketSetAccessPoint.Handler.class, PacketSetAccessPoint.class, nextID(), Side.SERVER);
        
        INSTANCE.registerMessage(PacketGetAccessPoints.Handler.class, PacketGetAccessPoints.class, nextID(), Side.CLIENT);
        
        INSTANCE.registerMessage(PacketSetLoaderConfig.Handler.class, PacketSetLoaderConfig.class, nextID(), Side.SERVER);
    	}

}
