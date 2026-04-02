package net.joseph.vaultfilters.attributes.deck_core;

import iskallia.vault.core.card.modifier.deck.DeckModifier;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.DeckSocketItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;


public class DeckCoreTypeAttribute extends StringAttribute {
    public DeckCoreTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        var deckModifier = DeckSocketItem.getDeckModifier(itemStack).orElse(null);
        if (deckModifier == null) return null;
        return deckModifier.getId();
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{ModConfigs.DECK_MODIFIERS.getById(this.value).map(DeckModifier::getName).orElse(this.value)};
    }

    @Override
    public String getNBTKey() {
        return "deck_core_type";
    }
}