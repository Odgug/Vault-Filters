package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.world.item.ItemStack;

public class SuffixAttribute extends AffixAttribute {
    public SuffixAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier) {
        return type == VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(VaultGearModifier.AffixType.SUFFIX, stack);
    }

    @Override
    public String getTranslationKey() {
        return "suffix";
    }
}