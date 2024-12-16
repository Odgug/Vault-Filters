package net.joseph.vaultfilters.mixin.compat.sophisticatedcore.mixin;

import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandlerSlotTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(value = InventoryHandlerSlotTracker.class, remap = false)
public interface InventoryHandlerSlotTrackerAccessor
{
    @Accessor("emptySlots")
    public Set<Integer> getEmptySlots();
}
