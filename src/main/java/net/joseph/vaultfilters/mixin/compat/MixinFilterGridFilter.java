package net.joseph.vaultfilters.mixin.compat;

import com.refinedmods.refinedstorage.screen.grid.filtering.FilterGridFilter;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = FilterGridFilter.class, remap = false)
public class MixinFilterGridFilter {
    @ModifyArg(method = "test(Lcom/refinedmods/refinedstorage/screen/grid/stack/IGridStack;)Z", at = @At(value = "INVOKE", target = "Lcom/refinedmods/refinedstorage/api/util/IComparer;isEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;I)Z"))
    private int modifyCompareFlag(ItemStack itemStack, ItemStack filterItemStack, int compare) {
        if (filterItemStack.getItem() instanceof FilterItem) {
            return VaultFilters.CHECK_FILTER_FLAG;
        }
        return compare;
    }
}
