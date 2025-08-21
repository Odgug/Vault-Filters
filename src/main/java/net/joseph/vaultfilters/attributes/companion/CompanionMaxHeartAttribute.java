package net.joseph.vaultfilters.attributes.companion;

import iskallia.vault.item.CompanionItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CompanionMaxHeartAttribute extends IntAttribute {
    public CompanionMaxHeartAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionItem)) {
            return null;
        }


        return CompanionItem.getCompanionMaxHearts(itemStack);
    }

    @Override
    public String getTranslationKey() {
        return "companion_max_hearts";
    }

    public boolean appliesTo(ItemStack itemStack) {
        Integer value = this.getValue(itemStack);
        return value != null && value <= (Integer)this.value;
    }
}
