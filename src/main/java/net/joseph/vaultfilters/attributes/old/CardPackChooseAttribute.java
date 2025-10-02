package net.joseph.vaultfilters.attributes.old;

import iskallia.vault.item.BoosterPackItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class CardPackChooseAttribute extends BooleanAttribute {
    public CardPackChooseAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BoosterPackItem)) {
            return false;
        }
        return false;
        //return BoosterPackItem.getOutcomes(itemStack) != null;
    }

    @Override
    public String getTranslationKey() {
        return "card_pack_choose";
    }
}