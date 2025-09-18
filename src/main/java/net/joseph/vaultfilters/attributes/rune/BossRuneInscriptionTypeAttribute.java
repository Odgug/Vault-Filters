package net.joseph.vaultfilters.attributes.rune;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.item.BossRuneItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BossRuneInscriptionTypeAttribute extends StringAttribute {
    public BossRuneInscriptionTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) {
            return null;
        }
        List<ItemStack> list = BossRuneItem.getItems(itemStack);
        if (list.isEmpty()) return null;
        ItemStack reward = list.get(0);
        if (reward.getItem() instanceof InscriptionItem) {
            InscriptionData data = InscriptionData.from(reward);
            if (data.getEntries().isEmpty()) {
                return null;
            }
            return data.getEntries().get(0).toRoomEntry().getName().getString();
        }
        return null;
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.value};
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_inscription_type";
    }
}