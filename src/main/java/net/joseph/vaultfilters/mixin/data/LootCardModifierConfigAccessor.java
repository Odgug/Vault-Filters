package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.TaskLootCardModifier;
import iskallia.vault.core.world.loot.LootPool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TaskLootCardModifier.Config.class)
public interface LootCardModifierConfigAccessor {
    @Accessor(remap = false)
    LootPool getLoot();
}