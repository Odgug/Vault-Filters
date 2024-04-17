package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;

public class ModifierGroupAttribute extends AffixAttribute {
    public ModifierGroupAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return null;
    }

    @Override
    public ItemAttribute withValue(VaultGearModifier<?> modifier) {
        return withValue(modifier.getModifierGroup());
    }

    @Override
    public String getTranslationKey() {
        return "modifier_group";
    }
}
