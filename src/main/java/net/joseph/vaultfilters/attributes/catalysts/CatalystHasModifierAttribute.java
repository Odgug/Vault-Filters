package net.joseph.vaultfilters.attributes.catalysts;

import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.attributes.abstracts.CatalystModifierAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class CatalystHasModifierAttribute extends CatalystModifierAttribute {
    public CatalystHasModifierAttribute(String value) {
        super(value);
    }
    @Override
    public String getTranslationKey() {
        return "catalyst_modifier";
    }

}
