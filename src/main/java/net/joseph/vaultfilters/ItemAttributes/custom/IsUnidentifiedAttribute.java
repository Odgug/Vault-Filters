package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.VaultGearState;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.gear.TrinketItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IsUnidentifiedAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new IsUnidentifiedAttribute("dummy"));
    }
    String unidentified;

    public IsUnidentifiedAttribute(String unidentified) { this.unidentified = unidentified;}

    public static boolean isUnidentified(ItemStack stack) {
            AttributeGearData data = AttributeGearData.read(stack);
            return !(data.getFirstValue(ModGearAttributes.STATE).orElse(VaultGearState.UNIDENTIFIED) == VaultGearState.IDENTIFIED);
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem || itemStack.getItem() instanceof TrinketItem) {
            return isUnidentified(itemStack);
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem  || itemStack.getItem() instanceof TrinketItem) {
           if (isUnidentified(itemStack)) {
               atts.add(new IsUnidentifiedAttribute("unidentified"));
           }
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "is_unidentified";
    }
    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{unidentified};
    }
    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("unidentified", this.unidentified);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new IsUnidentifiedAttribute(nbt.getString("unidentified"));
    }


}