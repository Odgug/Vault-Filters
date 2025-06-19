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
    public Object[] getTranslationParameters() {
        // Convert internal modifier string to user-friendly label
        String display = this.value;
        if (display != null && !display.isEmpty()) {
            // Replace underscores/hyphens with spaces, capitalize words
            display = display.trim()
                    .replace('_', ' ')
                    .replace('-', ' ')
                    .toLowerCase();
            String[] words = display.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    sb.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
                }
            }
            display = sb.toString().trim();
        }
        return new Object[]{display};
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_modifier";
    }
}