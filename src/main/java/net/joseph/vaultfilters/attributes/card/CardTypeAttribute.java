package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.StringListAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static iskallia.vault.item.CardItem.getCard;

public class CardTypeAttribute extends StringListAttribute {
    public CardTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return "";
    }

    @Override

    public ArrayList<String> getValues(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }
        Card card = getCard(itemStack);

        ArrayList<String> categoryList = new ArrayList<String>();
        List<String> grouplist = card.getGroups();
        for (String category : grouplist) {
            categoryList.add(category);
        }
        return categoryList;
    }



    @Override
    public String getTranslationKey() {
        return "card_type";
    }
}