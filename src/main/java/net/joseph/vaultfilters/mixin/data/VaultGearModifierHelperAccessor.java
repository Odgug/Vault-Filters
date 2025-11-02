package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.gear.VaultGearModifierHelper;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.minecraft.util.Tuple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(value = VaultGearModifierHelper.class,remap = false)
public interface VaultGearModifierHelperAccessor {
    @Invoker("filterImprovableModifiers")
    static void callFilterImprovableModifiers(List<Tuple<VaultGearModifier<?>, WeightedList<VaultGearTierConfig.ModifierOutcome<?>>>> modifierReplacements) {
        throw new AssertionError();
    }
}
