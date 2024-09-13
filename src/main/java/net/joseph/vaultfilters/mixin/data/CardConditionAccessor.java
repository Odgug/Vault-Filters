package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.CardCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(CardCondition.class)
public interface CardConditionAccessor {
    @Accessor(remap = false)
    Map<Integer, List<CardCondition.Filter>> getFilters();
}