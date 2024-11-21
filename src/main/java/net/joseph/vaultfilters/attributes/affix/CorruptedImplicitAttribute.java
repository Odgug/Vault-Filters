package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class CorruptedImplicitAttribute extends AffixAttribute {
    public CorruptedImplicitAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.IMPLICIT;
    }

    @Override
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.CORRUPTED);
    }


    @Override
    public boolean checkModifier(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.CORRUPTED) && this.value.equals(getName(modifier));
    }
    @Override
    public String getTranslationKey() {
        return "corrupted_implicit";
    }

    @Override
    public String getLegacyKey() {
        return "corruptedImplicit";
    }
}