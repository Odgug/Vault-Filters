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
        String modifierName = BossRuneItem.getModifier(itemStack);
        return modifierName.isBlank() ? null : modifierName;
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{BossRuneItem.getModifierName(this.value)};
    }

    @Override
    public String getNBTKey() {
        return "boss_rune_modifier";
    }
}