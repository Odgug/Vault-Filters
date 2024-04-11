package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.world.item.ItemStack;

public class NumberPrefixAttribute extends AffixAttribute {
    public NumberPrefixAttribute(String value) {
        super(value);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier, boolean includeLevel) {
        return includeLevel && type == VaultGearModifier.AffixType.PREFIX;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(stack, VaultGearModifier.AffixType.PREFIX, true);
    }

    @Override
    public String getTranslationKey() {
        return "prefix_number";
    }

    @Override
    public String getSubNBTKey() {
        return "prefixNumber";
    }
}