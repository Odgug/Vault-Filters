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
import com.simibubi.create.content.logistics.filter.FilterItem;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.joseph.vaultfilters.VFTests;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = StorageExportStrategy.class, remap = false)
public class MixinStorageExportStrategy<C> {
    @Shadow @Final
    private HandlerStrategy<C, CallbackI.S> handlerStrategy;
    @Shadow @Final
    private BlockApiCache<C> apiCache;
    @Shadow @Final
    private Direction fromSide;

    /**
     * @author TODO
     * @reason TODO: Add a reason
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
                        // TODO: maybe handle this better?
                    }
                }
            }

            return wasInserted;
        }

        return 0;
    }
}
