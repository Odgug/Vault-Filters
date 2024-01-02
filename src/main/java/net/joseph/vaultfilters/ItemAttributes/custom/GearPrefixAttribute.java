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

import static net.joseph.vaultfilters.ItemAttributes.custom.NumberPrefixAttribute.getModifierValue;
import static net.joseph.vaultfilters.ItemAttributes.custom.NumberPrefixAttribute.getName;

public class GearPrefixAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new GearPrefixAttribute("dummy"));
    }
    String prefixname;

    public GearPrefixAttribute(String prefixname) {
        this.prefixname = prefixname;
    }

    public boolean hasPrefix(ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        List<VaultGearModifier<?>> prefixes = data.getModifiers(VaultGearModifier.AffixType.PREFIX);

        for (int i = 0; i < prefixes.size(); i++) {
            String name = prefixes.get(i).getAttribute().getReader().getModifierName();
            if (name.equals("")) {
                if (getName(getPrefixDisplay(i, itemStack)).equals(this.prefixname)) {
                    return true;
                }
            }
            if (name.equals(this.prefixname)) {
                return true;
            }


        }
        if (this.prefixname.equals("Empty Slot")) {
            return hasEmptyPrefix(itemStack);
        }
        return false;
    }

    public boolean hasEmptyPrefix(ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        List<VaultGearModifier<?>> prefixes = data.getModifiers(VaultGearModifier.AffixType.PREFIX);
        if (prefixes.size() < getPrefixCount(itemStack)) {
            return true;
        }
        return false;
    }
    public int getPrefixCount(ItemStack itemStack) {
        VaultGearData data =VaultGearData.read(itemStack);
        return (Integer)data.getFirstValue(ModGearAttributes.PREFIXES).orElse(0);
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem) {
            return (hasPrefix(itemStack));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem) {
           VaultGearData data = VaultGearData.read(itemStack);
           List<VaultGearModifier<?>> prefixes = data.getModifiers(VaultGearModifier.AffixType.PREFIX);

           for (int i = 0; i < prefixes.size(); i++) {
               if (!prefixes.get(i).getAttribute().getReader().getModifierName().equals("")) {
                   atts.add(new GearPrefixAttribute(prefixes.get(i).getAttribute().getReader().getModifierName()));
               }
               else {
                   atts.add(new GearPrefixAttribute(getName(getPrefixDisplay(i,itemStack))));
               }
           }
           if (hasEmptyPrefix(itemStack)) {
               atts.add(new GearPrefixAttribute("Empty Slot"));
           }
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "prefix";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{prefixname};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("prefix", this.prefixname);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new GearPrefixAttribute(nbt.getString("prefix"));
    }
    public  String getPrefixDisplay(int index, ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        VaultGearModifier modifier = data.getModifiers(VaultGearModifier.AffixType.PREFIX).get(index);
        return (getDisplay(modifier, data, VaultGearModifier.AffixType.PREFIX, itemStack).get().getString());
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
}