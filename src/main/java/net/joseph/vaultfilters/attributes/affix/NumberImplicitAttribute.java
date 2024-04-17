package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAttribute;
import net.minecraft.world.item.ItemStack;

public class NumberImplicitAttribute extends NumberAffixAttribute {
    public NumberImplicitAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.IMPLICIT;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(VaultGearModifier.AffixType.IMPLICIT, stack);
    }

    @Override
    public String getTranslationKey() {
        return "implicit_number";
    }

    @Override
    public String getLegacyKey() {
        return "implicitNumber";
    }
}