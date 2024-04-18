package net.joseph.vaultfilters.attributes.jewel;

import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class JewelCutsAttribute extends IntAttribute {
    public JewelCutsAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getTag() == null || !(itemStack.getTag().contains("freeCuts"))) {
            return null;
        }
        return itemStack.getTag().getInt("freeCuts");
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Integer value = getValue(itemStack);
        return value != null && value.equals(this.value);
    }

    @Override
    public String getTranslationKey() {
        return "cut_count";
    }

    @Override
    public String getLegacyKey() {
        return "cuts";
    }
}