package net.joseph.vaultfilters.attributes.deck;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.CardDeckItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class CardDeckTypeAttribute extends StringAttribute {
    public CardDeckTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if(itemStack.getItem() instanceof CardDeckItem) {
            return ModConfigs.CARD_DECK.getName(CardDeckItem.getId(itemStack)).orElse(null);
        }

        return null;
    }

    @Override
    public String getNBTKey() {
        return "card_deck_type";
    }
}
