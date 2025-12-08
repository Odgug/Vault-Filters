package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.StringListAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CardTypeAttribute extends StringListAttribute {
    public CardTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return "";
    }

    @Override
    public List<String> getValues(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }
        return new ArrayList<>(CardItem.getCard(itemStack).getGroups());
    }

    @Override
    public String getNBTKey() {
        return "card_type";
    }
}