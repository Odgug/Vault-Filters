package net.joseph.vaultfilters.attributes.companion;

import iskallia.vault.item.CompanionItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CompanionHeartAttribute extends IntAttribute {
    public CompanionHeartAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionItem)) {
            return null;
        }


        return CompanionItem.getCompanionHearts(itemStack);
    }

    @Override
    public String getTranslationKey() {
        return "companion_hearts";
    }

    public boolean appliesTo(ItemStack itemStack) {
        Integer value = this.getValue(itemStack);
        return value != null && value <= (Integer)this.value;
    }
}
