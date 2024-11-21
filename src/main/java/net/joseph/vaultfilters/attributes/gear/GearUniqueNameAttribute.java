package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.config.UniqueGearConfig;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class GearUniqueNameAttribute extends StringAttribute {
    public GearUniqueNameAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        Optional<ResourceLocation> uniqueResource = data.getFirstValue(ModGearAttributes.UNIQUE_ITEM_KEY);
        if (uniqueResource.isEmpty()) {
            return null;
        }

        Optional<UniqueGearConfig.Entry> uniqueEntry = ModConfigs.UNIQUE_GEAR.getEntry(uniqueResource.get());

        if (uniqueEntry.isEmpty()) {
            return null;
        }

        UniqueGearConfig.Entry model = uniqueEntry.get();
        return model.getName();
    }

    @Override
    public String getTranslationKey() {
        return "gear_unique_key";
    }

}