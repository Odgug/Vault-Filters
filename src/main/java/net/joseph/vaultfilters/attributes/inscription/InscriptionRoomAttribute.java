package net.joseph.vaultfilters.attributes.inscription;

import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class InscriptionRoomAttribute extends StringAttribute {
    public InscriptionRoomAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof InscriptionItem) {
            InscriptionData data = InscriptionData.from(itemStack);
            if (data.getEntries().isEmpty()) {
                return null;
            }
            return data.getEntries().get(0).toRoomEntry().getName().getString();
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "inscription_room";
    }

    @Override
    public String getSubNBTKey() {
        return "room";
    }
}