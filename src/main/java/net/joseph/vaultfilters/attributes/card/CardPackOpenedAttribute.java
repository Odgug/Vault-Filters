package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.item.BoosterPackItem;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

import static iskallia.vault.item.CardItem.getCard;

public class CardPackOpenedAttribute extends BooleanAttribute {
    public CardPackOpenedAttribute(Boolean value) {
        super(true);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BoosterPackItem)) {
            return false;
        }
        return BoosterPackItem.getOutcomes(itemStack) != null;
    }

    @Override
    public String getTranslationKey() {
        return "card_pack_opened";
    }

}