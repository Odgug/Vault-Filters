package net.joseph.vaultfilters;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public class ModPresence {
    public static final Set<UUID> PLAYERS_WITH_VAULT_FILTERS = new HashSet<>();
    public static boolean serverHasVaultFilters = false;
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(VaultFilters.MOD_ID, "main"),
            () -> "1",
            o -> true,
            o -> true
    );

    public static void init() {
        CHANNEL.messageBuilder(ModPresence.Message.class, 0)
                .encoder(ModPresence.Message::encoder)
                .decoder(ModPresence.Message::decoder)
                .consumer(ModPresence.Message::consumer)
                .add();
    }

    @OnlyIn(Dist.CLIENT) @SubscribeEvent
    public static void onClientConnect(ClientPlayerNetworkEvent.LoggedInEvent event) {
        CHANNEL.sendToServer(new ModPresence.Message(VaultFilters.MOD_VERSION));
    }

    @OnlyIn(Dist.CLIENT) @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        serverHasVaultFilters = false;
    }

    @OnlyIn(Dist.DEDICATED_SERVER) @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ModPresence.Message(VaultFilters.MOD_VERSION));
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER) @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            PLAYERS_WITH_VAULT_FILTERS.remove(player.getUUID());
        }
    }

    public static class Message {
        protected final String version;

        public Message(String version) {
            this.version = version;
        }

        public void encoder(FriendlyByteBuf buf) {
            buf.writeUtf(this.version);
        }

        public static Message decoder(FriendlyByteBuf buf) {
            return new Message(buf.readUtf());
        }

        public void consumer(Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            LogicalSide side = context.getDirection().getReceptionSide();

            DistExecutor.unsafeRunForDist(() -> () -> {
                if (side.isClient()) {
                    serverHasVaultFilters = this.version.equals(VaultFilters.MOD_VERSION);
                }
                return null;
            }, () -> () -> {
                if (side.isServer()) {
                    ServerPlayer player = context.getSender();
                    if (player != null) {
                        if (this.version.equals(VaultFilters.MOD_VERSION)) {
                            PLAYERS_WITH_VAULT_FILTERS.add(player.getUUID());
                        } else {
                            // TODO: send message to player
                        }
                    }
                }
                return null;
            });

            context.setPacketHandled(true);
        }
    }
}
