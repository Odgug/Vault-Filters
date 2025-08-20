package net.joseph.vaultfilters.attributes.companion;

import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.CompanionItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CompanionTemporalAttribute extends StringAttribute {
    public CompanionTemporalAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionItem)) {
            return null;
        }

        ResourceLocation temporalModId = CompanionItem.getTemporalModifier(itemStack).orElse(null);

        if(temporalModId == null) {
            return null;
        }

        VaultModifier<?> temporalMod = VaultModifierRegistry.get(temporalModId);

        if(temporalMod == null) {
            return null;
        }

        return temporalMod.getDisplayName();
    }

    @Override
    public String getTranslationKey() {
        return "companion_temporal";
    }
}
