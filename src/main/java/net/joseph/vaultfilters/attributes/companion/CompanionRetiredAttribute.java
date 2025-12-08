package net.joseph.vaultfilters.attributes.companion;

import iskallia.vault.item.CompanionItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class CompanionRetiredAttribute extends BooleanAttribute {
    public CompanionRetiredAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionItem)) {
            return false;
        }
        int hearts = CompanionItem.getCompanionHearts(itemStack);
        return hearts <= 0;
    }

    @Override
    public String getNBTKey() {
        return "companion_retired";
    }
}
