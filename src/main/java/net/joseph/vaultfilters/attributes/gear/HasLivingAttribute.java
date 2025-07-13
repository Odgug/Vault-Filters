package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.attributes.affix.CorruptedImplicitAttribute;
import net.joseph.vaultfilters.attributes.affix.HasCorruptedAttribute;
import net.minecraft.world.item.ItemStack;

public class HasLivingAttribute extends BooleanAttribute {
    public HasLivingAttribute(Boolean value) {
        super(true);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        return data.hasAttribute(ModGearAttributes.LIVING);
    }

    public String getTranslationKey() {
        return "has_living";
    }
}