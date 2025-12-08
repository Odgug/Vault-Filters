package net.joseph.vaultfilters.attributes.other;

import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public class ItemNameAttribute extends StringAttribute {
    public ItemNameAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return itemStack.getItem().getDescriptionId();
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[] { new TranslatableComponent(this.value).getString() };
    }
    @Override
    public String getNBTKey() {
        return "item_name";
    }
}
