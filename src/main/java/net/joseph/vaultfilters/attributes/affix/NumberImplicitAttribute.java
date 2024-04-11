package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.world.item.ItemStack;

public class NumberImplicitAttribute extends AffixAttribute {
    public NumberImplicitAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier, boolean includeLevel) {
        return includeLevel && type == VaultGearModifier.AffixType.IMPLICIT;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(stack, VaultGearModifier.AffixType.IMPLICIT, true);
    }

    @Override
    public String getTranslationKey() {
        return "implicit_number";
    }

    @Override
    public String getSubNBTKey() {
        return "implicitNumber";
    }
}