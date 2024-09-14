package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.GearCardModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(GearCardModifier.class)
public interface GearCardModifierAccesor {
    @Accessor
    <T> Map<Integer, T> getValues();
}