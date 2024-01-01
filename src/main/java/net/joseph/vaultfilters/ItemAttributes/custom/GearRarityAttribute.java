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
    public static String rarityToJewel(String rarity) {
        if (rarity.equals("COMMON")) {
            return "CHIPPED";
        }
        if (rarity.equals("RARE")) {
            return "FLAWED";
        }
        if (rarity.equals("EPIC")) {
            return "FLAWLESS";
        }
        if (rarity.equals("OMEGA")) {
            return "PERFECT";
        }
        return "NULL";
    }

    public static String jewelToRarity(String jewel) {
        if (jewel.equals("CHIPPED")) {
            return "COMMON";
        }
        if (jewel.equals("FLAWED")) {
            return "RARE";
        }
        if (jewel.equals("FLAWLESS")) {
            return "EPIC";
        }
        if (jewel.equals("PERFECT")) {
            return "OMEGA";
        }
        return "NULL";
    }
    public static String getCharmRarity(ItemStack itemStack) {
        if (itemStack.getItem() == ModItems.SMALL_CHARM) {
            return "NOBLE";
        }
        if (itemStack.getItem() == ModItems.LARGE_CHARM) {
            return "DISTINGUISHED";
        }
        if (itemStack.getItem() == ModItems.GRAND_CHARM) {
            return "REGAL";
        }
        return "MAJESTIC";
    }
    public GearRarityAttribute(String rarity) {
        this.rarity = rarity;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
            if (isUnidentified(itemStack)) {
                String rolltype = AttributeGearData.read(itemStack).getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE).get();
                return rolltype.substring(0, rolltype.length() - 1).toUpperCase().equals(rarity);
            }
            return (VaultGearData.read(itemStack).getRarity().toString().equals(rarity));
        }

        if (itemStack.getItem() instanceof JewelItem) {
            return (rarityToJewel(VaultGearData.read(itemStack).getRarity().toString()).equals(rarity));
        }
        if (itemStack.getItem() instanceof CharmItem) {
            return (getCharmRarity(itemStack).equals(rarity));
        }
        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
           if (!isUnidentified(itemStack)) {
               atts.add(new GearRarityAttribute(VaultGearData.read(itemStack).getRarity().toString()));
           }
           else {
               String rolltype = AttributeGearData.read(itemStack).getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE).get();
               atts.add(new GearRarityAttribute(rolltype.substring(0,rolltype.length()-1).toUpperCase()));
           }
       }

       if (itemStack.getItem() instanceof JewelItem) {
           atts.add(new GearRarityAttribute(rarityToJewel(VaultGearData.read(itemStack).getRarity().toString())));
       }
       if (itemStack.getItem() instanceof CharmItem) {
           atts.add(new GearRarityAttribute(getCharmRarity(itemStack)));
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
        nbt.putString("rarity", this.rarity);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new GearRarityAttribute(nbt.getString("rarity"));
    }
}