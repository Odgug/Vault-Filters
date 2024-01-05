package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModConfigs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HasSoulValueAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new HasSoulValueAttribute("dummy"));
    }
    String soulValue;

    public HasSoulValueAttribute(String soulValue) { this.soulValue = soulValue;}

    public static boolean hasSoulValue(ItemStack itemStack) {

        return ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().containsKey((itemStack.getItem().getRegistryName()));
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {

       return hasSoulValue(itemStack);
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
           if (hasSoulValue(itemStack)) {
               atts.add(new HasSoulValueAttribute("soulValue"));
           }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "has_soul";
    }
    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{soulValue};
    }
    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("soulValue", this.soulValue);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new HasSoulValueAttribute(nbt.getString("soulValue"));
    }


}