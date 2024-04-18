package net.joseph.vaultfilters.attributes.soul;

import iskallia.vault.init.ModConfigs;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class AtleastSoulAttribute extends IntAttribute {
    public AtleastSoulAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        return ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().get(itemStack.getItem().getRegistryName());
    }

    @Override
    public String getTranslationKey() {
        return "atleast_soul";
    }

    @Override
    public String getLegacyKey() {
        return "atleastSoul";
    }
}