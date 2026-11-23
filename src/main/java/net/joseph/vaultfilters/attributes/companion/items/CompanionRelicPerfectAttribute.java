package net.joseph.vaultfilters.attributes.companion.items;

import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.CompanionRelicItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/** Matches relics whose positive modifiers are all the same one; Companion's Challenge is ignored. */
public class CompanionRelicPerfectAttribute extends StringAttribute {
    // shortcut: assumes companion_challenge is the only negative relic modifier, revisit if VH adds more
    public static final ResourceLocation CHALLENGE = new ResourceLocation("the_vault", "companion_challenge");

    public CompanionRelicPerfectAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionRelicItem)) {
            return null;
        }
        ResourceLocation positive = null;
        for (ResourceLocation id : CompanionRelicItem.getModifiers(itemStack)) {
            if (CHALLENGE.equals(id)) {
                continue;
            }
            if (positive != null && !positive.equals(id)) {
                return null;
            }
            positive = id;
        }
        if (positive == null) {
            return null;
        }
        VaultModifier<?> modifier = VaultModifierRegistry.get(positive);
        return modifier == null ? null : modifier.getDisplayName();
    }

    @Override
    public String getNBTKey() {
        return "companion_relic_perfect";
    }
}
