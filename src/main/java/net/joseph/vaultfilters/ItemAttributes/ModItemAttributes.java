package net.joseph.vaultfilters.ItemAttributes;

import net.joseph.vaultfilters.ItemAttributes.custom.GearPrefixAttribute;
import net.joseph.vaultfilters.ItemAttributes.custom.GearRarityAttribute;
import net.joseph.vaultfilters.ItemAttributes.custom.HasLegendaryAttribute;

public class ModItemAttributes {
    public static void register() {
        GearRarityAttribute.register();
        HasLegendaryAttribute.register();
        GearPrefixAttribute.register();
    }
}
