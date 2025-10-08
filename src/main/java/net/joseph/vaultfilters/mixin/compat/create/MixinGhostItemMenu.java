package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import com.simibubi.create.foundation.gui.menu.GhostItemMenu;
import net.joseph.vaultfilters.VaultFilters;
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
            shift = At.Shift.AFTER),cancellable = true)
    private void onClicked(int slotId, int dragType, ClickType clickTypeIn, Player player, CallbackInfo ci) {
        GhostItemMenu instance = (GhostItemMenu) (Object) this;
        if (instance instanceof FilterMenu filterMenu && clickTypeIn == ClickType.CLONE) {
            int slot = slotId - 36;
                ItemStack stackInSlot = ghostInventory.getStackInSlot(slot);
                if (stackInSlot.getItem() instanceof FilterItem filterItem) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ItemStack stack = stackInSlot.copy();

                        MenuProvider provider = new MenuProvider() {
                            @Override
                            public Component getDisplayName() {
                                return stack.getHoverName();
                            }

                            @Override
                            public AbstractContainerMenu createMenu(int id, Inventory inv, Player p) {
                                if (stack.getItem() instanceof FilterItem filterItem) {
                                    // Use the stack directly instead of player.getMainHandItem()
                                    //VaultFilters.LOGGER.info(stack.getDisplayName().getString());
                                    return stack.is(AllItems.FILTER.get())
                                            ? FilterMenu.create(id, inv, stack)
                                            : AttributeFilterMenu.create(id, inv, stack);
                                }
                                return null;
                            }

                        };
                        //VaultFilters.LOGGER.info(stack.getDisplayName().getString());
                        NetworkHooks.openGui(serverPlayer, provider, buf -> buf.writeItem(stack));
                    }
                }
                ci.cancel();
        }
    }
}
