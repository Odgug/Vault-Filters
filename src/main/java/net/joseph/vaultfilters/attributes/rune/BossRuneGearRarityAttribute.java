package net.joseph.vaultfilters.attributes.rune;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.BossRuneItem;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.attributes.gear.IsUnidentifiedAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class BossRuneGearRarityAttribute extends StringAttribute {
    public BossRuneGearRarityAttribute(String value) {
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
        if (reward.getItem() instanceof JewelItem || !(reward.getItem() instanceof VaultGearItem)) {
            return null;
        }

        if (Boolean.TRUE.equals(IsUnidentifiedAttribute.isUnidentified(reward))) {
            Optional<String> roll = AttributeGearData.read(reward).getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE);
            if (roll.isEmpty()) {
                return null;
            }
            String rollType = roll.get();
            int rollLength = rollType.length();
            return rollType.charAt(rollLength-1) == '+' ? rollType.substring(0,rollLength-1) : rollType;
        }

        VaultGearData data = VaultGearData.read(reward);
        return StringUtils.capitalize(data.getRarity().toString().toLowerCase(Locale.ROOT));
    }

    @Override
    public String getNBTKey() {
        return "boss_rune_gear_rarity";
    }
}