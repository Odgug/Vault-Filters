package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.mixin.data.CardEntryAccessor;
import net.joseph.vaultfilters.mixin.data.CardConditionAccessor;
import net.joseph.vaultfilters.mixin.data.ConditionFilterAccessor;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

import static iskallia.vault.item.CardItem.getCard;

public class CardConditionCompAttribute extends StringAttribute {
    public CardConditionCompAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }
        Card card = getCard(itemStack);
        List<CardEntry> entries = card.getEntries();
        if (entries == null) {
            return null;
        }
        if (entries.isEmpty()) {
            return null;
        }
        CardCondition condition = ((CardEntryAccessor) entries.get(0)).getCondition();
        if (condition == null) {
            return null;
        }
        Map<Integer, List<CardCondition.Filter>> filters = ((CardConditionAccessor) condition).getFilters();
        if (filters == null) {
            return null;
        }
        for (Integer key : filters.keySet()) {
            for (CardCondition.Filter filter : filters.get(key)) {
                Integer minCount= ((ConditionFilterAccessor)filter).getMinCount();
                Integer maxCount= ((ConditionFilterAccessor)filter).getMaxCount();
                if (minCount == null && maxCount == null) {
                    continue;
                }
                if (minCount != null && maxCount != null) {
                    return "Between";
                }
                if (minCount == null) {
                    return "At Most";
                }
                return "At Least";
            }

        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "card_condition_comparison";
    }
}