package net.joseph.vaultfilters.attributes.other;

import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class ItemNameAttribute extends StringAttribute {
    public ItemNameAttribute(String value) {super(value);}

    @Override
    public String getValue(ItemStack itemStack) {
        return itemStack.getItem().getDescriptionId();
    }

    @Override
    public String getTranslationKey() {
        return "item_name";
    }
}
