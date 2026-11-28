package net.joseph.vaultfilters.attributes.old.rune;

import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.OldAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BossRuneGivesItemAttribute extends OldAttribute {
    public BossRuneGivesItemAttribute(Object value) {
        super(value);
    }
/*
    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) {
            return null;
        }
        List<ItemStack> list = BossRuneItem.getItems(itemStack);
        if (list.isEmpty()) return null;
        ItemStack reward = list.get(0);
        return reward.getItem().getDescriptionId();
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[] { new TranslatableComponent(this.value).getString() };
    }

 */

    @Override
    public String getNBTKey() {
        return "boss_rune_gives_item";
    }
}
