package net.joseph.vaultfilters.mixin.compat.ae2.mixin;

import appeng.api.behaviors.StackTransferContext;
import appeng.api.config.Actionable;
import appeng.api.networking.storage.IStorageService;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.storage.StorageHelper;
import appeng.parts.automation.HandlerStrategy;
import appeng.parts.automation.StorageExportStrategy;
import appeng.util.BlockApiCache;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterItem;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.joseph.vaultfilters.VFTests;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.core.Direction;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = StorageExportStrategy.class, remap = false)
public class MixinStorageExportStrategy<C> {
    @Shadow @Final
    private HandlerStrategy<C, CallbackI.S> handlerStrategy;
    @Shadow @Final
    private BlockApiCache<C> apiCache;
    @Shadow @Final
    private Direction fromSide;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public long transfer(StackTransferContext context, AEKey what, long amount, Actionable mode) {
        if (!handlerStrategy.isSupported(what)) {
            return 0;
        }

        C adjacentStorage = apiCache.find(fromSide);
        if (adjacentStorage == null) {
            return 0;
        }

        IStorageService inv = context.getInternalStorage();
        if (what instanceof AEItemKey itemKey && VFServerConfig.AE2_COMPAT.get() && itemKey.getItem() instanceof FilterItem) {
            for (Object2LongMap.Entry<AEKey> key : inv.getInventory().getAvailableStacks()) {
                AEKey aek = key.getKey();
                if (!(aek instanceof AEItemKey itemKey2)) {
                    continue;
                }

                if (VFTests.checkFilter(itemKey2, itemKey, true, null)) {
                    what = aek;
                    break;
                }
            }
        }

        long extracted = StorageHelper.poweredExtraction(
                context.getEnergySource(),
                inv.getInventory(),
                what,
                amount,
                context.getActionSource(),
                Actionable.SIMULATE);
        long wasInserted = handlerStrategy.insert(adjacentStorage, what, extracted, Actionable.SIMULATE);

        if (wasInserted > 0) {
            if (mode == Actionable.MODULATE) {
                extracted = StorageHelper.poweredExtraction(
                        context.getEnergySource(),
                        inv.getInventory(),
                        what,
                        wasInserted,
                        context.getActionSource(),
                        Actionable.MODULATE);
                wasInserted = handlerStrategy.insert(adjacentStorage, what, extracted, Actionable.MODULATE);

                if (wasInserted < extracted) {
                    // Be nice and try to give the overflow back
                    long leftover = extracted - wasInserted;
                    leftover -= inv.getInventory().insert(what, leftover, Actionable.MODULATE, context.getActionSource());
                    if (leftover > 0) {

                    }
                }
            }

            return wasInserted;
        }

        return 0;
    }
//    @ModifyVariable(
//            method = "transfer",
//            at = @At(
//                    value = "INVOKE_ASSIGN",
//                    target = "Lappeng/api/behaviors/StackTransferContext;getInternalStorage()Lappeng/api/networking/storage/IStorageService;",
//                    shift = At.Shift.AFTER
//            ),
//            ordinal = 1 //parameter what
//    )
//    private AEKey modifyWhat(AEKey originalWhat, StackTransferContext context, long amount, Actionable mode, @Local IStorageService inv) {
//        if (originalWhat instanceof AEItemKey itemKey && VFServerConfig.AE2_COMPAT.get() && itemKey.getItem() instanceof FilterItem) {
//            for (Object2LongMap.Entry<AEKey> key : inv.getInventory().getAvailableStacks()) {
//                AEKey aek = key.getKey();
//                if (!(aek instanceof AEItemKey itemKey2)) continue;
//
//                if (VFTests.checkFilter(itemKey2, itemKey, true, null)) {
//                    return aek;
//                }
//            }
//        }
//
//        return originalWhat;
//    }

}
