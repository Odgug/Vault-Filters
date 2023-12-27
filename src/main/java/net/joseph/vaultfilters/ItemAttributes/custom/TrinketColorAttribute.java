package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.config.TrinketConfig;
import iskallia.vault.item.gear.TrinketItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static iskallia.vault.item.gear.TrinketItem.*;

public class TrinketColorAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new TrinketColorAttribute("dummy"));
    }
    String color;
    
    public static String getTrinketColor(ItemStack itemStack) {
        if (!isIdentified(itemStack)) {
            return "Pink";
        }
        String identifier = getSlotIdentifier(itemStack).get();
        if (identifier.contains("blue")) {
            return "Blue";
        }
        if (identifier.contains("red")) {
            return "Red";
        }
        return identifier;
    }
    public TrinketColorAttribute(String color) {
        this.color = color;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof TrinketItem) {
            return (getTrinketColor(itemStack).equals(color));
        }


        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof TrinketItem) {
           atts.add(new TrinketColorAttribute(getTrinketColor(itemStack)));
       }

        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "trinket_color";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{color};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("trinketColor", this.color);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new TrinketColorAttribute(nbt.getString("trinketColor"));
    }
}