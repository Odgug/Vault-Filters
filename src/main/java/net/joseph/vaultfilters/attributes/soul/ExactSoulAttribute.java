package net.joseph.vaultfilters.attributes.soul;

import iskallia.vault.init.ModConfigs;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class ExactSoulAttribute extends IntAttribute {
    public ExactSoulAttribute(Integer value) {
        super(value);
    }
    @Override
    public NumComparator getComparator() {
        return NumComparator.EQUAL;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        return ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().get(itemStack.getItem().getRegistryName());
    }


    @Override
    public String getTranslationKey() {
        return "exact_soul";
    }

    @Override
    public String getLegacyKey() {
        return "exactSoul";
    }
}