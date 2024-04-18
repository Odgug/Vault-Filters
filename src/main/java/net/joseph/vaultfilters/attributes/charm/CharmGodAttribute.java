package net.joseph.vaultfilters.attributes.charm;

import iskallia.vault.gear.charm.CharmEffect;
import iskallia.vault.item.gear.CharmItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class CharmGodAttribute extends StringAttribute {
    public CharmGodAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!CharmItem.isIdentified(itemStack)) {
            return null;
        }

        String tooltip = ((CharmEffect.Config) CharmItem.getCharm(itemStack).get().getCharmConfig().getConfig()).getAttribute().getReader().getModifierName();
        if (tooltip.contains("Velara")) {
            return "Velara";
        } else if (tooltip.contains("Idona")) {
            return "Idona";
        } else if (tooltip.contains("Tenos")) {
            return "Tenos";
        } else if (tooltip.contains("Wendarr")) {
            return "Wendarr";
        }

        return null;
    }

    @Override
    public String getTranslationKey() {
        return "charm_god";
    }
}