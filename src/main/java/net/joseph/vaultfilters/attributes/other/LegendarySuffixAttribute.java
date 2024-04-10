package net.joseph.vaultfilters.attributes.other;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.GearAttribute;

public class LegendarySuffixAttribute extends GearAttribute {
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