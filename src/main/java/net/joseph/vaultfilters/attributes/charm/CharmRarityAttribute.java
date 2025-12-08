package net.joseph.vaultfilters.attributes.charm;

import iskallia.vault.init.ModItems;
import iskallia.vault.item.gear.CharmItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class CharmRarityAttribute extends StringAttribute {
    public CharmRarityAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CharmItem)) {
            return null;
        }

        if (itemStack.getItem() == ModItems.SMALL_CHARM) {
            return "Noble";
        } else if (itemStack.getItem() == ModItems.LARGE_CHARM) {
            return "Distinguished";
        } else if (itemStack.getItem() == ModItems.GRAND_CHARM) {
            return "Regal";
        } else {
            return "Majestic";
        }
    }

    @Override
    public String getNBTKey() {
        return "charm_rarity";
    }

    @Override
    public String getLegacyKey() {
        return "charmRarity";
    }
}