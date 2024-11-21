
package net.joseph.vaultfilters.attributes.affix;

import iskallia.vault.block.entity.VaultArtisanStationTileEntity;
import iskallia.vault.client.gui.screen.block.VaultArtisanStationScreen;
import iskallia.vault.container.VaultArtisanStationContainer;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class IsCraftedAttribute extends BooleanAttribute {
    public IsCraftedAttribute(Boolean value) {
        super(true);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        return data.hasAttribute(ModGearAttributes.CRAFTED_BY);
    }

    @Override
    public String getTranslationKey() {
        return "is_crafted";
    }

    @Override
    public String getLegacyKey() {
        return "isCrafted";
    }
}