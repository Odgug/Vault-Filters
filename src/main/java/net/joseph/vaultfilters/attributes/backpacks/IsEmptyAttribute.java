package net.joseph.vaultfilters.attributes.backpacks;

import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.mixin.compat.sophisticatedcore.mixin.InventoryHandlerSlotTrackerAccessor;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class IsEmptyAttribute extends BooleanAttribute
{
    public IsEmptyAttribute(Boolean value)
    {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack)
    {
        return isEmpty(itemStack);
    }
    
    public static Boolean isEmpty(ItemStack stack)
    {
        AtomicBoolean empty = new AtomicBoolean(false);
        if(stack.getItem() instanceof BackpackItem) {
            stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).ifPresent(iBackpackWrapper -> {
                empty.set(((InventoryHandlerSlotTrackerAccessor) iBackpackWrapper.getInventoryHandler().getSlotTracker()).getEmptySlots().size() == ((BackpackItem) stack.getItem()).getNumberOfSlots());
            });
        }
        
        return empty.get();
    }

    @Override
    public String getNBTKey()
    {
        return "bag_empty";
    }

}
