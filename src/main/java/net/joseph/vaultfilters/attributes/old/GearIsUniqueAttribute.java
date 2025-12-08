package net.joseph.vaultfilters.attributes.old;

import net.joseph.vaultfilters.attributes.abstracts.OldAttribute;
import net.minecraft.world.item.ItemStack;

public class GearIsUniqueAttribute extends OldAttribute {
    public GearIsUniqueAttribute(Object value) {
        super(value);
    }

    @Override
    public Object getValue(ItemStack itemStack) {
        return false;
    }

    @Override
    public String getNBTKey() {
        return "gear_unique";
    }

}