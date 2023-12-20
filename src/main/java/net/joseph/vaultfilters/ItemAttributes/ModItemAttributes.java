package net.joseph.vaultfilters.ItemAttributes;

import net.joseph.vaultfilters.ItemAttributes.custom.*;

public class ModItemAttributes {
    public static void register() {
        GearRarityAttribute.register();
        HasLegendaryAttribute.register();
        GearImplicitAttribute.register();
        GearPrefixAttribute.register();
        GearSuffixAttribute.register();
        GearLevelAttribute.register();
        JewelSizeAttribute.register();
    }
}
