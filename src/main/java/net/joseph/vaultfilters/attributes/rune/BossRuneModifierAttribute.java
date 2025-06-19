package net.joseph.vaultfilters.attributes.rune;

import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class BossRuneModifierAttribute extends StringAttribute {
    public BossRuneModifierAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) {
            return null;
        }
        if (itemStack.hasTag() && itemStack.getTag().contains("Modifier")) {
            return itemStack.getTag().getString("Modifier");
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_modifier";
    }
}
