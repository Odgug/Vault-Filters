package net.joseph.vaultfilters.attributes.companion.items;

import iskallia.vault.item.CompanionParticleTrailItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class CompanionTrailAttribute extends StringAttribute {
    public CompanionTrailAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionParticleTrailItem)) {
            return null;
        }

        CompanionParticleTrailItem.TrailType trailType = CompanionParticleTrailItem.getType(itemStack);

        if(trailType == null) {
            return null;
        }

        return trailType.getDisplayName();
    }

    @Override
    public String getNBTKey() {
        return "companion_trail_type";
    }
}
