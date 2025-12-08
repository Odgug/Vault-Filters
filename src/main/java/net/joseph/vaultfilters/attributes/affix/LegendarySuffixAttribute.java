package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class LegendarySuffixAttribute extends AffixAttribute {
    public LegendarySuffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY);
    }

    @Override
    public boolean checkModifier(VaultGearModifier<?> modifier) {

        return modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY) && this.value.equals(getName(modifier));
    }
    @Override
    public String getNBTKey() {
        return "legendary_suffix";
    }

    @Override
    public String getLegacyKey() {
        return "legendarySuffix";
    }
}