package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class GearLevelAttribute extends IntAttribute {
    public GearLevelAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            return VaultGearData.read(itemStack).getItemLevel();
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "level";
    }
}