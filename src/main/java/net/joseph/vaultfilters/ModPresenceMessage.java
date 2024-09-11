package net.joseph.vaultfilters;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ModPresenceMessage {
    public void encoder(FriendlyByteBuf ignored) {}

    public static ModPresenceMessage decoder(FriendlyByteBuf ignored) {
        return new ModPresenceMessage();
    }

    public void consumer(Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunForDist(() -> () -> {
            VaultFilters.serverHasVaultFilters = true;
            return null;
        }, () -> () -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                VaultFilters.PLAYERS_WITH_VAULT_FILTERS.add(player.getUUID());
            }
            return null;
        });
        ctx.get().setPacketHandled(true);
    }
}
