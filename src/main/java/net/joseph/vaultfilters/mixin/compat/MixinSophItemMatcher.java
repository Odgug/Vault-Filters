package net.joseph.vaultfilters.mixin.compat;

import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FilterLogicBase.class, remap = false)
public class MixinSophItemMatcher {
    @Inject(method = "stackMatchesFilter", at = @At("HEAD"), cancellable = true)
    public void sophFilterMatcher(ItemStack stack, ItemStack filterStack, CallbackInfoReturnable<Boolean> cir) {
        if (VFServerConfig.BACKPACKS_COMPAT.get() && filterStack.getItem() instanceof FilterItem) {
            cir.setReturnValue(VaultFilters.checkFilter(stack, filterStack,true));
        }
    }
}
