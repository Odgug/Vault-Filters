package net.joseph.vaultfilters.attributes.jewel;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class JewelRarityAttribute extends StringAttribute {
    public JewelRarityAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof JewelItem)) {
            return null;
        }

        return switch (VaultGearData.read(itemStack).getRarity()) {
            case COMMON -> "Chipped";
            case RARE -> "Flawed";
            case EPIC -> "Flawless";
            case OMEGA -> "Perfect";
            default -> null;
        };
    }

    @Override
    public String getTranslationKey() {
        return "jewel_rarity";
    }

    @Override
    public String getSubNBTKey() {
        return "jewelRarity";
    }
}