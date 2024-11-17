
package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class HasCraftedAttribute extends BooleanAttribute {
    public HasCraftedAttribute(Boolean value) {
        super(true);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        for (VaultGearModifier<?> modifier : data.getAllModifierAffixes()) {
            if (modifier.hasCategory(VaultGearModifier.AffixCategory.CRAFTED)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getTranslationKey() {
        return "has_crafted";
    }

    @Override
    public String getLegacyKey() {
        return "crafted";
    }
}