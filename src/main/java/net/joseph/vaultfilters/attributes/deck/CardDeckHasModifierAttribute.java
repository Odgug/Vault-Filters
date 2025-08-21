package net.joseph.vaultfilters.attributes.deck;

import iskallia.vault.core.card.CardDeck;
import iskallia.vault.core.card.modifier.deck.DummyDeckModifier;
import iskallia.vault.item.CardDeckItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class CardDeckHasModifierAttribute extends BooleanAttribute {

    public CardDeckHasModifierAttribute(Boolean value) {
        super(value);
    }

    @Override
    public String getTranslationKey() {
        return "card_deck_has_modifier";
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if(itemStack.getItem() instanceof CardDeckItem) {
            CardDeck deck = CardDeckItem.getCardDeck(itemStack).orElse(null);

            if(deck == null) {
                return null;
            }

            return !deck.getModifiers().stream().filter(modifier -> !(modifier instanceof DummyDeckModifier)).toList().isEmpty();
        }

        return null;
    }
}
