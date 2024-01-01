package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.data.AttributeGearData;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static net.joseph.vaultfilters.ItemAttributes.custom.IsUnidentifiedAttribute.isUnidentified;

public class JewelRarityAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new JewelRarityAttribute("dummy"));
    }
    String rarity;
    public static String rarityToJewel(String rarity) {
        if (rarity.equals("Common")) {
            return "Chipped";
        }
        if (rarity.equals("Rare")) {
            return "Flawed";
        }
        if (rarity.equals("Epic")) {
            return "Flawless";
        }
        if (rarity.equals("Omega")) {
            return "Perfect";
        }
        return "NULL";
    }

    public JewelRarityAttribute(String rarity) {
        this.rarity = rarity;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof JewelItem) {
            return (rarityToJewel(VaultGearData.read(itemStack).getRarity().toString()).equals(rarity));
        }
        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof JewelItem) {
           atts.add(new JewelRarityAttribute(rarityToJewel(VaultGearData.read(itemStack).getRarity().toString())));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "jewel_rarity";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{rarity};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("jewelRarity", this.rarity);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new JewelRarityAttribute(nbt.getString("jewelRarity"));
    }
}