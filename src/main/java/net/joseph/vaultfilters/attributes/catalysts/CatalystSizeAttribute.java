package net.joseph.vaultfilters.attributes.catalysts;

import iskallia.vault.item.InfusedCatalystItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CatalystSizeAttribute extends IntAttribute {
    public CatalystSizeAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof InscriptionItem) {

            return (Integer) InfusedCatalystItem.getSize(itemStack).orElse(null);
        }
        return null;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Integer value = getValue(itemStack);
        return value != null && value <= this.value;
    }

    @Override
    public String getTranslationKey() {
        return "catalyst_size";
    }
}