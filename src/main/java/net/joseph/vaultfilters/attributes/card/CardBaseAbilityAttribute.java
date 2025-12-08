package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.modifier.card.CardModifier;
import iskallia.vault.core.card.modifier.card.GearCardModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.attributes.affix.BaseAbilityAffixAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CardBaseAbilityAttribute extends StringAttribute {
    public CardBaseAbilityAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }

        Card card = CardItem.getCard(itemStack);
        List<CardEntry> entries = card.getEntries();
        if (entries == null || entries.isEmpty()) {
            return null;
        }

        CardEntry entry = entries.get(0);
        if (entry == null) {
            return null;
        }

        CardModifier<?> modifier = entry.getModifier();
        if (modifier == null) {
            return null;
        }
        if (modifier instanceof GearCardModifier<?> gearModifier) {
            if (gearModifier.getValue(1) instanceof AbilityLevelAttribute abilityLevelAttribute) {
                return BaseAbilityAffixAttribute.getBaseAbility(abilityLevelAttribute);
            }
        }
        return null;
    }

    @Override
    public String getNBTKey() {
        return "base_ability_card";
    }
}
