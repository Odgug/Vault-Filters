package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.config.UniqueGearConfig;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class GearIsUniqueAttribute extends BooleanAttribute {
    public GearIsUniqueAttribute(Boolean value) {
        super(value);
    }

    @Override
    public Boolean getValue(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof VaultGearItem)) {
            return false;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        Optional<ResourceLocation> uniqueResource = data.getFirstValue(ModGearAttributes.UNIQUE_ITEM_KEY);
        if (uniqueResource.isEmpty()) {
            return false;
        }

        Optional<UniqueGearConfig.Entry> uniqueEntry = ModConfigs.UNIQUE_GEAR.getEntry(uniqueResource.get());

        return !uniqueEntry.isEmpty();
    }

    @Override
    public String getTranslationKey() {
        return "gear_unique";
    }

}