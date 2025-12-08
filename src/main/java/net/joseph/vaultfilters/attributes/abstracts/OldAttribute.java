package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class OldAttribute extends VaultAttribute<Object> {
    protected OldAttribute(Object value) {
        super(value);
    }


    public void register(Function<Object, OldAttribute> factory) {
        super.register();
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
        return new ArrayList<>();
    }

    @Override
    public void writeNBT(CompoundTag nbt) {}

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        nbt.remove(getLegacyKey());
        nbt.remove(getNBTKey());
        return null;
    }
}
