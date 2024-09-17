package net.joseph.vaultfilters.mixin.compat.tomsstorage.mixin;

import com.simibubi.create.content.logistics.filter.FilterItem;
import com.tom.storagemod.util.FilteredInventoryHandler;
import net.joseph.vaultfilters.VFTests;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FilteredInventoryHandler.class, remap = false)
public class MixinFilteredInventoryHandler {
    @Shadow
    private Container filter;

    @Inject(method = "isInFilter", at = @At("HEAD"), cancellable = true)
    private void checkVaultFilter(ItemStack checkStack, CallbackInfoReturnable<Boolean> cir) {
        if (!VFServerConfig.TOMS_COMPAT.get()) {
            return;
        }

        for (int i = 0; i < this.filter.getContainerSize(); ++i) {
            ItemStack itemStack = this.filter.getItem(i);
            if (itemStack.getItem() instanceof FilterItem && VFTests.checkFilter(checkStack, itemStack, true, null)) {
                cir.setReturnValue(true);
                return;
            }
        }
    }
}
