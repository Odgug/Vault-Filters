package net.joseph.vaultfilters.attributes.jewel;

import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class JewelRarityAttribute extends StringAttribute {
    public JewelRarityAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof JewelItem)) {
            return null;
        }

        VaultGearRarity rarity = VaultGearData.read(itemStack).getRarity();
        return switch (rarity) {
            case COMMON -> "Chipped";
            case RARE -> "Flawed";
            case EPIC -> "Flawless";
            case OMEGA -> "Perfect";
            default -> StringUtils.capitalize(rarity.toString().toLowerCase(Locale.ROOT));
        };
    }

    @Override
    public String getTranslationKey() {
        return "jewel_rarity";
    }

    @Override
    public String getLegacyKey() {
        return "jewelRarity";
    }
}