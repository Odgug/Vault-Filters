package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.config.InscriptionConfig;
import iskallia.vault.gear.VaultGearHelper;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.joseph.vaultfilters.ItemAttributes.custom.IsUnidentifiedAttribute.isUnidentified;

public class GearRarityAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new GearRarityAttribute("dummy"));
    }
    String rarity;


    public GearRarityAttribute(String rarity) {
        this.rarity = rarity;
    }

    public static String getGearRarity(ItemStack itemStack) {
        if (itemStack.getItem() instanceof JewelItem) {
            return "NULL";
        }
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return "NULL";
        }
        if (isUnidentified(itemStack)) {
            String rolltype = AttributeGearData.read(itemStack).getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE).get();
            return rolltype.substring(0, rolltype.length() - 1);
        }
        VaultGearData data = VaultGearData.read(itemStack);
        String tempRarity = data.getRarity().toString();
        return capFirst(tempRarity);
    }
    public static String capFirst(String word) {
        String word2 = word.toLowerCase();
        word2 = word2.substring(0,1).toUpperCase();
        return word2;
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
           return (getGearRarity(itemStack).equals(rarity));
        }
        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {

               atts.add(new GearRarityAttribute(getGearRarity(itemStack)));

       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "gear_rarity";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{rarity};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("gearRarity", this.rarity);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new GearRarityAttribute(nbt.getString("gearRarity"));
    }
}