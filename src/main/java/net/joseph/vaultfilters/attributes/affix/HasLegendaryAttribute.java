package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class HasLegendaryAttribute extends BooleanAttribute {
    public HasLegendaryAttribute(Boolean value) {
        super(true);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        for (VaultGearModifier<?> modifier : data.getAllModifierAffixes()) {
            if (modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY)) {
                return true;
            }
        }
        if (data.hasAttribute(ModGearAttributes.IS_LEGENDARY)) {
            return true;
        }

        return false;
    }

    @Override
    public String getNBTKey() {
        return "has_legendary";
    }

}