package net.joseph.vaultfilters.attributes.charm;

import iskallia.vault.item.gear.CharmItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;


public class CharmAffinityAttribute extends IntAttribute {
    public CharmAffinityAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (CharmItem.isIdentified(itemStack)) {
            return Math.round(CharmItem.getValue(itemStack) * 100.0F);
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "charm_affinity";
    }

    @Override
    public String getLegacyKey() {
        return "charmAffinity";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[] { this.value + "%" };
    }
}