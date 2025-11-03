package net.joseph.vaultfilters.attributes.card;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.CardNeighborType;
import iskallia.vault.item.CardItem;
import iskallia.vault.item.gear.CharmItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.joseph.vaultfilters.mixin.data.CardConditionAccessor;
import net.joseph.vaultfilters.mixin.data.CardEntryAccessor;
import net.joseph.vaultfilters.mixin.data.ConditionFilterAccessor;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static iskallia.vault.item.CardItem.getCard;

public class CardConditionNumAttribute extends IntAttribute {
    public CardConditionNumAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        return null;
    }
    @Override
    public NumComparator getComparator() {
        return null;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        Tuple<Integer,Integer> counts = getCardMinMax(itemStack);
        if (counts == null) {
            return new ArrayList<>();
        }

        List<ItemAttribute> attributes = new ArrayList<>();
        Integer minCount = counts.getA();
        Integer maxCount = counts.getB();
        if (minCount != null) {
            attributes.add(withValue(minCount));
        }
        if (maxCount != null) {
            attributes.add(withValue(maxCount));
        }

        return attributes;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        Tuple<Integer,Integer> counts = getCardMinMax(itemStack);
        if (counts == null) {
            return false;
        }

        Integer minCount = counts.getA();
        Integer maxCount = counts.getB();
        if (maxCount == null && minCount == null) {
            return false;
        } else if (maxCount != null && minCount != null) {
            return (this.value >= minCount && this.value <= maxCount);
        } else if (maxCount != null) {
            return this.value <= maxCount;
        }
        return this.value >= minCount;
    }

    @Override
    public String getTranslationKey() {
        return "card_condition_num";
    }

    public static Tuple<Integer,Integer> getCardMinMax(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }

        Card card = getCard(itemStack);
        List<CardEntry> entries = card.getEntries();
        if (entries == null || entries.isEmpty()) {
            return null;
        }

        CardEntry entry = entries.get(0);
        CardCondition condition = ((CardEntryAccessor)entry).getCondition();
        if (condition == null) {
            return null;
        }

        Map<Integer, List<CardCondition.Filter>> filters = ((CardConditionAccessor)condition).getFilters();
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        Tuple<Integer, Integer> counts = new Tuple<Integer,Integer>(null, null);
        for (int key : filters.keySet()) {
            for (CardCondition.Filter filter : filters.get(key)) {
                Integer tempMinCount= ((ConditionFilterAccessor)filter).getMinCount();
                Integer tempMaxCount= ((ConditionFilterAccessor)filter).getMaxCount();
                if (tempMinCount != null) {
                    counts.setA(tempMinCount);
                }
                if (tempMaxCount != null) {
                    counts.setB(tempMaxCount);
                }
            }
        }

        return counts;
    }
}