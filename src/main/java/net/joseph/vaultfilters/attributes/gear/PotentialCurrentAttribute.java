package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.gear.tooltip.VaultGearTooltipItem;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class PotentialCurrentAttribute extends IntAttribute {
    public PotentialCurrentAttribute(Integer value) {
        super(value);
    }
    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
            VaultGearData data = VaultGearData.read(itemStack);
            if (!data.hasAttribute(ModGearAttributes.CRAFTING_POTENTIAL)) {
                return null;
            }
            return (data.getFirstValue(ModGearAttributes.CRAFTING_POTENTIAL).orElse(null));
        }
        return null;
    }

    @Override
    public String getNBTKey() {
        return "current_potential";
    }
}
