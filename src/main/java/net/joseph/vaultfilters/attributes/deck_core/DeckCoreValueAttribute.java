package net.joseph.vaultfilters.attributes.deck_core;

import iskallia.vault.item.DeckSocketItem;
import net.joseph.vaultfilters.attributes.abstracts.FloatAttribute;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

/**
 ✅ Azure        	All Blue cards will be X% more efficient<br>
 ✅ Crimson 	    All Red cards will be X% more efficient<br>
 ✅ Viridian 	    All Green cards will be X% more efficient<br>
 ✅ Golden 	        All Yellow cards will be X% more efficient<br>
 ✅ Equilibrium 	+X% Stat card efficiency per unique deck colour<br>
 ✅ Shiny 	        All Foil cards will be X% more efficient<br>
 ✅ Steadfast 	    All Stat cards will be X% more efficient<br>
 ✅ Harvest 	    -X% Resource card requirements<br>
 ✅ Fortune 	    +X% chance for Resource cards to double rewards<br>
 <br>
 ⚠️ Empyreal 	    ✅ +X efficiency for ❌ NUMBER ❌ TYPE cards {@link iskallia.vault.core.card.modifier.deck.SlotDeckModifier}<br>
 ❌ Bounty 	        ❌ +X Crate Tier when completing a vault with at least ❌ X Resource Card(s) {@link iskallia.vault.core.card.modifier.deck.BountyDeckModifier}<br>
 */
public class DeckCoreValueAttribute extends FloatAttribute {
    public DeckCoreValueAttribute(Float value) {
        super(value);
    }

    @Override
    public IntAttribute.NumComparator getComparator() {
        return IntAttribute.NumComparator.AT_LEAST;
    }

    @Override
    public Float getValue(ItemStack itemStack) {
        var deckModifier = DeckSocketItem.getDeckModifier(itemStack).orElse(null);
        if (deckModifier == null) return null;
        return deckModifier.getModifierValue();
    }

    @Override public Object[] getTranslationParameters() {
        return new Object[] {String.format("%.2f%%", value * 100)};
    }

    @Override
    public String getNBTKey() {
        return "deck_core_value";
    }

}
