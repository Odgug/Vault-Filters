package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.world.item.ItemStack;

public class LegendarySuffixAttribute extends AffixAttribute {
    public LegendarySuffixAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier) {
        return type == VaultGearModifier.AffixType.SUFFIX && modifier.getCategory() == VaultGearModifier.AffixCategory.LEGENDARY;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(VaultGearModifier.AffixType.SUFFIX, stack);
    }

    @Override
    public String getTranslationKey() {
        return "legendary_suffix";
    }

    @Override
    public String getLegacyKey() {
        return "legendarySuffix";
    }
}