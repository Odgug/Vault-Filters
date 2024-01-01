package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.gear.CharmItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static iskallia.vault.item.gear.CharmItem.*;

public class CharmUsesAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new CharmUsesAttribute("0"));
    }
    String uses;

    public CharmUsesAttribute(String uses) {
        this.uses = uses;
    }
    public static int getCharmUses(ItemStack itemStack) {
        return getUses(itemStack) - getUsedVaults(itemStack).size();
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof CharmItem && isIdentified(itemStack)) {
            return (getCharmUses(itemStack) >= Integer.valueOf(uses));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof CharmItem && isIdentified(itemStack)) {
           atts.add(new CharmUsesAttribute(String.valueOf(getCharmUses(itemStack))));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "charm_uses";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{String.valueOf(uses)};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("charmUses", this.uses);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new CharmUsesAttribute(nbt.getString("charmUses"));
    }
}