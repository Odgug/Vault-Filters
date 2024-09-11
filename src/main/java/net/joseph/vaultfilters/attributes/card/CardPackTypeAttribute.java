package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.BoosterPackItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class CardPackTypeAttribute extends StringAttribute {
    public CardPackTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof BoosterPackItem)) {
            return null;
        }
        Optional<Component> name = ModConfigs.BOOSTER_PACK.getName(BoosterPackItem.getId(itemStack));
        if (name.isEmpty()) {
            return null;
        }
        return name.get().getString();
    }

    @Override
    public String getTranslationKey() {
        return "card_pack_type";
    }

}