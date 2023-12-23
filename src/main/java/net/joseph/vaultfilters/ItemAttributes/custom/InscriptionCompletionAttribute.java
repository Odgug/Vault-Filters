package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.mixin.InscriptionDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InscriptionCompletionAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new InscriptionCompletionAttribute("0"));
    }
    String comp;

    public static int getCompletion(ItemStack itemStack) {
        InscriptionData data = InscriptionData.from(itemStack);
        return Math.round( ((InscriptionDataAccessor) data).getCompletion() * 100.0F);
    }
    public InscriptionCompletionAttribute(String comp) {
        this.comp = comp;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof InscriptionItem) {
            return (getCompletion(itemStack) >= Integer.valueOf(comp));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof InscriptionItem) {
           atts.add(new InscriptionCompletionAttribute(String.valueOf(getCompletion(itemStack))));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "inscription_completion";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{String.valueOf(comp)};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("comp", this.comp);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new InscriptionCompletionAttribute(nbt.getString("comp"));
    }
}