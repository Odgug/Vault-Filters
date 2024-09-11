package net.joseph.vaultfilters;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ModPresenceMessage {
    protected final String version;

    public ModPresenceMessage(String version) {
        this.version = version;
    }

    public void encoder(FriendlyByteBuf buf) {
        buf.writeUtf(this.version);
    }

    public static ModPresenceMessage decoder(FriendlyByteBuf buf) {
        return new ModPresenceMessage(buf.readUtf());
    }

    public void consumer(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        LogicalSide side = context.getDirection().getReceptionSide();

        DistExecutor.unsafeRunForDist(() -> () -> {
            if (side.isClient()) {
                VaultFilters.serverHasVaultFilters = this.version.equals(VaultFilters.MOD_VERSION);
            }
            return null;
        }, () -> () -> {
            if (side.isServer()) {
                ServerPlayer player = context.getSender();
                if (player != null) {
                    if (this.version.equals(VaultFilters.MOD_VERSION)) {
                        VaultFilters.PLAYERS_WITH_VAULT_FILTERS.add(player.getUUID());
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
