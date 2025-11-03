package net.joseph.vaultfilters.attributes.companion;

import iskallia.vault.item.CompanionItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CompanionLevelAttribute extends IntAttribute {
    public CompanionLevelAttribute(Integer value) {
        super(value);
    }

    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }


    @Override
    public Integer getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionItem)) {
            return null;
        }


        return CompanionItem.getCompanionLevel(itemStack);
    }

    @Override
    public String getTranslationKey() {
        return "companion_level";
    }

}
