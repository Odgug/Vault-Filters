package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.modifier.card.GearCardModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = iskallia.vault.core.card.modifier.card.GearCardModifier.class, remap = false)
public interface GearCardModifierAccesor {
    @Accessor
    <T> Map<Integer, T> getValues();
}