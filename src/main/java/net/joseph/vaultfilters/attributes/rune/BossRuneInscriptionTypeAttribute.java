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
        // Example: "the_vault:vault/rooms/challenge/laboratory"
        String[] split = pool.split("/");
        if (split.length < 4) return null;
        String name = split[split.length - 1]; // e.g. "laboratory"
        // Capitalize first letter
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    @Override
    public Object[] getTranslationParameters() {
        // Show user-friendly name, e.g. "Laboratory"
        return new Object[]{this.value};
    }

    @Override
    public String getTranslationKey() {
        return "inscription_type";
    }
}