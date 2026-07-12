package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.gear.attribute.type.VaultGearAttributeTypeMerger;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class HasEndlessAttribute extends BooleanAttribute {
    public HasEndlessAttribute(Boolean value) {
        super(true);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        return data.get(ModGearAttributes.IMMORTALITY, VaultGearAttributeTypeMerger.floatSum()) >= 1.0F;
    }

    public String getNBTKey() {
        return "has_endless";
    }
}
