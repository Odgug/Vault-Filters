package net.joseph.vaultfilters.attributes.jewel;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class JewelSizeAttribute extends IntAttribute {
    public JewelSizeAttribute(Integer value) {
        super(value);
    }
    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_MOST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof JewelItem) {
            VaultGearData data = VaultGearData.read(itemStack);
            return data.getFirstValue(ModGearAttributes.JEWEL_SIZE).orElse(null);
        }
        return null;
    }



    @Override
    public boolean nestedLegacyKey() {
        return true;
    }
    @Override
    public String getLegacyKey() {
        return "size";
    }

    @Override
    public String getTranslationKey() {
        return "jewel_size";
    }
}