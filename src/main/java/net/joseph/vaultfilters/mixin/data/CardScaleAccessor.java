package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.CardScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(CardScaler.class)
public interface CardScaleAccessor {
    @Accessor(remap = false)
    Map<Integer, List<CardScaler.Filter>> getFilters();
}