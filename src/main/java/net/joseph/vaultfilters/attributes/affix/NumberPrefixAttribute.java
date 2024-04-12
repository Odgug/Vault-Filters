package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAttribute;
import net.minecraft.world.item.ItemStack;

public class NumberPrefixAttribute extends NumberAffixAttribute {
    public NumberPrefixAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier) {
        return type == VaultGearModifier.AffixType.PREFIX;
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return appliesTo(VaultGearModifier.AffixType.PREFIX, stack);
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