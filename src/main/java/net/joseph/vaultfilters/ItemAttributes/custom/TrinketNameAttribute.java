package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.config.TrinketConfig;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.tool.JewelItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static iskallia.vault.item.gear.TrinketItem.getTrinket;
import static iskallia.vault.item.gear.TrinketItem.isIdentified;

public class TrinketNameAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new TrinketNameAttribute("dummy"));
    }
    String name;
    
    public static String getTrinketName(ItemStack itemStack) {
        TrinketConfig.Trinket cfg = getTrinket(itemStack).get().getTrinketConfig();
        TextComponent cmp = new TextComponent(cfg.getName());
        return cmp.getString();
    }
    public TrinketNameAttribute(String name) {
        this.name = name;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof TrinketItem) {
            if (!isIdentified(itemStack)) {
                return false;
            }
            return (getTrinketName(itemStack).equals(name));
        }


        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof TrinketItem) {
           if (!isIdentified(itemStack)) {
               return atts;
           }
           atts.add(new TrinketNameAttribute(getTrinketName(itemStack)));
       }

        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "trinket_name";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{name};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("trinketName", this.name);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new TrinketNameAttribute(nbt.getString("trinketName"));
    }
}