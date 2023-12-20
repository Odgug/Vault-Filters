package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GearLevelAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new GearLevelAttribute("0"));
    }
    String level;

    public GearLevelAttribute(String level) {
        this.level = level;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem) {
            return (VaultGearData.read(itemStack).getItemLevel() >= Integer.valueOf(level));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem) {
           atts.add(new GearLevelAttribute(String.valueOf(VaultGearData.read(itemStack).getItemLevel())));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "level";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{String.valueOf(level)};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("level", this.level);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new GearLevelAttribute(nbt.getString("level"));
    }
}