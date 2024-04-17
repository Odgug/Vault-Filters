package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.world.item.ItemStack;

public class LegendarySuffixAttribute extends AffixAttribute {
    public LegendarySuffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.IMPLICIT;
    }
    @Override
    public ItemAttribute attFromModifier(VaultGearModifier<?> modifier) {
        if (modifier.getCategory() == VaultGearModifier.AffixCategory.LEGENDARY) {
            return withValue(getName(modifier));
        }
        return null;
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