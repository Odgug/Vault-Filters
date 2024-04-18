package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class LegendaryPrefixAttribute extends AffixAttribute {
    public LegendaryPrefixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.PREFIX;
    }

    @Override
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return modifier.getCategory() == VaultGearModifier.AffixCategory.LEGENDARY;
    }

    @Override
    public String getTranslationKey() {
        return "legendary_prefix";
    }

    @Override
    public String getLegacyKey() {
        return "legendaryPrefix";
    }
}