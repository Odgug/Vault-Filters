package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.*;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.attributes.abstracts.StringListAttribute;
import net.joseph.vaultfilters.mixin.data.CardEntryAccessor;
import net.joseph.vaultfilters.mixin.data.ConditionFilterAccessor;
import net.joseph.vaultfilters.mixin.data.FilterAccessor;
import net.joseph.vaultfilters.mixin.data.ScalerFilterAccessor;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static iskallia.vault.item.CardItem.getCard;

public class CardConditionGroupsAttribute extends StringListAttribute {
    public CardConditionGroupsAttribute(String value) {
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


        List<CardEntry> entries = card.getEntries();
        if (entries == null) {
            return null;
        }
        if (entries.isEmpty()) {
            return null;
        }
        CardEntry entry = entries.get(0);
        CardCondition condition = ((CardEntryAccessor)entry).getCondition();
        if (condition == null) {
            return null;
        }
        Map<Integer, List<CardScaler.Filter>> filters = ((ConditionFilterAccessor)condition).getFilters();
        if (filters == null) {
            return null;
        }
        ArrayList<String> categoryList = new ArrayList<String>();
        for (int key : filters.keySet()) {
            for (CardScaler.Filter filter : filters.get(key)) {
                Set<CardNeighborType> neighborFilter = ((FilterAccessor)filter).getNeighborFilter();
                if (neighborFilter != null) {
                    for (CardNeighborType neighbor : neighborFilter) {
                        categoryList.add(neighbor.name());
                    }
                }
                Set<Integer> tierFilter = ((FilterAccessor)filter).getTierFilter();
                if (tierFilter != null) {
                    for (Integer tier : tierFilter) {
                        categoryList.add("Tier " + String.valueOf(tier));
                    }
                }
                Set<CardEntry.Color> colorFilter = ((FilterAccessor)filter).getColorFilter();
                if (colorFilter != null) {
                    for (CardEntry.Color color : colorFilter) {
                        categoryList.add(color.name());
                    }
                }
                Set<String> groupFilter = ((FilterAccessor)filter).getGroupFilter();
                if (groupFilter != null) {
                    for (String group : groupFilter) {
                        categoryList.add(group);
                    }
                }
            }
        }


        return categoryList;
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.value.charAt(0) + this.value.substring(1).toLowerCase()};
    }

    @Override
    public String getTranslationKey() {
        return "card_condition_group";
    }
}