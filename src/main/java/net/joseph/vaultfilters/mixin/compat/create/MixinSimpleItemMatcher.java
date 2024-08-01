package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.FilterItem;
import me.desht.modularrouters.logic.filter.Filter;
import me.desht.modularrouters.logic.filter.matchers.SimpleItemMatcher;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Create filter integration for modular routers by radimous on GitHub rizek_ on Discord, massive thanks.

@Mixin(value = SimpleItemMatcher.class, remap = false)
public class MixinSimpleItemMatcher {
    @Shadow
    @Final
    private ItemStack filterStack;

    @Inject(method = "matchItem", at = @At("HEAD"), cancellable = true)
    public void createFilterMatcher(ItemStack stack, Filter.Flags flags, CallbackInfoReturnable<Boolean> cir) {
        if (VFServerConfig.MR_COMPAT.get() && filterStack.getItem() instanceof FilterItem) {
            cir.setReturnValue(VaultFilters.checkFilter(stack, this.filterStack,true,null));
        }
    }
}