package net.joseph.vaultfilters.attributes.inscription;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class InscriptionSizeAttribute extends IntAttribute {
    public InscriptionSizeAttribute(Integer value) {
        super(value);
    }
    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_MOST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof InscriptionItem) {
             return InscriptionData.from(itemStack).getSize();
        }
        return null;
    }



    @Override
    public String getTranslationKey() {
        return "inscription_size";
    }
}
