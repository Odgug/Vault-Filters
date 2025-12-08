package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class GreaterAffixAttribute extends AffixAttribute {
    public GreaterAffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return null;
    }

    @Override
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.GREATER);
    }


    @Override
    public boolean checkModifier(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.GREATER) && this.value.equals(getName(modifier));
    }
    @Override
    public String getNBTKey() {
        return "greater_affix";
    }

}
