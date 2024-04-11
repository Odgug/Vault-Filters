package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.world.item.ItemStack;

public class NumberSuffixAttribute extends AffixAttribute {
    public NumberSuffixAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier, boolean includeLevel) {
        return includeLevel && type == VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(stack, VaultGearModifier.AffixType.SUFFIX, true);
    }

    @Override
    public String getTranslationKey() {
        return "suffix_number";
    }

    @Override
    public String getSubNBTKey() {
        return "suffixNumber";
    }
}