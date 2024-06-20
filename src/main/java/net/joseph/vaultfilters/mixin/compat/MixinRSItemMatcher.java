package net.joseph.vaultfilters.mixin.compat;

import com.refinedmods.refinedstorage.apiimpl.util.Comparer;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Comparer.class, remap = false)
public class MixinRSItemMatcher {
    @Inject(method = "isEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;I)Z", at = @At("HEAD"), cancellable = true)
    public void checkFilter(ItemStack left, ItemStack right, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (VFServerConfig.RS_COMPAT.get() && flags == VaultFilters.CHECK_FILTER_FLAG && right.getItem() instanceof FilterItem) {
            cir.setReturnValue(VaultFilters.checkFilter(left, right,true,null));
        }
        if (VFServerConfig.RS_COMPAT.get() && flags == VaultFilters.NO_CACHE_FLAG && right.getItem() instanceof FilterItem) {
            cir.setReturnValue(VaultFilters.checkFilter(left, right,false,null));
        }
    }

}