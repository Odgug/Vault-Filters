package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.CardScaler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(CardEntry.class)
public interface CardEntryAccessor {
    @Accessor(remap = false)
    CardCondition getCondition();
}