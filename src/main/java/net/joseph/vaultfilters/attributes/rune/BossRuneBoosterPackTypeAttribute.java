package net.joseph.vaultfilters.attributes.rune;

import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class BossRuneBoosterPackTypeAttribute extends StringAttribute {
    public BossRuneBoosterPackTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BossRuneItem)) return null;
        if (!itemStack.hasTag() || !itemStack.getTag().contains("Items", 9)) return null;
        ListTag items = itemStack.getTag().getList("Items", 10);
        if (items.isEmpty()) return null;
        CompoundTag packTag = items.getCompound(0);
        if (!"the_vault:booster_pack".equals(packTag.getString("id"))) return null;
        if (!packTag.contains("tag", 10)) return null;
        CompoundTag innerTag = packTag.getCompound("tag");
        if (!innerTag.contains("id")) return null;
        String boosterId = innerTag.getString("id");
        return boosterId;
    }

    @Override
    public Object[] getTranslationParameters() {
        // Display the pack type's item name if possible
        String displayName = this.value;
        try {
            ResourceLocation id = new ResourceLocation(this.value);
            Item item = ForgeRegistries.ITEMS.getValue(id);
            if (item != null && item.getDescription() != null) {
                displayName = item.getDescription().getString();
            }
        } catch (Exception ignored) {}
        return new Object[]{displayName};
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_booster_pack_type";
    }
}