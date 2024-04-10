package net.joseph.vaultfilters.attributes.other;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class GearRepairSlotAttribute extends IntAttribute {
    public GearRepairSlotAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
            VaultGearData data = VaultGearData.read(itemStack);
            return data.getRepairSlots() - data.getUsedRepairSlots();
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "repair";
    }
}