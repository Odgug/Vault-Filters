package net.joseph.vaultfilters.attributes.inner;

import iskallia.vault.item.JewelPouchItem;
import net.joseph.vaultfilters.attributes.abstracts.InnerFilterAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InnerJewelPouchAttribute extends InnerFilterAttribute {
    public InnerJewelPouchAttribute(ItemStack value) {
        super(value);
    }

    @Override
    public List<ItemStack> getInnerItems(ItemStack stack) {
        if (!(stack.getItem() instanceof JewelPouchItem )) {
            return null;
        }
        List<JewelPouchItem.RolledJewel> jewelPouch= JewelPouchItem.getJewels(stack);
        List<ItemStack> jewelOptions = new ArrayList<>();
        for (JewelPouchItem.RolledJewel rolledJewel : jewelPouch) {
            if (rolledJewel.identified()) {
                jewelOptions.add(rolledJewel.stack());
            }
        }
        return jewelOptions;
    }

    @Override
    public String getNBTKey() {
        return "inner_jewel_pouch";
    }
}
