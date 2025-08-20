package net.joseph.vaultfilters.attributes.companion.items;

import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.CompanionRelicItem;
import net.joseph.vaultfilters.attributes.abstracts.StringListAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CompanionRelicAttribute extends StringListAttribute {
    public CompanionRelicAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return "";
    }

    @Override
    public List<String> getValues(ItemStack itemStack) {
        if(itemStack.getItem() instanceof CompanionRelicItem) {
            List<ResourceLocation> modifierIds = CompanionRelicItem.getModifiers(itemStack);
            List<String> modifierNames = new ArrayList<>();

            if(modifierIds.isEmpty()) {
                return null;
            }

            for(ResourceLocation id : modifierIds) {
                VaultModifier<?> modifier = VaultModifierRegistry.get(id);
                if(modifier != null) {
                    modifierNames.add(modifier.getDisplayName());
                }
            }

            return modifierNames;
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "companion_relic_modifiers";
    }
}
