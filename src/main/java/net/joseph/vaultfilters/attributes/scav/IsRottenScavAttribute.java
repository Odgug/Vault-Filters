package net.joseph.vaultfilters.attributes.scav;

import iskallia.vault.item.BasicScavengerItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class IsRottenScavAttribute extends BooleanAttribute {
    public IsRottenScavAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BasicScavengerItem) {
            if (itemStack.hasTag() && itemStack.getOrCreateTag().contains("rotten")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNBTKey() {
        return "rotten_scav";
    }
}
