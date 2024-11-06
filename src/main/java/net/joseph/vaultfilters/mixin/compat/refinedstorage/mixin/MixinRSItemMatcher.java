package net.joseph.vaultfilters.mixin.compat.refinedstorage.mixin;

import com.refinedmods.refinedstorage.apiimpl.util.Comparer;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VFTests;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(value = Comparer.class, remap = false)
public class MixinRSItemMatcher {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean isEqual(@Nonnull ItemStack left, @Nonnull ItemStack right, int flags) {
        if (flags >= VaultFilters.CHECK_FILTER_FLAG) {
            flags = flags - VaultFilters.CHECK_FILTER_FLAG;
            if (VFServerConfig.RS_COMPAT.get() && right.getItem() instanceof FilterItem) {
                return VFTests.checkFilter(left, right,true,null);
            }
        }
        if (left.isEmpty() && right.isEmpty()) {
            return true;
        } else if (!ItemStack.isSame(left, right)) {
            return false;
        } else {
            return (flags & 1) == 1 && !ItemStack.tagMatches(left, right) ? false : (flags & 2) != 2 || left.getCount() == right.getCount();
        }
    }
}