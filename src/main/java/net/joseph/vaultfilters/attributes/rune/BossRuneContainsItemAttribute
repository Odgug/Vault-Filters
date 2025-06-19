package net.joseph.vaultfilters.attributes.rune;

import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BossRuneContainsItemAttribute extends StringAttribute {
    public BossRuneContainsItemAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) {
            return null;
        }
        List<ItemStack> crateItems = BossRuneItem.getItems(itemStack);
        for (ItemStack stack : crateItems) {
            if (stack.getItem().getRegistryName() != null &&
                stack.getItem().getRegistryName().toString().equals(value)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_contains_item";
    }
}
