package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.mixin.data.CardEntryAccessor;
import net.joseph.vaultfilters.mixin.data.CardConditionAccessor;
import net.joseph.vaultfilters.mixin.data.ConditionFilterAccessor;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

import static iskallia.vault.item.CardItem.getCard;
import static net.joseph.vaultfilters.attributes.card.CardConditionNumAttribute.getCardMinMax;

public class CardConditionCompAttribute extends StringAttribute {
    public CardConditionCompAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        Tuple<Integer,Integer> counts = getCardMinMax(itemStack);
        if (counts == null) {
            return null;
        }
        Integer minCount = counts.getA();
        Integer maxCount = counts.getB();
        if (minCount == null && maxCount == null) {
            return null;
        }
        if (minCount != null && maxCount != null) {
            return "Between";
        }
        if (minCount == null) {
            return "At Most";
        }
        return "At Least";
    }

    @Override
    public String getTranslationKey() {
        return "card_condition_comp";
    }
}