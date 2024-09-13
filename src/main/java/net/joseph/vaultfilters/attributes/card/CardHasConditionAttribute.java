package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.mixin.data.CardEntryAccessor;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static iskallia.vault.item.CardItem.getCard;

public class CardHasConditionAttribute extends BooleanAttribute {
    public CardHasConditionAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return false;
        }

        Card card = getCard(itemStack);
        List<CardEntry> entries = card.getEntries();
        if (entries == null || entries.isEmpty()) {
            return null;
        }
        return ((CardEntryAccessor)entries.get(0)).getCondition() != null;
    }

    @Override
    public String getTranslationKey() {
        return "card_has_condition";
    }
}