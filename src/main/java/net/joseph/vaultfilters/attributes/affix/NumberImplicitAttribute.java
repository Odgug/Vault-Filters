package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAttribute;

public class NumberImplicitAttribute extends NumberAffixAttribute {
    public NumberImplicitAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.IMPLICIT;
    }

    @Override
    public String getNBTKey() {
        return "implicit_number";
    }

    @Override
    public String getLegacyKey() {
        return "implicitNumber";
    }
}