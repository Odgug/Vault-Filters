package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

import static iskallia.vault.item.CardItem.getCard;

public class CardUpgradableAttribute extends BooleanAttribute {
    public CardUpgradableAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return false;
        }
        Card card = getCard(itemStack);
        return card.canUpgrade();
    }

    @Override
    public String getNBTKey() {
        return "card_upgradable";
    }
}