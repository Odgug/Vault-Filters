package net.joseph.vaultfilters.attributes.relic;

import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.gear.TemporalShardItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class TemporalRelicModifierAttribute extends StringAttribute {
    public TemporalRelicModifierAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack stack) {
        if(!(stack.getItem() instanceof TemporalShardItem)) {
            return null;
        }

        VaultModifier<?> modifier = TemporalShardItem.getVaultModifier(stack);

        if(modifier == null) {
            return null;
        }


        return modifier.getDisplayName();
    }

    @Override
    public String getNBTKey() {
        return "temporal_relic_modifier";
    }
}
