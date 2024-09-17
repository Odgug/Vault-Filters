package net.joseph.vaultfilters.attributes.packs;

import iskallia.vault.item.BoosterPackItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class CardPackOpenedAttribute extends BooleanAttribute {
    public CardPackOpenedAttribute(Boolean value) {
        super(value);
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