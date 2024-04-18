package net.joseph.vaultfilters.attributes.trinket;

import iskallia.vault.item.gear.TrinketItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

import static iskallia.vault.item.gear.TrinketItem.isIdentified;

public class TrinketColorAttribute extends StringAttribute {
    public TrinketColorAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof TrinketItem)) {
            return null;
        }

        if (!isIdentified(itemStack)) {
            return "Pink";
        }

        String identifier = TrinketItem.getSlotIdentifier(itemStack).orElse("");
        if (identifier.contains("blue")) {
            return "Blue";
        } else if (identifier.contains("red")) {
            return "Red";
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "trinket_color";
    }

    @Override
    public String getLegacyKey() {
        return "trinketColor";
    }
}