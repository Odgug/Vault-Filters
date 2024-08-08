package net.joseph.vaultfilters.mixin.compat.ae2.mixin;

import appeng.api.behaviors.StackExportStrategy;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.util.prioritylist.FuzzyPriorityList;
import appeng.util.prioritylist.PrecisePriorityList;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.configs.VFServerConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(value = PrecisePriorityList.class, remap = false)
public abstract class MixinPrecisePriorityList {

    @Shadow public abstract Iterable<AEKey> getItems();

    @Shadow @Final private KeyCounter list;

    /**
     * @author iwolfking
     * @reason Doing thangs
     */
    @Inject(method = "isListed", at = @At("HEAD"), cancellable = true)
    public void isListed(AEKey input, CallbackInfoReturnable<Boolean> cir) {
        if(VFServerConfig.AE2_COMPAT.get()) {
            for (AEKey key : this.getItems()) {
                if (key instanceof AEItemKey itemKey) {
                    if (itemKey.getItem() instanceof FilterItem && input instanceof AEItemKey inputItemKey) {
                        if(!(inputItemKey.getItem() instanceof FilterItem)) {
                            cir.setReturnValue(VaultFilters.checkFilter(inputItemKey.toStack(), itemKey.toStack(), true, null) || this.list.get(input) > 0L);
                        }
                        else {
                            cir.setReturnValue(VaultFilters.checkFilter(inputItemKey.toStack(), itemKey.toStack(), true, null));
                        }

                    }
                }
            }
        }

    }
}
