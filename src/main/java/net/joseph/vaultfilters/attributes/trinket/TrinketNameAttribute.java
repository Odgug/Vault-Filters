package net.joseph.vaultfilters.attributes.trinket;

import iskallia.vault.config.TrinketConfig;
import iskallia.vault.gear.trinket.TrinketEffect;
import iskallia.vault.item.gear.TrinketItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class TrinketNameAttribute extends StringAttribute {
    public TrinketNameAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!TrinketItem.isIdentified(itemStack)) {
            return null;
        }

        Optional<TrinketEffect<?>> trinket = TrinketItem.getTrinket(itemStack);
        if (trinket.isPresent()) {
            TrinketConfig.Trinket cfg = trinket.get().getTrinketConfig();
            TextComponent cmp = new TextComponent(cfg.getName());
            return cmp.getString();
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "trinket_name";
    }

    @Override
    public String getSubNBTKey() {
        return "trinketName";
    }
}