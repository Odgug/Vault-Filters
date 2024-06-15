package net.joseph.vaultfilters.caching;

import net.minecraft.world.item.ItemStack;

public interface IVFGearDataCache {
    boolean vaultfilters$hasLegendaryAttribute();
    int vaultfilters$getRepairSlots();
    int vaultfilters$getUsedRepairSlots();
    int vaultfilters$getExtraGearLevel();
    Integer vaultfilters$getExtraJewelSize();
    boolean vaultfilters$testFilter(ItemStack filterStack);
}
