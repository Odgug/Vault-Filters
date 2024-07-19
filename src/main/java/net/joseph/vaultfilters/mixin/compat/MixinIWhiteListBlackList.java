package net.joseph.vaultfilters.mixin.compat;

import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.blockentity.config.IWhitelistBlacklist;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = IWhitelistBlacklist.class, remap = false)
public interface MixinIWhiteListBlackList {
    /**
     * @author iwolfking
     * @reason Forgive me for what must be done, injects and redirects were ignored ;-;.
     * Replaces Compararer check with Vault Filters check for FilterItems, adds support for (most) RS nodes except for exporters.
     */
    @Overwrite
    public static boolean acceptsItem(IItemHandler filters, int mode, int compare, ItemStack stack) {
        int i;
        ItemStack slot;
        if (mode == 0) {
            for (i = 0; i < filters.getSlots(); ++i) {
                slot = filters.getStackInSlot(i);
                if(slot.getItem() instanceof FilterItem) {
                    return VaultFilters.checkFilter(stack, slot, true, null);
                }
                if (API.instance().getComparer().isEqual(slot, stack, compare)) {
                    return true;
                }
            }

            return false;
        } else if (mode == 1) {
            for (i = 0; i < filters.getSlots(); ++i) {
                slot = filters.getStackInSlot(i);
                if(slot.getItem() instanceof FilterItem) {
                    return !VaultFilters.checkFilter(stack, slot, true, null);
                }
                if (API.instance().getComparer().isEqual(slot, stack, compare)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
