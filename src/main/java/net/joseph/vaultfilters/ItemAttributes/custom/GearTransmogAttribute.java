package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.model.armor.ArmorPieceModel;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.gear.data.GearDataCache;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GearTransmogAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new GearTransmogAttribute("dummy"));
    }
    String transmog;
    
    public static String getGearTransmog(ItemStack itemStack) {
        //return GearDataCache.of(itemStack).getGearModel().get().getNamespace();
        VaultGearData data = VaultGearData.read(itemStack);
        if (data.getFirstValue(ModGearAttributes.GEAR_MODEL).isEmpty()) {
            return "BLANK";
        }
        ResourceLocation modelId = data.getFirstValue(ModGearAttributes.GEAR_MODEL).get();
        if (ModDynamicModels.REGISTRIES.getModel(itemStack.getItem(), modelId).isEmpty()) {
            return "BLANK";
        }
            DynamicModel<?> gearModel = ModDynamicModels.REGISTRIES.getModel(itemStack.getItem(), modelId).get();
            Item patt7655$temp = itemStack.getItem();
            if (patt7655$temp instanceof VaultGearItem gearItem) {
                String name = gearModel.getDisplayName();
                if (gearModel instanceof ArmorPieceModel modelPiece) {
                    name = modelPiece.getArmorModel().getDisplayName();
                }
                return name;
            }
        return "BLANK";
    }
    public GearTransmogAttribute(String transmog) {
        this.transmog = transmog;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
            if (!GearDataCache.of(itemStack).getGearModel().isPresent()) {
                return false;
            }
            if (getGearTransmog(itemStack).equals("BLANK")) {
                return false;
            }
            return (getGearTransmog(itemStack).equals(transmog));
        }


        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem && !(itemStack.getItem() instanceof JewelItem)) {
           if (!GearDataCache.of(itemStack).getGearModel().isPresent()) {
              return atts;
           }
           if (getGearTransmog(itemStack).equals("BLANK")) {
               return atts;
           }
           atts.add(new GearTransmogAttribute(getGearTransmog(itemStack)));
       }

        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "gear_transmog";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{transmog};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("transmog", this.transmog);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new GearTransmogAttribute(nbt.getString("transmog"));
    }
}