package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.CardNeighborType;
import iskallia.vault.core.card.CardScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(CardCondition.Filter.class)
public interface ConditionFilterAccessor {
    @Accessor
    Set<CardNeighborType> getNeighborFilter();
    @Accessor
    Set<Integer> getTierFilter();
    @Accessor
    Set<CardEntry.Color> getColorFilter();
    @Accessor
    Set<String> getGroupFilter();
    @Accessor
    Integer getMinCount();

    @Accessor
    Integer getMaxCount();
}