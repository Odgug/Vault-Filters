package net.joseph.vaultfilters.attributes.old.rune;

import iskallia.vault.item.FuryRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.InnerFilterAttribute;
import net.joseph.vaultfilters.attributes.abstracts.OldAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class InnerRuneItemAttribute extends OldAttribute {
    public InnerRuneItemAttribute(Object value) {
        super(value);
    }
/*
    @Override
    public List<ItemStack> getInnerItems(ItemStack stack) {
        if (stack.getItem() instanceof FuryRuneItem) {
            return FuryRuneItem.getSealedItem(stack);
        }
        return null;
    }

 */
    @Override
    public String getNBTKey() {
        return "inner_boss_rune";
    }
}
