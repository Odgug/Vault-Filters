package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.item.gear.TrinketItem;
import iskallia.vault.item.gear.trinketItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static iskallia.vault.item.gear.TrinketItem.getUsedVaults;
import static iskallia.vault.item.gear.TrinketItem.getUses;
import static iskallia.vault.item.gear.trinketItem.getUsedVaults;
import static iskallia.vault.item.gear.trinketItem.getUses;

public class TrinketUsesAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new TrinketUsesAttribute("0"));
    }
    String uses;

    public TrinketUsesAttribute(String uses) {
        this.uses = uses;
    }
    public static int getTrinketUses(ItemStack itemStack) {
        return getUses(itemStack) - getUsedVaults(itemStack).size();
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof TrinketItem) {
            return (getTrinketUses(itemStack) >= Integer.valueOf(uses));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof TrinketItem) {
           atts.add(new TrinketUsesAttribute(String.valueOf(getTrinketUses(itemStack))));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "trinket_uses";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{String.valueOf(uses)};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("trinketUses", this.uses);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new TrinketUsesAttribute(nbt.getString("trinketUses"));
    }
}