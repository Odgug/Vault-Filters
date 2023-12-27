package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NumberPrefixAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new NumberPrefixAttribute("dummy"));
    }
    String prefixname;

    public NumberPrefixAttribute(String prefixname) {
        this.prefixname = prefixname;
    }
    public Optional<MutableComponent> getDisplay2(VaultGearModifier modifier, VaultGearData data, VaultGearModifier.AffixType type, ItemStack stack) {
        return Optional.ofNullable(modifier.getAttribute().getReader().getDisplay(modifier, data, type, stack));
    }
    public Optional<MutableComponent> getDisplay(VaultGearModifier modifier, VaultGearData data, VaultGearModifier.AffixType type, ItemStack stack) {


        return getDisplay2(modifier, data, type, stack).map(VaultGearModifier.AffixCategory.NONE.getModifierFormatter()).map((displayText) -> {
            if (!modifier.hasGameTimeAdded()) {
                return displayText;
            } else {


                return displayText;
            }
        });
    }
    public  String getPrefixDisplay(int index, ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        VaultGearModifier modifier = data.getModifiers(VaultGearModifier.AffixType.PREFIX).get(index);
        return (getDisplay(modifier, data, VaultGearModifier.AffixType.PREFIX, itemStack).get().getString());
    }
    public int getPrefixCount(ItemStack itemStack) {
        return VaultGearData.read(itemStack).getModifiers(VaultGearModifier.AffixType.PREFIX).size();
    }
    public static boolean isNumber(String num) {
        if (num == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static double getModifierValue(String modifier) {
        boolean flag = false;
        int flagint = 0;
        for (int i = 0; i < modifier.length(); i++) {
            if (isNumber(String.valueOf(modifier.charAt(i)))) {
                flag = true;
                flagint = i;
                i = 100000;
            }
        }

        if (!flag) {
            return 0;
        }
        String tempnum = String.valueOf(modifier.charAt(flagint));
        for (int i = flagint+1; i < modifier.length(); i++) {
            if (isNumber(String.valueOf(modifier.charAt(i))) || String.valueOf(modifier.charAt(i)).equals(".")) {
                tempnum = tempnum + (String.valueOf(modifier.charAt(i)));
            } else {
                i = 100000;
            }
        }
        return Double.parseDouble(tempnum);

    }

    public static String getName(String modifier) {
        int flagint = 0;
        for (int i = 0; i < modifier.length(); i++) {
            if (Character.isAlphabetic(modifier.charAt(i))) {
                flagint = i;
                i = 1000;
            }
        }
        String name = "";
        for (int i = flagint; i <modifier.length(); i++) {
            name = name + String.valueOf(modifier.charAt(i));
        }
        return name;
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem) {
            for (int i = 0; i < getPrefixCount(itemStack); i++) {
                if (getName(getPrefixDisplay(i,itemStack)).equals(getName(prefixname))) {
                    if (getModifierValue(getPrefixDisplay(i,itemStack)) >= getModifierValue(prefixname)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem) {
           for (int i = 0; i < getPrefixCount(itemStack); i++) {
               if (getModifierValue(getPrefixDisplay(i,itemStack)) != 0) {
                   atts.add(new NumberPrefixAttribute(getPrefixDisplay(i,itemStack)));
               }
           }
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "prefix_number";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{prefixname};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("prefixNumber", this.prefixname);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new NumberPrefixAttribute(nbt.getString("prefixNumber"));
    }
}