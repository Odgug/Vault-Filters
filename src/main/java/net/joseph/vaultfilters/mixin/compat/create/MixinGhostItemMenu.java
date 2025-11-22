package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import com.simibubi.create.foundation.gui.menu.GhostItemMenu;
import net.joseph.vaultfilters.VFNestedFilters;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.network.NestedFilterPacket;
import net.joseph.vaultfilters.network.VFMessages;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GhostItemMenu.class,remap = false)
public class MixinGhostItemMenu {
    @Shadow
    public ItemStackHandler ghostInventory;

    @Inject(method = "clicked",at= @At(value = "INVOKE_ASSIGN",
            target = "Lcom/simibubi/create/foundation/gui/menu/GhostItemMenu;getCarried()Lnet/minecraft/world/item/ItemStack;",
            shift = At.Shift.AFTER),cancellable = true,remap = true)
    private void onClicked(int slotId, int dragType, ClickType clickTypeIn, Player player, CallbackInfo ci) {
        GhostItemMenu instance = (GhostItemMenu) (Object) this;
        if (instance instanceof FilterMenu filterMenu && clickTypeIn == ClickType.CLONE) {
            int slot = slotId - 36;
                ItemStack stackInSlot = ghostInventory.getStackInSlot(slot);
                if (stackInSlot.getItem() instanceof FilterItem filterItem) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (instance.contentHolder instanceof ItemStack currentItem)
                            if (instance instanceof AbstractFilterMenu abm) {
                                ((AbstractFilterMenuInvoker) abm).callSaveData(currentItem);
                                VFNestedFilters.openNextMenu(serverPlayer,currentItem,stackInSlot,slot);
                            }

                        }
//                    if (player instanceof LocalPlayer) {
//                            VFMessages.VFCHANNEL.sendToServer(new NestedFilterPacket(false,slot));
//                    }
                }
                ci.cancel();
        }
    }

}
