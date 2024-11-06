package net.joseph.vaultfilters.mixin.compat.refinedstorage.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.refinedmods.refinedstorage.api.util.Action;
import com.refinedmods.refinedstorage.apiimpl.storage.externalstorage.ItemExternalStorage;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemExternalStorage.class,remap = false)
public class ItemExternalStorageMixin {
    @Redirect(method="extract(Lnet/minecraft/world/item/ItemStack;IILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;grow(I)V",remap = true)
    )
    private void checkGrow(ItemStack instance, int pIncrement, @Local(ordinal=1) ItemStack received, @Local(ordinal = 3) ItemStack got) {
        //VaultFilters.LOGGER.info("received has " + received.getItem().getDescriptionId() + " " + received.getCount());
        //VaultFilters.LOGGER.info("got has " + got.getItem().getDescriptionId() + " " + got.getCount());
            if (ItemStack.isSameItemSameTags(received,got)) {
                received.grow(got.getCount());
            }

    }
}
