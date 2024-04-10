package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class IntAttribute extends VaultAttribute<Integer> {
    private static final Map<Class<?>, Function<Integer, ItemAttribute>> factories = new HashMap<>();

    protected IntAttribute(Integer value) {
        super(value);
    }

    public void register(Function<Integer, ItemAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Integer value = getValue(itemStack);
        return value != null && value >= this.value;
    }

    @Override
    public ItemAttribute withValue(Integer value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putString(getSubNBTKey(), String.valueOf(this.value));
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        return withValue(Integer.parseInt(compoundTag.getString(getSubNBTKey())));
    }
}
