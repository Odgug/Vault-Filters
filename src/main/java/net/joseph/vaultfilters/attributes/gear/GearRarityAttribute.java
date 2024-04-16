package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Optional;

public class GearRarityAttribute extends StringAttribute {
    public GearRarityAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof JewelItem || !(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        if (Boolean.TRUE.equals(IsUnidentifiedAttribute.isUnidentified(itemStack))) {
            Optional<String> roll = AttributeGearData.read(itemStack).getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE);
            if (roll.isEmpty()) {
                return null;
            }
            String rollType = roll.get();
            return rollType.substring(0, rollType.length() - 1);
        }

        VaultGearData data = VaultGearData.read(itemStack);
        return StringUtils.capitalize(data.getRarity().toString().toLowerCase(Locale.ROOT));
    }

    @Override
    public String getTranslationKey() {
        return "gear_rarity";
    }

    @Override
    public String getLegacyKey() {
        return "gearRarity";
    }
}