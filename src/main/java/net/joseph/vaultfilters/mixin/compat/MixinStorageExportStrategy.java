package net.joseph.vaultfilters.mixin.compat;

import appeng.api.behaviors.StackTransferContext;
import appeng.api.config.Actionable;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.storage.StorageHelper;
import appeng.parts.automation.HandlerStrategy;
import appeng.parts.automation.StorageExportStrategy;
import appeng.util.BlockApiCache;
import com.simibubi.create.content.logistics.filter.FilterItem;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.joseph.vaultfilters.VaultFilters;
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
    /**
     * @author
     * @reason
     */
    @Final
    @Shadow
    private HandlerStrategy<C, CallbackI.S> handlerStrategy;


    @Final
    @Shadow
    private BlockApiCache<C> apiCache;

    @Final
    @Shadow
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

        var adjacentStorage = apiCache.find(fromSide);
        if (adjacentStorage == null) {
            return 0;
        }

        var inv = context.getInternalStorage();

        if (what instanceof AEItemKey filterItemKey) {

            if (VFServerConfig.AE2_COMPAT.get() && filterItemKey.getItem() instanceof FilterItem) {
                ItemStack filterStack = filterItemKey.toStack();
                Iterable<Object2LongMap.Entry<AEKey>> invitems = inv.getInventory().getAvailableStacks();
                for (Object2LongMap.Entry<AEKey> key : invitems) {
                    AEKey aek = key.getKey();
                    if (!(aek instanceof AEItemKey)) {
                        continue;
                    }
                    ItemStack stack = ((AEItemKey) aek).toStack();
                    if (VaultFilters.checkFilter(stack,filterStack,true,null)) {
                        what = aek;
                        break;
                    }
                }
            }
        }

        var extracted = StorageHelper.poweredExtraction(
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
                    leftover -= inv.getInventory().insert(what, leftover, Actionable.MODULATE,
                            context.getActionSource());
                    if (leftover > 0) {
                        //LOGGER.error("Storage export: adjacent block unexpectedly refused insert, voided {}x{}",
                        //leftover, what);
                    }
                }
            }

            return wasInserted;
        }

        return 0;
    }
}
