package net.joseph.vaultfilters.attributes.other;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.GearAttribute;
import net.minecraft.world.item.ItemStack;

public class GearSuffixAttribute extends GearAttribute {
    public GearSuffixAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier, boolean includeLevel) {
        return !includeLevel && type == VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(stack, VaultGearModifier.AffixType.SUFFIX, false);
    }

    @Override
    public String getTranslationKey() {
        return "prefix";
    }
}