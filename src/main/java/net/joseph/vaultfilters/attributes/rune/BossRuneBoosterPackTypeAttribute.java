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
        if (!(itemStack.getItem() instanceof BossRuneItem)) {
            return null;
        }
        List<ItemStack> list = BossRuneItem.getItems(itemStack);
        if (list.isEmpty()) return null;
        ItemStack reward = list.get(0);
        if (!(reward.getItem() instanceof BoosterPackItem)) {
            return null;
        }
        return ModConfigs.BOOSTER_PACK.getName(BoosterPackItem.getId(reward)).map(Component::getString).orElse(null);
    }

    @Override
    public String getNBTKey() {
        return "boss_rune_booster_pack_type";
    }
}
