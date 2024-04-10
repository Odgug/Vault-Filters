package net.joseph.vaultfilters.attributes.charm;

import iskallia.vault.item.gear.CharmItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CharmUsesAttribute extends IntAttribute {
    public CharmUsesAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (CharmItem.isIdentified(itemStack)) {
            return CharmItem.getUses(itemStack) - CharmItem.getUsedVaults(itemStack).size();
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "charm_uses";
    }

    @Override
    public String getSubNBTKey() {
        return "charmUses";
    }
}