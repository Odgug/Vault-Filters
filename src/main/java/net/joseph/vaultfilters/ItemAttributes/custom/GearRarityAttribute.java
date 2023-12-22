package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.config.InscriptionConfig;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
    public GearRarityAttribute(String rarity) {
        this.rarity = rarity;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
            return (VaultGearData.read(itemStack).getRarity().toString().equals(rarity));
        }

        if (itemStack.getItem() instanceof JewelItem) {
            return (jewelToRarity(VaultGearData.read(itemStack).getRarity().toString()).equals(rarity));
        }
        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
           atts.add(new GearRarityAttribute(VaultGearData.read(itemStack).getRarity().toString()));
       }

       if (itemStack.getItem() instanceof JewelItem) {
           atts.add(new GearRarityAttribute(rarityToJewel(VaultGearData.read(itemStack).getRarity().toString())));
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