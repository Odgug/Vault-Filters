package net.joseph.vaultfilters.attributes.catalysts;

import iskallia.vault.core.vault.modifier.modifier.DecoratorAddModifier;
import iskallia.vault.core.vault.modifier.modifier.DecoratorCascadeModifier;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import net.joseph.vaultfilters.attributes.abstracts.CatalystModifierAttribute;

public class CatalystModifierCategoryAttribute extends CatalystModifierAttribute {
    public CatalystModifierCategoryAttribute(String value) {
        super(value);
    }
    @Override
    public String getTranslationKey() {
        return "catalyst_category";
    }
    @Override
    public <T> String getName(VaultModifier modifier) {
        if (modifier instanceof DecoratorAddModifier) {
            return "Bonus Chests";
        } else if (modifier instanceof DecoratorCascadeModifier) {
            return "Cascading";
        }

        return "";
    }
}
