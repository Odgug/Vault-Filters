package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import com.simibubi.create.foundation.gui.menu.GhostItemMenu;
import com.simibubi.create.foundation.gui.menu.MenuBase;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.access.GhostItemMenuAdvancedAccessor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GhostItemMenu.class,remap = false)
public abstract class MixinGhostItemMenu extends MenuBase implements GhostItemMenuAdvancedAccessor {
    @Shadow
    public ItemStackHandler ghostInventory;
    @Unique
    public GhostItemMenu<ItemStack> vaultfilters$previousMenu;


    //unused
    protected MixinGhostItemMenu(MenuType type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    @Unique
    public void vaultfilters$setPreviousMenu(GhostItemMenu<ItemStack> menu) {
        this.vaultfilters$previousMenu = menu;
    }

    @Unique
    public GhostItemMenu<ItemStack> vaultfilters$getPreviousMenu() {
        return this.vaultfilters$previousMenu;
    }


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
                        serverPlayer.server.execute(() -> {
                        GhostItemMenu<ItemStack> previousMenu = (GhostItemMenu<ItemStack>) (Object) this;
                        MenuProvider provider = new MenuProvider() {
                            @Override
                            public Component getDisplayName() {
                                return stackInSlot.getHoverName();
                            }

                            @Override
                            public GhostItemMenu<ItemStack> createMenu(int id, Inventory inv, Player p) {
                                if (stackInSlot.getItem() instanceof FilterItem filterItem) {
                                    // Use the stack directly instead of player.getMainHandItem()
                                    //VaultFilters.LOGGER.info(stack.getDisplayName().getString());
                                    GhostItemMenu<ItemStack> menu = stackInSlot.is(AllItems.FILTER.get())
                                            ? FilterMenu.create(id, inv, stackInSlot)
                                            : AttributeFilterMenu.create(id, inv, stackInSlot);
                                    ((GhostItemMenuAdvancedAccessor) menu).vaultfilters$setPreviousMenu((previousMenu));
                                    return menu;
                                }
                                return null;
                            }

                        };
                        //VaultFilters.LOGGER.info(stack.getDisplayName().getString());
                        NetworkHooks.openGui(serverPlayer, provider, buf -> buf.writeItem(stackInSlot));
                        });
                    }
                }
                ci.cancel();
        }
    }
    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        GhostItemMenu instance = (GhostItemMenu) (Object) this;
        if (instance instanceof AbstractFilterMenu filterMenu) {
            GhostItemMenu<ItemStack> previousMenu = ((GhostItemMenuAdvancedAccessor) instance).vaultfilters$getPreviousMenu();
            if (previousMenu != null && playerIn instanceof ServerPlayer serverPlayer) {
                serverPlayer.server.execute(() -> {
                    ((MenuBaseAccessor)previousMenu ).vaultfilters$saveData(previousMenu.contentHolder);
                    MenuProvider provider = new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return previousMenu.contentHolder.getDisplayName();
                        }

                        @Override
                        public GhostItemMenu<ItemStack> createMenu(int id, Inventory inv, Player p) {
                            GhostItemMenu<ItemStack> menu = previousMenu.contentHolder.is(AllItems.FILTER.get())
                                    ? FilterMenu.create(id, inv, previousMenu.contentHolder)
                                    : AttributeFilterMenu.create(id, inv, previousMenu.contentHolder);
                            ((GhostItemMenuAdvancedAccessor) menu).vaultfilters$setPreviousMenu(((GhostItemMenuAdvancedAccessor)previousMenu).vaultfilters$getPreviousMenu());
                            return menu;
                        }

                    };
                    NetworkHooks.openGui(serverPlayer, provider, buf -> buf.writeItem(previousMenu.contentHolder));
                });

            }
        }
    }
}
