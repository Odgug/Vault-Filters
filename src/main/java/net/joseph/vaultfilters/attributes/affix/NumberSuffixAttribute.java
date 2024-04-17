package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAttribute;
import net.minecraft.world.item.ItemStack;

public class NumberSuffixAttribute extends NumberAffixAttribute {
    public NumberSuffixAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.SUFFIX;
    }
    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(VaultGearModifier.AffixType.SUFFIX, stack);
    }

    @Override
    public String getTranslationKey() {
        return "suffix_number";
    }

    @Override
    public String getLegacyKey() {
        return "suffixNumber";
    }
}