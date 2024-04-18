package net.joseph.vaultfilters.mixin;

import com.refinedmods.refinedstorage.api.util.Action;
import com.refinedmods.refinedstorage.api.util.IComparer;
import com.refinedmods.refinedstorage.apiimpl.network.node.ExporterNetworkNode;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ExporterNetworkNode.class, remap = false)
public class ExporterNetworkNodeMixin {
    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lcom/refinedmods/refinedstorage/api/util/IComparer;isEqual(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;I)Z"))
    private int modifyCompareFlag(ItemStack left, ItemStack right, int compare){
        if (right.getItem() instanceof FilterItem){
            return VaultFilters.CHECK_FILTER_FLAG;
        }
        return compare;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lcom/refinedmods/refinedstorage/api/network/INetwork;extractItem(Lnet/minecraft/world/item/ItemStack;IILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;"), index = 2)
    private int modifyCompareFlag(ItemStack slot, int size, int compare, Action action){
        if (slot.getItem() instanceof FilterItem){
            return VaultFilters.CHECK_FILTER_FLAG;
        }
        return compare;
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lcom/refinedmods/refinedstorage/api/util/IComparer;isEqualNoQuantity(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean EqualsNoQty(IComparer instance, ItemStack left, ItemStack right){
        if (right.getItem() instanceof FilterItem){
            return FilterItem.test(null, left, right);
        }
        return instance.isEqualNoQuantity(left, right);
    }
}