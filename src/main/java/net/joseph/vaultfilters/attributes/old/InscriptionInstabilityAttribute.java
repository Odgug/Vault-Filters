package net.joseph.vaultfilters.attributes.old;

import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.DoubleAttribute;
import net.joseph.vaultfilters.attributes.abstracts.OldAttribute;
import net.minecraft.world.item.ItemStack;

public class InscriptionInstabilityAttribute extends OldAttribute {
    public InscriptionInstabilityAttribute(Object value) {
        super(value);
    }
    //used to be DoubleAttribute
//    public InscriptionInstabilityAttribute(Double value) {
//        super(value);
//    }
    
//    @Override
//    public Double getValue(ItemStack itemStack) {
//        if (itemStack.getItem() instanceof InscriptionItem) {
//            InscriptionData data = InscriptionData.from(itemStack);
//            float val = ((InscriptionDataAccessor) data).getInstability() * 1000.0F;
//            return ((double) Math.round(val)) /10;
//        }
//        return null;
//    }

//    @Override
//    public boolean appliesTo(ItemStack itemStack) {
//        final Double value = getValue(itemStack);
//        return value != null && value <= this.value;
//    }
    
    @Override
    public String getTranslationKey() {
        return "inscription_instability";
    }

    @Override
    public String getLegacyKey() {
        return "instab";
    }

//    @Override
//    public Object[] getTranslationParameters() {
//        return new Object[]{this.value+"%"};
//    }
}