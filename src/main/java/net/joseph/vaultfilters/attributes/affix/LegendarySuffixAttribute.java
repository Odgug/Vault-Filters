package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class LegendarySuffixAttribute extends AffixAttribute {
    public LegendarySuffixAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier, boolean includeLevel) {
        return !includeLevel && type == VaultGearModifier.AffixType.SUFFIX && modifier.getCategory() == VaultGearModifier.AffixCategory.LEGENDARY;
    }

    @Override
    public String getTranslationKey() {
        return "legendary_suffix";
    }

    @Override
    public String getSubNBTKey() {
        return "legendarySuffix";
    }
}