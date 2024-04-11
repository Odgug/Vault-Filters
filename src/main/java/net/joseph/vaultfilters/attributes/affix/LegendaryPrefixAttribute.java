package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class LegendaryPrefixAttribute extends AffixAttribute {
    public LegendaryPrefixAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier, boolean includeLevel) {
        return !includeLevel && type == VaultGearModifier.AffixType.PREFIX && modifier.getCategory() == VaultGearModifier.AffixCategory.LEGENDARY;
    }

    @Override
    public String getTranslationKey() {
        return "legendary_prefix";
    }

    @Override
    public String getSubNBTKey() {
        return "legendaryPrefix";
    }
}