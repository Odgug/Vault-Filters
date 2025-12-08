package net.joseph.vaultfilters.attributes.old;

import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.joseph.vaultfilters.attributes.abstracts.OldAttribute;
import net.minecraft.world.item.ItemStack;

public class InscriptionTimeAttribute extends OldAttribute {
    public InscriptionTimeAttribute(Object value) {
        super(value);
    }
    //used to be IntAttribute
//    public InscriptionTimeAttribute(Integer value) {
//        super(value);
//    }

    //@Override
//    public Integer getValue(ItemStack itemStack) {
//        if (itemStack.getItem() instanceof InscriptionItem) {
//            InscriptionData data = InscriptionData.from(itemStack);
//            return ((InscriptionDataAccessor) data).getTime() / 20;
//        }
//        return null;
//    }

    @Override
    public String getNBTKey() {
        return "inscription_time";
    }

    @Override
    public String getLegacyKey() {
        return "time";
    }
}