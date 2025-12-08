package net.joseph.vaultfilters.attributes.packs;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.BoosterPackItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class CardPackTypeAttribute extends StringAttribute {
    public CardPackTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BoosterPackItem)) {
            return null;
        }
        return ModConfigs.BOOSTER_PACK.getName(BoosterPackItem.getId(itemStack)).map(Component::getString).orElse(null);
    }

    @Override
    public String getNBTKey() {
        return "card_pack_type";
    }
}