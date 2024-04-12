package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAttribute;
import net.minecraft.world.item.ItemStack;

public class NumberImplicitAttribute extends NumberAffixAttribute {
    public NumberImplicitAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier) {
        return type == VaultGearModifier.AffixType.IMPLICIT;
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
    public String getSubNBTKey() {
        return "implicitNumber";
    }
}