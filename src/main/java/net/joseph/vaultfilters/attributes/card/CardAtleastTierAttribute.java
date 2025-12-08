package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CardAtleastTierAttribute extends IntAttribute {
    public CardAtleastTierAttribute(Integer value) {
        super(value);
    }

    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }
    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CardItem) {
            return CardItem.getCard(itemStack).getTier();
        }
        return null;
    }

    @Override
    public String getNBTKey() {
        return "card_tier";
    }
}