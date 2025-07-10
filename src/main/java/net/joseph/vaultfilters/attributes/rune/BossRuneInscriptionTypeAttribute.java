package net.joseph.vaultfilters.attributes.rune;

import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

public class BossRuneInscriptionTypeAttribute extends StringAttribute {
    public BossRuneInscriptionTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) return null;
        if (!itemStack.hasTag() || !itemStack.getTag().contains("Items", 9)) return null;
        ListTag items = itemStack.getTag().getList("Items", 10);
        if (items.isEmpty()) return null;
        CompoundTag insTag = items.getCompound(0);
        if (!"the_vault:inscription".equals(insTag.getString("id"))) return null;
        if (!insTag.contains("tag", 10)) return null;
        CompoundTag innerTag = insTag.getCompound("tag");
        if (!innerTag.contains("data", 10)) return null;
        CompoundTag data = innerTag.getCompound("data");
        if (!data.contains("entries", 9)) return null;
        ListTag entries = data.getList("entries", 10);
        if (entries.isEmpty()) return null;
        CompoundTag entry = entries.getCompound(0);
        if (!entry.contains("pool")) return null;
        String pool = entry.getString("pool");

        // Edge case: Raid
        if (pool.contains("/raid/")) {
            return "Raid";
        }
        String[] split = pool.split("/");
        // Find the last non-generic segment
        for (int i = split.length - 1; i >= 0; i--) {
            String s = split[i];
            if (!s.equals("rooms") && !s.equals("challenge") && !s.equals("vault")) {
                return Character.toUpperCase(s.charAt(0)) + s.substring(1);
            }
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