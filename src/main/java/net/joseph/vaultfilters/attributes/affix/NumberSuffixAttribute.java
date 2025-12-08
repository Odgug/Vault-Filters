package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAttribute;

public class NumberSuffixAttribute extends NumberAffixAttribute {
    public NumberSuffixAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public String getNBTKey() {
        return "suffix_number";
    }

    @Override
    public String getLegacyKey() {
        return "suffixNumber";
    }
}