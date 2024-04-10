package net.joseph.vaultfilters.attributes.inscription;

import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.joseph.vaultfilters.mixin.InscriptionDataAccessor;
import net.minecraft.world.item.ItemStack;

public class InscriptionTimeAttribute extends IntAttribute {
    public InscriptionTimeAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof InscriptionItem) {
            InscriptionData data = InscriptionData.from(itemStack);
            return ((InscriptionDataAccessor) data).getTime() / 20;
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "inscription_time";
    }

    @Override
    public String getSubNBTKey() {
        return "time";
    }
}