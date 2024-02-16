package net.joseph.vaultfilters.mixin;

import com.refinedmods.refinedstorage.apiimpl.util.Comparer;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import net.joseph.vaultfilters.vaultfilters;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Comparer.class, remap = false)
public class MixinRSItemMatcher {
    @Inject(method = "isEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;I)Z", at = @At("HEAD"), cancellable = true)
    public void checkFilter(ItemStack left, ItemStack right, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (flags == vaultfilters.CHECK_FILTER_FLAG && right.getItem() instanceof FilterItem) {
            cir.setReturnValue(FilterItemStack.of(right).test(null, left));
        }
    }

}