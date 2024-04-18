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
        String group = modifier.getModifierGroup();
        return group.isEmpty() ? null : withValue(group);
    }

    @Override
    public Object[] getTranslationParameters() {
        String parsedValue = this.value.substring(0,3).equals("Mod") ? this.value.substring(3) : this.value;
        return new Object[]{parsedValue};
    }
    @Override
    public String getTranslationKey() {
        return "modifier_group";
    }
}
