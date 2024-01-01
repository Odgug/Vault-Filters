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

public class CharmRarityAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new CharmRarityAttribute("dummy"));
    }
    String rarity;



    public static String getCharmRarity(ItemStack itemStack) {
        if (itemStack.getItem() == ModItems.SMALL_CHARM) {
            return "Noble";
        }
        if (itemStack.getItem() == ModItems.LARGE_CHARM) {
            return "Distinguished";
        }
        if (itemStack.getItem() == ModItems.GRAND_CHARM) {
            return "Regal";
        }
        return "Majestic";
    }
    public CharmRarityAttribute(String rarity) {
        this.rarity = rarity;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CharmItem) {
            return (getCharmRarity(itemStack).equals(rarity));
        }
        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof CharmItem) {
           atts.add(new CharmRarityAttribute(getCharmRarity(itemStack)));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "charm_rarity";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{rarity};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("charmRarity", this.rarity);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new CharmRarityAttribute(nbt.getString("charmRarity"));
    }
}