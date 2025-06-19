package net.joseph.vaultfilters.attributes.rune;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

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
        if (!gearTag.contains("id")) return null;

        String itemId = gearTag.getString("id");
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
        if (item == null) return null;
        int count = gearTag.contains("Count") ? gearTag.getInt("Count") : 1;
        ItemStack gearStack = new ItemStack(item, count);
        if (gearTag.contains("tag", 10)) {
            gearStack.setTag(gearTag.getCompound("tag"));
        }
        if (!(gearStack.getItem() instanceof VaultGearItem)) return null;
        VaultGearData data = VaultGearData.read(gearStack);
        return StringUtils.capitalize(data.getRarity().toString().toLowerCase(Locale.ROOT));
    }

    @Override
    public Object[] getTranslationParameters() {
        // Only show the rarity
        return new Object[]{this.value};
    }

    @Override
    public String getTranslationKey() {
        return "boss_rune_gear_rarity";
    }
}