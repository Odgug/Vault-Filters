package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.CardItem;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

import javax.print.DocFlavor;

import static iskallia.vault.item.CardItem.getCard;

public class CardAtleastTierAttribute extends IntAttribute {
    public CardAtleastTierAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }
        return getCard(itemStack).getTier();
    }


    @Override
    public String getTranslationKey() {
        return "card_tier";
    }
}