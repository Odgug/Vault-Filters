package net.joseph.vaultfilters.attributes.pouch;

import iskallia.vault.item.BoosterPackItem;
import iskallia.vault.item.JewelPouchItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class JewelPouchOpenedAttribute extends BooleanAttribute {
    public JewelPouchOpenedAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof JewelPouchItem)) {
            return false;
        }
        return !JewelPouchItem.getJewels(itemStack).isEmpty();
    }

    @Override
    public String getNBTKey() {
        return "jewel_pouch_opened";
    }
}