package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.TaskLootCardModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = TaskLootCardModifier.class, remap = false)
public interface TaskLootCardModifierAccessor {
    @Accessor
    Map<Integer, Integer> getCounts();
}