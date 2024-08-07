package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(CardCondition.class)
public interface ConditionFilterAccessor {
    @Accessor
    Map<Integer, List<CardScaler.Filter>> getFilters();
}