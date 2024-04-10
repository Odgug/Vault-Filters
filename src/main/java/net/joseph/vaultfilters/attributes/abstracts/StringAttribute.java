package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class StringAttribute extends VaultAttribute<String> {
    private static final Map<Class<?>, Function<String, ItemAttribute>> factories = new HashMap<>();

    protected StringAttribute(String value) {
        super(value);
    }

    public void register(Function<String, ItemAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();

    }

    @Override
    public ItemAttribute withValue(String value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putString(getSubNBTKey(), this.value);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        return withValue(compoundTag.getString(getSubNBTKey()));
    }
}
