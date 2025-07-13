package net.joseph.vaultfilters.attributes.rune;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BossRuneGearRarityAttribute extends StringAttribute {
    public BossRuneGearRarityAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) return null;
        if (!itemStack.hasTag() || !itemStack.getTag().contains("Items", 9)) return null;
        ListTag items = itemStack.getTag().getList("Items", 10);
        if (items.isEmpty()) return null;
        CompoundTag gearTag = items.getCompound(0);
        if (!gearTag.contains("tag", 10)) return null;
        CompoundTag innerTag = gearTag.getCompound("tag");
        if (!innerTag.contains("clientCache", 10)) return null;
        CompoundTag clientCache = innerTag.getCompound("clientCache");
        if (!clientCache.contains("rollType")) return null;
        return clientCache.getString("rollType");
    }
    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        return new ArrayList<>();
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_gear_rarity";
    }
}