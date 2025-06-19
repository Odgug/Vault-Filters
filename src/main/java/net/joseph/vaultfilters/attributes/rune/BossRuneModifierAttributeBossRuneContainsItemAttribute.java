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
        String modifier = BossRuneItem.getModifier(itemStack);
        String suffix = BossRuneItem.getSuffixModifier(itemStack);
        if (modifier != null && value.equalsIgnoreCase(modifier)) {
            return value;
        }
        if (suffix != null && value.equalsIgnoreCase(suffix)) {
            return value;
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_modifier";
    }
}
