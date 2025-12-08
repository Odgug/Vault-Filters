package net.joseph.vaultfilters.attributes.other;

import iskallia.vault.block.TreasureDoorBlock;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.BossRuneItem;
import iskallia.vault.item.InfusedCatalystItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.tool.JewelItem;
import iskallia.vault.item.tool.ToolItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemTypeAttribute extends StringAttribute {
    public ItemTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof CharmItem) {
            return "Charm";
        } else if (item instanceof TrinketItem) {
            return "Trinket";
        } else if (item instanceof JewelItem) {
            return "Jewel";
        } else if (item instanceof InscriptionItem) {
            return "Inscription";
        } else if (item instanceof ToolItem) {
            return "Vault Tool";
        } else if (item instanceof VaultGearItem) {
            return "Gear Piece";
        } else if (item instanceof InfusedCatalystItem) {
            return "Catalyst";
        } else if (isTreasureDoor(itemStack)) {
            return "Treasure Key";
        } else if (item instanceof BossRuneItem) {
            return "Boss Rune";
        }

        return null;
    }

    public static boolean isTreasureDoor(ItemStack itemStack) {
        for (TreasureDoorBlock.Type doorType : TreasureDoorBlock.Type.values()) {
            if (itemStack.getItem() ==  doorType.getKey()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNBTKey() {
        return "item_type";
    }

    @Override
    public String getLegacyKey() {
        return "itemType";
    }
}