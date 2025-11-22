package net.joseph.vaultfilters.network;

import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class VFMessages {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel VFCHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(VaultFilters.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int packetId = 0;

    public static void register() {
        VFCHANNEL.registerMessage(packetId++, MenuFeaturesPacket.class,
                MenuFeaturesPacket::encode, MenuFeaturesPacket::decode, MenuFeaturesPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        VFCHANNEL.registerMessage(packetId++,NestedFilterPacket.class,
                NestedFilterPacket::encode,NestedFilterPacket::decode,NestedFilterPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

}
