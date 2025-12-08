package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class CorruptedSuffixAttribute extends AffixAttribute {
    public CorruptedSuffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.SUFFIX;
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
    public String getNBTKey() {
        return "corrupted_suffix";
    }

}