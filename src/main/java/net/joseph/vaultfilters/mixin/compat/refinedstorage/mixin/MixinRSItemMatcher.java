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
    @Inject(method = "isEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;I)Z", at = @At("HEAD"), cancellable = true)
    public void checkFilter(ItemStack left, ItemStack right, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (!VFServerConfig.RS_COMPAT.get() || !(right.getItem() instanceof FilterItem) || flags != VaultFilters.CHECK_FILTER_FLAG) {
            return;
        }
        cir.setReturnValue(VFTests.checkFilter(left, right, true, null));
    }
}