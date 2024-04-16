package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class DoubleAttribute extends VaultAttribute<Double> {
    private static final Map<Class<?>, Function<Double, ItemAttribute>> factories = new HashMap<>();

    protected DoubleAttribute(Double value) {
        super(value);
    }

    public void register(Function<Double, ItemAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Double value = getValue(itemStack);
        return value != null && value >= this.value;
    }

    @Override
    public ItemAttribute withValue(Double value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putDouble(getTranslationKey(), this.value);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        byte type = compoundTag.getTagType(key);
        if (type == CompoundTag.TAG_DOUBLE) {
            return withValue(compoundTag.getDouble(key));
        } else if (type == CompoundTag.TAG_STRING) {
            return withValue(Double.parseDouble(compoundTag.getString(key)));
        } else {
            return withValue(Double.parseDouble(compoundTag.getString(getLegacyKey())));
        }
    }
}
