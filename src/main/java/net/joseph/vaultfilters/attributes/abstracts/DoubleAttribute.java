package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class DoubleAttribute extends VaultAttribute<Double> {
    private static final Map<Class<?>, Function<Double, DoubleAttribute>> factories = new HashMap<>();

    protected DoubleAttribute(Double value) {
        super(value);
    }

    public void register(Function<Double, DoubleAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    protected abstract IntAttribute.NumComparator getComparator();

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Double value = getValue(itemStack);
        if (value == null) {
            return false;
        }
        if (this.getComparator() == IntAttribute.NumComparator.AT_LEAST) {
            return value >= this.value;
        }
        if (this.getComparator() == IntAttribute.NumComparator.AT_MOST) {
            return value <= this.value;
        }
        if (this.getComparator() == IntAttribute.NumComparator.EQUAL) {
            return value.equals(this.value);
        }

        return value >= this.value;

    }

    @Override
    public DoubleAttribute withValue(Double value) {
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
        }
        // Data Fixer
        else if (type == CompoundTag.TAG_STRING) {
            DoubleAttribute attribute = withValue(Double.parseDouble(compoundTag.getString(key)));
            compoundTag.putDouble(key, attribute.value);
            return attribute;
        } else {
            DoubleAttribute attribute = withValue(Double.parseDouble(compoundTag.getString(getLegacyKey())));
            compoundTag.putDouble(key, attribute.value);
            compoundTag.remove(getLegacyKey());
            return attribute;
        }
    }
}
