package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringListAttribute;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static iskallia.vault.item.CardItem.getCard;

public class CardColorAttribute extends StringListAttribute {
    public CardColorAttribute(String value) {
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
        Set<CardEntry.Color> colors = card.getColors();
        ArrayList<String> colorStrings = new ArrayList<String>();
        for (CardEntry.Color color : colors) {
            colorStrings.add(color.name());
        }
        return colorStrings;
    }


    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.value.charAt(0) + this.value.substring(1).toLowerCase()};
    }
    @Override
    public String getTranslationKey() {
        return "card_color";
    }
}