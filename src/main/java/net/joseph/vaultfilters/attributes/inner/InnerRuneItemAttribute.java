package net.joseph.vaultfilters.attributes.inner;

import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.InnerFilterAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InnerRuneItemAttribute extends InnerFilterAttribute {
    public InnerRuneItemAttribute(ItemStack value) {
        super(value);
    }

    @Override
    public List<ItemStack> getInnerItems(ItemStack stack) {
        if (stack.getItem() instanceof BossRuneItem) {
            return BossRuneItem.getItems(stack);
        }
        return null;
    }
    @Override
    public String getTranslationKey() {
        return "inner_boss_rune";
    }
}
