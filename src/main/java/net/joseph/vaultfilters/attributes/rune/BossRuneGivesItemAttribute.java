package net.joseph.vaultfilters.attributes.rune;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BossRuneGivesItemAttribute extends StringAttribute {
    public BossRuneGivesItemAttribute(String value) {
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
        return reward.getItem().getDescriptionId();
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[] { new TranslatableComponent(this.value).getString() };
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_gives_item";
    }
}
