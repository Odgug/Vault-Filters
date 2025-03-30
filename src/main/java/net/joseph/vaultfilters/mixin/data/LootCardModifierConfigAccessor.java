package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.core.card.modifier.card.TaskLootCardModifier;
import iskallia.vault.core.world.loot.LootPool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TaskLootCardModifier.Config.class, remap = false)
public interface LootCardModifierConfigAccessor {
    @Accessor
    LootPool getLoot();
}