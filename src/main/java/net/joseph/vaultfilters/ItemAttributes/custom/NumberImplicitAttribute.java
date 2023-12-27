package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NumberImplicitAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new NumberImplicitAttribute("dummy"));
    }
    String implicitname;

    public NumberImplicitAttribute(String implicitname) {
        this.implicitname = implicitname;
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
    public String getImplicitDisplay(int index, ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        VaultGearModifier modifier = data.getModifiers(VaultGearModifier.AffixType.IMPLICIT).get(index);
        return (getDisplay(modifier, data, VaultGearModifier.AffixType.IMPLICIT, itemStack).get().getString());
    }
    public int getImplicitCount(ItemStack itemStack) {
        return VaultGearData.read(itemStack).getModifiers(VaultGearModifier.AffixType.IMPLICIT).size();
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
        return Integer.parseInt(tempnum);

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

        if (itemStack.getItem() instanceof VaultGearItem  && !(itemStack.getItem() instanceof JewelItem)) {
            for (int i = 0; i < getImplicitCount(itemStack); i++) {
                if (getName(getImplicitDisplay(i,itemStack)).equals(getName(implicitname))) {
                    if (getModifierValue(getImplicitDisplay(i,itemStack)) >= getModifierValue(implicitname)) {
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
       if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
           for (int i = 0; i < getImplicitCount(itemStack); i++) {
               if (getModifierValue(getImplicitDisplay(i,itemStack)) != 0) {
                   atts.add(new NumberImplicitAttribute(getImplicitDisplay(i,itemStack)));
               }
           }
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "implicit_number";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{implicitname};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("implicitNumber", this.implicitname);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new NumberImplicitAttribute(nbt.getString("implicitNumber"));
    }
}