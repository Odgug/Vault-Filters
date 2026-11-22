package net.joseph.vaultfilters.attributes.companion.items;

import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.CompanionRelicItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/** A relic is "perfect" when it has 2+ positive modifiers and they are all the same one; negatives are ignored. */
public class CompanionRelicPerfectAttribute extends StringAttribute {
    // Companion's Challenge is the only negative relic modifier
    private static final ResourceLocation CHALLENGE = new ResourceLocation("the_vault", "companion_challenge");

    public CompanionRelicPerfectAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionRelicItem)) {
            return null;
        }
        ResourceLocation perfect = null;
        int positives = 0;
        for (ResourceLocation id : CompanionRelicItem.getModifiers(itemStack)) {
            if (CHALLENGE.equals(id)) {
                continue;
            }
            if (perfect != null && !perfect.equals(id)) {
                return null;
            }
            perfect = id;
            positives++;
        }
        if (positives < 2) {
            return null;
        }
        VaultModifier<?> modifier = VaultModifierRegistry.get(perfect);
        return modifier == null ? null : modifier.getDisplayName();
    }

    @Override
    public String getNBTKey() {
        return "companion_relic_perfect";
    }
}
