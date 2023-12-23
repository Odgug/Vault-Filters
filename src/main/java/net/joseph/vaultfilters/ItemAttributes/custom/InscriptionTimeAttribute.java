package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.mixin.InscriptionDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InscriptionTimeAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new InscriptionTimeAttribute("0"));
    }
    String time;

    public static int getTime(ItemStack itemStack) {
        InscriptionData data = InscriptionData.from(itemStack);
        return ((InscriptionDataAccessor) data).getTime() / 20;

    }
    public InscriptionTimeAttribute(String time) {
        this.time = time;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof InscriptionItem) {
            return (getTime(itemStack) >= Integer.valueOf(time));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof InscriptionItem) {
           atts.add(new InscriptionTimeAttribute(String.valueOf(getTime(itemStack))));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "inscription_time";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{String.valueOf(time)};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("time", this.time);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new InscriptionTimeAttribute(nbt.getString("time"));
    }
}