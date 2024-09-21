package net.joseph.vaultfilters.mixin.compat.refinedstorage.mixin;

import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.blockentity.config.IWhitelistBlacklist;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VFTests;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = IWhitelistBlacklist.class, remap = false)
public interface MixinIWhiteListBlackList {
    /**
     * @author iwolfking
     * @reason Forgive me for what must be done, injects and redirects were ignored ;-;.
     * Replaces Compararer check with Vault Filters check for FilterItems, adds support for (most) RS nodes except for exporters.
     */
    @Overwrite
    static boolean acceptsItem(IItemHandler filters, int mode, int compare, ItemStack stack) {
        if (mode != 0 && mode != 1) {
            return false;
        }

        boolean blacklist = mode == 1;
        for (int i = 0; i < filters.getSlots(); ++i) {
            ItemStack slot = filters.getStackInSlot(i);
            if (slot.getItem() instanceof FilterItem && VFServerConfig.RS_COMPAT.get()) {
                boolean result = VFTests.checkFilter(stack, slot, true, null);
                if (blacklist || result) {
                    return blacklist != result;
                }
            } else if (API.instance().getComparer().isEqual(slot, stack, compare)) {
                return !blacklist;
            }
        }

        return blacklist;
    }
}
