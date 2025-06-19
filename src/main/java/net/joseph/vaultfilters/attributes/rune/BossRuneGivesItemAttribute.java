package net.joseph.vaultfilters.attributes.rune;

import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

public class BossRuneGivesItemAttribute extends StringAttribute {
    public BossRuneGivesItemAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) {
            return null;
        }
        if (itemStack.hasTag() && itemStack.getTag().contains("Items", 9)) { // 9 = ListTag
            ListTag items = itemStack.getTag().getList("Items", 10); // 10 = CompoundTag
            if (!items.isEmpty()) {
                CompoundTag givenItem = items.getCompound(0);
                if (givenItem.contains("id")) {
                    return givenItem.getString("id");
                }
            }
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_gives_item";
    }
}
