package net.joseph.vaultfilters.attributes.rune;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.BoosterPackItem;
import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

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

        // Synthesize a real booster pack ItemStack from the tag!
        Item boosterPackItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("the_vault:booster_pack"));
        if (boosterPackItem == null) return null;
        ItemStack fakePack = new ItemStack(boosterPackItem);
        if (packTag.contains("tag", 10))
            fakePack.setTag(packTag.getCompound("tag"));

        // Use the same logic as CardPackTypeAttribute
        return ModConfigs.BOOSTER_PACK
            .getName(BoosterPackItem.getId(fakePack))
            .map(Component::getString)
            .orElse(null);
    }
    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        return new ArrayList<>();
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_booster_pack_type";
    }
}
