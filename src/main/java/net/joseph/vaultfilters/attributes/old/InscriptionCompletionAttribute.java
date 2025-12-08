package net.joseph.vaultfilters.attributes.old;

import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.joseph.vaultfilters.attributes.abstracts.OldAttribute;
import net.minecraft.world.item.ItemStack;

public class InscriptionCompletionAttribute extends OldAttribute {
    public InscriptionCompletionAttribute(Object value) {
        super(value);
    }
    //used to be intAttribute
//    public InscriptionCompletionAttribute(Integer value) {
//        super(value);
//    }

//    @Override
//    public Integer getValue(ItemStack itemStack) {
//        if (itemStack.getItem() instanceof InscriptionItem) {
//            //InscriptionData data = InscriptionData.from(itemStack);
//            //return Math.round( ((InscriptionDataAccessor) data).getCompletion() * 100.0F);
//        }
//        return null;
//    }

    @Override
    public String getNBTKey() {
        return "inscription_completion";
    }

    @Override
    public String getLegacyKey() {
        return "comp";
    }

//    @Override
//    public Object[] getTranslationParameters() {
//        return new Object[]{this.value+"%"};
//    }
}