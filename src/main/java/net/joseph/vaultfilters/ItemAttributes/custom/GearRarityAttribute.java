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
        return word.substring(0,1).toUpperCase() + word.substring(1,word.length()).toLowerCase();
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
        String datafixer = nbt.getString("rarity");
        if (datafixer.equals("NOBLE") || datafixer.equals("REGAL") || datafixer.equals("DISTINGUISHED") || datafixer.equals("MAJESTIC")) {
            return new CharmRarityAttribute(capFirst(datafixer));
        }
        if (datafixer.equals("CHIPPED") || datafixer.equals("FLAWED") || datafixer.equals("FLAWLESS") || datafixer.equals("PERFECT")) {
            return new JewelRarityAttribute(capFirst(datafixer));
        }
        if (datafixer.equals("SCRAPPY") || datafixer.equals("COMMON") || datafixer.equals("RARE") || datafixer.equals("EPIC") || datafixer.equals("OMEGA")) {
            return new GearRarityAttribute(capFirst(datafixer));
        }
        return new GearRarityAttribute(nbt.getString("gearRarity"));
    }
}