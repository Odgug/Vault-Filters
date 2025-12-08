package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class SuffixAttribute extends AffixAttribute {
    public SuffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public String getNBTKey() {
        return "suffix";
    }
}