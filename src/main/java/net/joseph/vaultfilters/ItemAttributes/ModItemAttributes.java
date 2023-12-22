package net.joseph.vaultfilters.ItemAttributes;

import net.joseph.vaultfilters.ItemAttributes.custom.*;

public class ModItemAttributes {
    public static void register() {
        GearRarityAttribute.register();
        HasLegendaryAttribute.register();
        GearLevelAttribute.register();
        JewelSizeAttribute.register();
        GearRepairSlotAttribute.register();
        GearImplicitAttribute.register();
        GearPrefixAttribute.register();
        GearSuffixAttribute.register();

    }
}
