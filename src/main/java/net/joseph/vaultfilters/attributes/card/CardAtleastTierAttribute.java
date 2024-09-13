package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CardAtleastTierAttribute extends IntAttribute {
    public CardAtleastTierAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CardItem) {
            return CardItem.getCard(itemStack).getTier();
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "card_tier";
    }
}