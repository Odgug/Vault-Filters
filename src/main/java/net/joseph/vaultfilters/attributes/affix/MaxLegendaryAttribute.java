package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.mixin.data.VaultGearModifierHelperAccessor;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MaxLegendaryAttribute extends BooleanAttribute {
    public MaxLegendaryAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        for (VaultGearModifier<?> modifier : data.getAllModifierAffixes()) {
            if (modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY)) {
                VaultGearTierConfig cfg = VaultGearTierConfig.getConfig(itemStack).orElse(null);
                if (cfg == null) {
                    return null;
                }
                List<Tuple<VaultGearModifier<?>, WeightedList<VaultGearTierConfig.ModifierOutcome<?>>>> modifierReplacements = new ArrayList<>();
                int itemLevel = data.getItemLevel();
                VaultGearTierConfig.ModifierTierGroup group = cfg.getTierGroup(modifier.getModifierIdentifier());
                if (group != null) {
                    WeightedList<VaultGearTierConfig.ModifierOutcome<?>> replacementTiers = new WeightedList<>();
                    VaultGearTierConfig.ModifierTier<?> tier = group.getModifierForTier(modifier.getRolledTier());
                    replacementTiers.add(new VaultGearTierConfig.ModifierOutcome<>((VaultGearTierConfig.ModifierTier<?>)tier, group), tier.getWeight());

//                    group.getModifiersForLevel(itemLevel)
//                            .forEach(
//                                    tier -> replacementTiers.add(
//                                            new VaultGearTierConfig.ModifierOutcome<>((VaultGearTierConfig.ModifierTier<?>)tier, group), tier.getWeight()
//                                    )
//                            );
                    modifierReplacements.add(new Tuple(modifier, replacementTiers));
                }
                VaultGearModifierHelperAccessor.callFilterImprovableModifiers(modifierReplacements);
                return modifierReplacements.isEmpty();
            }
        }

        return false;
    }

    @Override
    public String getTranslationKey() {
        return "max_legendary";
    }
}
