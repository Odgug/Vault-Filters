package net.joseph.vaultfilters.attributes.soul;

import iskallia.vault.init.ModConfigs;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class HasSoulValueAttribute extends BooleanAttribute {
    public HasSoulValueAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        return ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().containsKey(itemStack.getItem().getRegistryName());
    }

    @Override
    public String getNBTKey() {
        return "has_soul";
    }

    @Override
    public String getLegacyKey() {
        return "soulValue";
    }
}