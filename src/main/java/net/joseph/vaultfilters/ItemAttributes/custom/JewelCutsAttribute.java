package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JewelCutsAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new JewelCutsAttribute("0"));
    }
    String cuts;

    public JewelCutsAttribute(String cuts) {
        this.cuts = cuts;
    }

    public static int getcutSlots(ItemStack itemStack) {
        if (!(itemStack.getTag().contains("freeCuts"))) {
            return 0;
        }
        return itemStack.getTag().getInt("freeCuts");
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof JewelItem) {
            return (Integer.valueOf(cuts) == getcutSlots(itemStack));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof JewelItem) {
           atts.add(new JewelCutsAttribute(String.valueOf(getcutSlots(itemStack))));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "cut_count";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{String.valueOf(cuts)};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("cuts", this.cuts);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new JewelCutsAttribute(nbt.getString("cuts"));
    }
}