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

public class GearImplicitAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new GearImplicitAttribute("dummy"));
    }
    String implicitname;

    public GearImplicitAttribute(String implicitname) {
        this.implicitname = implicitname;
    }

    public boolean hasimplicit(ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        List<VaultGearModifier<?>> implicits = data.getModifiers(VaultGearModifier.AffixType.IMPLICIT);

        for (int i = 0; i < implicits.size(); i++) {
            if (implicits.get(i).getAttribute().getReader().getModifierName().equals(this.implicitname)) {
                return true;
            }

        }

        return false;
    }



    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem) {
            return (hasimplicit(itemStack));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem) {
           VaultGearData data = VaultGearData.read(itemStack);
           List<VaultGearModifier<?>> implicits = data.getModifiers(VaultGearModifier.AffixType.IMPLICIT);

           for (int i = 0; i < implicits.size(); i++) {
               atts.add(new GearImplicitAttribute(implicits.get(i).getAttribute().getReader().getModifierName()));
           }

       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "implicit";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{implicitname};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("implicit", this.implicitname);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new GearImplicitAttribute(nbt.getString("implicit"));
    }
}