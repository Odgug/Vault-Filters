package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static iskallia.vault.item.gear.TrinketItem.getSlotIdentifier;
import static iskallia.vault.item.gear.TrinketItem.isIdentified;

public class ItemTypeAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new ItemTypeAttribute("dummy"));
    }
    String type;
    
    public static String getType(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CharmItem) {
            return "Charm";
        }
        if (itemStack.getItem() instanceof TrinketItem) {
            return "Trinket";
        }
        if (itemStack.getItem() instanceof JewelItem) {
                return "Jewel";
        }
        if (itemStack.getItem() instanceof InscriptionItem) {
            return "Inscription";
        }
        if (itemStack.getItem() instanceof VaultGearItem) {
            return "Gear Piece";
        }
        return "Empty";
    }
    public ItemTypeAttribute(String type) {
        this.type = type;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (!(getType(itemStack).equals("Empty"))) {
            return (getType(itemStack).equals(type));
        }


        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (!(getType(itemStack).equals("Empty"))) {
           atts.add(new ItemTypeAttribute(getType(itemStack)));
       }

        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "item_type";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{type};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("itemType", this.type);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new ItemTypeAttribute(nbt.getString("itemType"));
    }
}