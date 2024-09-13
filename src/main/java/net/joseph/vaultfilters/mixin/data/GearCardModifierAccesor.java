package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.GearCardModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GearCardModifier.class)
public interface GearCardModifierAccesor {
    @Accessor(remap = false)
    <T> Map<Integer, T> getValues();
}