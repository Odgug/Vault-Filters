package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.GearCardModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = GearCardModifier.class, remap = false)
public interface GearCardModifierAccesor {
    @Accessor
    <T> Map<Integer, T> getValues();
}