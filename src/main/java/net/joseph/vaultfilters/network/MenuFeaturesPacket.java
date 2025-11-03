package net.joseph.vaultfilters.network;

import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import com.simibubi.create.content.logistics.filter.FilterScreenPacket;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.joseph.vaultfilters.access.FilterMenuAdvancedAccessor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MenuFeaturesPacket {
    public enum MenuAction {
        CHANGE_NAME,
        MATCH_ALL,
        MATCH_ANY
    }
    private final MenuAction type;
    private final String potentialName;

    public MenuFeaturesPacket(MenuAction type, String potentialName) {
        if (potentialName == null) {
            this.potentialName = "";
        } else {
            this.potentialName = potentialName;
        }
        this.type = type;
    }
    public MenuFeaturesPacket(MenuAction type) {
        potentialName = "";
        this.type = type;
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeUtf(potentialName);
    }

    public static MenuFeaturesPacket decode(FriendlyByteBuf buf) {
        MenuAction type = buf.readEnum(MenuAction.class);
        String potentialName = buf.readUtf(50);
        return new MenuFeaturesPacket(type,potentialName);

    }
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (player.containerMenu instanceof FilterMenu fc) {
                    if (this.type == MenuAction.MATCH_ALL) {
                        ((FilterMenuAdvancedAccessor) fc).vault_filters$setMatchAll(true);
                    } else if (this.type == MenuAction.MATCH_ANY) {
                        ((FilterMenuAdvancedAccessor) fc).vault_filters$setMatchAll(false);
                    }
                }
                if (player.containerMenu instanceof AbstractFilterMenu ac) {
                    if (this.type == MenuAction.CHANGE_NAME) {
                        ((AbstractFilterMenuAdvancedAccessor)ac).vault_filters$setName(potentialName);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
