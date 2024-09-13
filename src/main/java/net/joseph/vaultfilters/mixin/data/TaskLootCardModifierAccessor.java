package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.TaskLootCardModifier;
import iskallia.vault.core.world.loot.LootPool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(TaskLootCardModifier.class)
public interface TaskLootCardModifierAccessor {
    @Accessor(remap = false)
    Map<Integer, Integer> getCounts();
}