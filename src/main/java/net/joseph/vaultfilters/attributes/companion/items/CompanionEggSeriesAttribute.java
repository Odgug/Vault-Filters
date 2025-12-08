package net.joseph.vaultfilters.attributes.companion.items;

import iskallia.vault.item.CompanionEggItem;
import iskallia.vault.item.CompanionParticleTrailItem;
import iskallia.vault.item.CompanionSeries;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class CompanionEggSeriesAttribute extends StringAttribute {
    public CompanionEggSeriesAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionEggItem)) {
            return null;
        }

        CompanionSeries series = CompanionEggItem.getSeries(itemStack);

        if(series == null) {
            return null;
        }

        return series.getDisplayName();
    }

    @Override
    public String getNBTKey() {
        return "companion_egg_series";
    }
}
