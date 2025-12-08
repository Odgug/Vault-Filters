package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAttribute;

public class NumberPrefixAttribute extends NumberAffixAttribute {
    public NumberPrefixAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.PREFIX;
    }

    @Override
    public String getNBTKey() {
        return "prefix_number";
    }

    @Override
    public String getLegacyKey() {
        return "prefixNumber";
    }
}