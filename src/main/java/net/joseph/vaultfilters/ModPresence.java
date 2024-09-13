package net.joseph.vaultfilters;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModPresence {
    private static final int RECEIVE_MESSAGE_TIMEOUT = 20 * 30; // 20 ticks per second, 30 seconds
    public static final Map<UUID, Integer> SERVER_LOGIN_TICKS = new HashMap<>();
    public static final Set<UUID> PLAYERS_WITH_VAULT_FILTERS = new HashSet<>();
    public static boolean serverHasVaultFilters = false;
    public static int clientLoginTicks = 0;
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
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getCurrentServer() == null || minecraft.player == null || serverHasVaultFilters || event.phase != TickEvent.Phase.END || clientLoginTicks == RECEIVE_MESSAGE_TIMEOUT) {
            return;
        }

        clientLoginTicks++;
        if (clientLoginTicks == RECEIVE_MESSAGE_TIMEOUT) {
            Component cNoVFOnServer = new TextComponent("Valid Vault Filters version not detected on server, " +
                    "Vault Filters features are disabled.").withStyle(ChatFormatting.RED);
            minecraft.player.displayClientMessage(cNoVFOnServer,false);
        }
    }

    @OnlyIn(Dist.CLIENT) @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        serverHasVaultFilters = false;
        clientLoginTicks = 0;
    }

    @OnlyIn(Dist.DEDICATED_SERVER) @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ModPresence.Message(VaultFilters.MOD_VERSION));
            SERVER_LOGIN_TICKS.put(player.getUUID(), 0);
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER) @SubscribeEvent
    public static void onServerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }

        UUID uuid = player.getUUID();
        Integer ticks = SERVER_LOGIN_TICKS.get(uuid);
        if (ticks == null) {
            return;
        }

        if (ticks < RECEIVE_MESSAGE_TIMEOUT) {
            SERVER_LOGIN_TICKS.put(uuid, ticks + 1);
        } else {
            Component s2cNoVaultFilters = new TextComponent("This server has Vault Filters installed," +
                    "please install Vault Filters version " + VaultFilters.MOD_VERSION + " to use its features").withStyle(ChatFormatting.RED);
            player.displayClientMessage(s2cNoVaultFilters,false);
            SERVER_LOGIN_TICKS.remove(uuid);
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
                        UUID uuid = player.getUUID();
                        if (this.version.equals(VaultFilters.MOD_VERSION)) {
                            PLAYERS_WITH_VAULT_FILTERS.add(uuid);
                        } else {

                            Component s2cVersionMismatch = new TextComponent("Vault Filters version mismatch with the server, " +
                                    "please install version " + VaultFilters.MOD_VERSION +
                                    "to use Vault Filter's features").withStyle(ChatFormatting.RED);
                            player.displayClientMessage(s2cVersionMismatch,false);
                        }
                        SERVER_LOGIN_TICKS.remove(uuid);
                    }
                }
                return null;
            });

            context.setPacketHandled(true);
        }
    }
}
