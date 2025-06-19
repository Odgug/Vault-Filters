package net.joseph.vaultfilters.attributes.rune;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.BossRuneItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
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

        // Only check if it's vault gear
        String itemId = gearTag.getString("id");
        if (!itemId.startsWith("the_vault:")) return null;

        // Try to get rarity from sub-NBT
        if (!gearTag.contains("tag", 10)) return null;
        CompoundTag innerTag = gearTag.getCompound("tag");
        if (!innerTag.contains("vaultGearData")) return null;

        ItemStack gearStack = ItemStack.of(gearTag);
        if (!(gearStack.getItem() instanceof VaultGearItem)) return null;
        VaultGearData data = VaultGearData.read(gearStack);
        return StringUtils.capitalize(data.getRarity().toString().toLowerCase(Locale.ROOT));
    }

    @Override
    public Object[] getTranslationParameters() {
        // Only show the rarity, e.g. "Omega"
        return new Object[]{this.value};
    }

    @Override
    public String getTranslationKey() {
        return "gear_rarity";
    }
}