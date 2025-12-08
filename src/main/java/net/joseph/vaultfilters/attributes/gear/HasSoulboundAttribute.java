package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class HasSoulboundAttribute extends BooleanAttribute {
    public HasSoulboundAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
            VaultGearData data = VaultGearData.read(itemStack);
            if (!data.hasAttribute(ModGearAttributes.SOULBOUND)) {
                return false;
            }
            return data.getFirstValue(ModGearAttributes.SOULBOUND).isPresent();
        }
        return false;
    }

    @Override
    public String getNBTKey() {
        return "soulbound_base";
    }
}
