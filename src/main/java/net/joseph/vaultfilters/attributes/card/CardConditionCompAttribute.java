package net.joseph.vaultfilters.attributes.card;

import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

public class CardConditionCompAttribute extends StringAttribute {
    public CardConditionCompAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        Tuple<Integer, Integer> counts = CardConditionNumAttribute.getCardMinMax(itemStack);
        if (counts == null) {
            return null;
        }

        Integer minCount = counts.getA();
        Integer maxCount = counts.getB();
        if (minCount == null && maxCount == null) {
            return null;
        } else if (minCount != null && maxCount != null) {
            return "Between";
        } else if (minCount == null) {
            return "At Most";
        }
        return "At Least";
    }

    @Override
    public String getNBTKey() {
        return "card_condition_comp";
    }
}