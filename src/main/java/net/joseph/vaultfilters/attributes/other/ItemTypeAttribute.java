package net.joseph.vaultfilters.attributes.other;

import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class ItemTypeAttribute extends StringAttribute {
    public ItemTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CharmItem) {
            return "Charm";
        } else if (itemStack.getItem() instanceof TrinketItem) {
            return "Trinket";
        } else if (itemStack.getItem() instanceof JewelItem) {
            return "Jewel";
        } else if (itemStack.getItem() instanceof InscriptionItem) {
            return "Inscription";
        } else if (itemStack.getItem() instanceof VaultGearItem) {
            return "Gear Piece";
        }

        return null;
    }

    @Override
    public String getTranslationKey() {
        return "item_type";
    }

    @Override
    public String getLegacyKey() {
        return "itemType";
    }
}