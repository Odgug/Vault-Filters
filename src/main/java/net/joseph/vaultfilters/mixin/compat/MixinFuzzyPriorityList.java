package net.joseph.vaultfilters.mixin.compat;

import appeng.api.config.FuzzyMode;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.util.prioritylist.FuzzyPriorityList;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.configs.VFServerConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FuzzyPriorityList.class, remap = false)
public abstract class MixinFuzzyPriorityList {

    @Shadow @Final private KeyCounter list;
    @Shadow @Final private FuzzyMode mode;
    @Shadow public abstract Iterable<AEKey> getItems();

    @Inject(method = "isListed", at = @At("HEAD"), cancellable = true)
    public void isListed(AEKey input, CallbackInfoReturnable<Boolean> cir) {
        if(VFServerConfig.AE2_COMPAT.get()) {
            for (AEKey key : this.getItems()) {
                if (key instanceof AEItemKey itemKey) {
                    if (itemKey.getItem() instanceof FilterItem && input instanceof AEItemKey inputItemKey) {
                        if (!(inputItemKey.getItem() instanceof FilterItem)) {
                            cir.setReturnValue(VaultFilters.checkFilter(inputItemKey.toStack(), itemKey.toStack(), true, null) || !this.list.findFuzzy(input, this.mode).isEmpty());
                        } else {
                            cir.setReturnValue(VaultFilters.checkFilter(inputItemKey.toStack(), itemKey.toStack(), true, null));
                        }

                    }
                }
            }
        }
    }

}
