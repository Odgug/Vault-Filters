package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class OldAttribute extends VaultAttribute{

    protected OldAttribute(Object value) {
        super(value);
    }

    @Override
    public ItemAttribute withValue(Object value) {
        return null;
    }


    @Override
    public boolean appliesTo(ItemStack itemStack) {
        return false;
    }

    @Override
    public Object getValue(ItemStack itemStack) {
        return null;
    }
    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        return attributes;
    }
    @Override
    public void writeNBT(CompoundTag nbt) {

    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        if (nbt.contains(getLegacyKey())) {
            nbt.remove(getLegacyKey());
        }
        if (nbt.contains(getTranslationKey())) {
            nbt.remove(getTranslationKey());
        }
        return null;
    }
}
