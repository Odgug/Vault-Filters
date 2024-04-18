package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class PrefixAttribute extends AffixAttribute {
    public PrefixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.PREFIX;
    }

    @Override
    public String getTranslationKey() {
        return "prefix";
    }
}