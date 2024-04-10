package net.joseph.vaultfilters.attributes.trinket;

import iskallia.vault.item.gear.TrinketItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class TrinketUsesAttribute extends IntAttribute {
    public TrinketUsesAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (TrinketItem.isIdentified(itemStack)) {
            return TrinketItem.getUses(itemStack) - TrinketItem.getUsedVaults(itemStack).size();
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "trinket_uses";
    }

    @Override
    public String getSubNBTKey() {
        return "trinketUses";
    }
}