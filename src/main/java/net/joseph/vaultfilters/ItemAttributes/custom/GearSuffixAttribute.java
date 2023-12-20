package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GearSuffixAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new GearSuffixAttribute("dummy"));
    }
    String suffixname;

    public GearSuffixAttribute(String suffixname) {
        this.suffixname = suffixname;
    }

    public boolean hassuffix(ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        List<VaultGearModifier<?>> suffixes = data.getModifiers(VaultGearModifier.AffixType.SUFFIX);

        for (int i = 0; i < suffixes.size(); i++) {
            if (suffixes.get(i).getAttribute().getReader().getModifierName().equals(this.suffixname)) {
                return true;
            }

        }
        if (this.suffixname.equals("Empty Slot")) {
            return hasEmptysuffix(itemStack);
        }
        return false;
    }

    public boolean hasEmptysuffix(ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        List<VaultGearModifier<?>> suffixes = data.getModifiers(VaultGearModifier.AffixType.SUFFIX);
        if (suffixes.size() < getsuffixCount(itemStack)) {
            return true;
        }
        return false;
    }
    public int getsuffixCount(ItemStack itemStack) {
        VaultGearData data =VaultGearData.read(itemStack);
        return (Integer)data.getFirstValue(ModGearAttributes.SUFFIXES).orElse(0);
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem) {
            return (hassuffix(itemStack));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem) {
           VaultGearData data = VaultGearData.read(itemStack);
           List<VaultGearModifier<?>> suffixes = data.getModifiers(VaultGearModifier.AffixType.SUFFIX);

           for (int i = 0; i < suffixes.size(); i++) {
               atts.add(new GearSuffixAttribute(suffixes.get(i).getAttribute().getReader().getModifierName()));
           }
           if (hasEmptysuffix(itemStack)) {
               atts.add(new GearSuffixAttribute("Empty Slot"));
           }
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "suffix";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{suffixname};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("suffix", this.suffixname);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new GearSuffixAttribute(nbt.getString("suffix"));
    }
}