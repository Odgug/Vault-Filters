package net.joseph.vaultfilters.attributes.trinket;

import iskallia.vault.item.gear.TrinketItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class TrinketUsesAttribute extends IntAttribute {
    public TrinketUsesAttribute(Integer value) {
        super(value);
    }
    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (TrinketItem.isIdentified(itemStack)) {
            return TrinketItem.getUses(itemStack) - TrinketItem.getUsedVaults(itemStack).size();
        }
        return null;
    }

    @Override
    public String getNBTKey() {
        return "trinket_uses";
    }

    @Override
    public String getLegacyKey() {
        return "trinketUses";
    }
}