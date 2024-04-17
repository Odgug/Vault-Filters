package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.world.item.ItemStack;

public class LegendaryPrefixAttribute extends AffixAttribute {
    public LegendaryPrefixAttribute(String value) {
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
        return appliesTo(VaultGearModifier.AffixType.PREFIX, stack);
    }

    @Override
    public String getTranslationKey() {
        return "legendary_prefix";
    }

    @Override
    public String getLegacyKey() {
        return "legendaryPrefix";
    }
}