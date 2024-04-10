package net.joseph.vaultfilters.attributes.other;

import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.model.armor.ArmorPieceModel;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.init.ModGearAttributes;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class GearTransmogAttribute extends StringAttribute {
    public GearTransmogAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof VaultGearItem)) {
            return null;
        }

        VaultGearData data = VaultGearData.read(itemStack);
        Optional<ResourceLocation> modelResource = data.getFirstValue(ModGearAttributes.GEAR_MODEL);
        if (modelResource.isEmpty()) {
            return null;
        }

        Optional<? extends DynamicModel<?>> gearModel = ModDynamicModels.REGISTRIES.getModel(itemStack.getItem(), modelResource.get());
        if (gearModel.isEmpty()) {
            return null;
        }

        DynamicModel<?> model = gearModel.get();
        return model instanceof ArmorPieceModel armorModel
                ? armorModel.getArmorModel().getDisplayName()
                : model.getDisplayName();
    }

    @Override
    public String getTranslationKey() {
        return "gear_transmog";
    }

    @Override
    public String getSubNBTKey() {
        return "transmog";
    }
}