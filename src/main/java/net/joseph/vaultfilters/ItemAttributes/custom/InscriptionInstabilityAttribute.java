package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.mixin.InscriptionDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InscriptionInstabilityAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new InscriptionInstabilityAttribute("0"));
    }
    String instab;

    public static double getInstability(ItemStack itemStack) {
        InscriptionData data = InscriptionData.from(itemStack);
        float val = ((InscriptionDataAccessor) data).getInstability() * 1000.0F;
        return ((double) Math.round(val)) /10;
    }
    public InscriptionInstabilityAttribute(String instab) {
        this.instab = instab;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof InscriptionItem) {
            return (getInstability(itemStack) <= Double.valueOf(instab));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof InscriptionItem) {
           atts.add(new InscriptionInstabilityAttribute(String.valueOf(getInstability(itemStack))));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "inscription_instability";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{String.valueOf(instab)+"%"};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("instab", this.instab);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new InscriptionInstabilityAttribute(nbt.getString("instab"));
    }
}