package net.joseph.vaultfilters.attributes.inner;

import iskallia.vault.item.BoosterPackItem;
import net.joseph.vaultfilters.attributes.abstracts.InnerFilterAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InnerCardPackAttribute extends InnerFilterAttribute {
    public InnerCardPackAttribute(ItemStack value) {
        super(value);
    }

    @Override
    public List<ItemStack> getInnerItems(ItemStack stack) {
        if (stack.getItem() instanceof BoosterPackItem) {
            return BoosterPackItem.getOutcomes(stack);
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "inner_card_pack";
    }
}
