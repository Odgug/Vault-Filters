package net.joseph.vaultfilters.attributes.rune;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
    public Object[] getTranslationParameters() {
        // Convert the stored item ID value (e.g. "the_vault:helmet") to its display name for tooltips
        String displayName = this.value;
        try {
            ResourceLocation id = new ResourceLocation(this.value);
            Item item = ForgeRegistries.ITEMS.getValue(id);
            if (item != null && item.getDescription() != null) {
                displayName = item.getDescription().getString();
            }
        } catch (Exception ignored) {
            // fallback to ID string if not found or invalid
        }
        return new Object[]{displayName};
    }
    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        return new ArrayList<>();
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_gives_item";
    }
}
