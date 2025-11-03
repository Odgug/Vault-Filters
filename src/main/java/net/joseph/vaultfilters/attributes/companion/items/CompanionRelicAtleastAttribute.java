package net.joseph.vaultfilters.attributes.companion.items;

import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.CompanionRelicItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CompanionRelicAtleastAttribute extends IntAttribute {
    public CompanionRelicAtleastAttribute(Integer value) {
        super(value);
    }
    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if(itemStack.getItem() instanceof CompanionRelicItem) {
            List<ResourceLocation> modifierIds = CompanionRelicItem.getModifiers(itemStack);
            return modifierIds.size();

        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "companion_relic_count";
    }
}
