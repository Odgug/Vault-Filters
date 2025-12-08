package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.joseph.vaultfilters.attributes.gear.IsUnidentifiedAttribute;
import net.minecraft.world.item.ItemStack;

public class HasAtleastLegendaryAttribute extends IntAttribute {
    public HasAtleastLegendaryAttribute(Integer value) {
        super(value);
    }

    @Override
    protected NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        if (IsUnidentifiedAttribute.isUnidentified(itemStack)) {
            if (data.hasAttribute(ModGearAttributes.IS_LEGENDARY)) {
                return 1;
            }
        }
        int count = 0;
        for (VaultGearModifier<?> modifier : data.getAllModifierAffixes()) {
            if (modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY)) {
                count++;
            }
        }
        if (count == 0) {
            return null;
        }
        return count;
    }

    @Override
    public String getNBTKey() {
        return "has_legendary";
    }

    @Override
    public String getTranslationKey() {
        if (this.value == 1) {
            return getNBTKey() + "_single";
        }
        return getNBTKey() + "_plural";
    }

}