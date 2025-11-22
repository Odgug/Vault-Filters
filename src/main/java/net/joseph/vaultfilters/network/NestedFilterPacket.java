package net.joseph.vaultfilters.network;

import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import net.joseph.vaultfilters.VFNestedFilters;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.joseph.vaultfilters.access.FilterMenuAdvancedAccessor;
import net.joseph.vaultfilters.mixin.compat.create.AbstractFilterMenuInvoker;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NestedFilterPacket {
    //message to open the previous menu
    private final boolean close;
    private final int slot;
    public NestedFilterPacket(boolean close, int slot) {
        this.close = close;
        this.slot = slot;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(close);
        buf.writeInt(slot);
    }

    public static NestedFilterPacket decode(FriendlyByteBuf buf) {
        return new NestedFilterPacket(buf.readBoolean(),buf.readInt());

    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (player.containerMenu instanceof AbstractFilterMenu ac) {
                    if (close) {
                        ((AbstractFilterMenuInvoker) ac).callSaveData(ac.contentHolder);
                        VFNestedFilters.openPreviousMenu(player,ac.contentHolder);
                    } else {
                        ItemStack nextItem = ac.ghostInventory.getStackInSlot(slot);
                        VFNestedFilters.openNextMenu(player,ac.contentHolder,nextItem,slot);
                    }

                }
            }
        });
        context.setPacketHandled(true);
    }
}
