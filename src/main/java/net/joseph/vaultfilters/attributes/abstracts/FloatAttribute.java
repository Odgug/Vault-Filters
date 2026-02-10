package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class FloatAttribute extends VaultAttribute<Float> {
    private static final Map<Class<?>, Function<Float, FloatAttribute>> factories = new HashMap<>();

    protected FloatAttribute(Float value) {
        super(value);
    }

    public void register(Function<Float, FloatAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    protected abstract IntAttribute.NumComparator getComparator();

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Float value = getValue(itemStack);
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
    public FloatAttribute withValue(Float value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putFloat(getNBTKey(), this.value);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        return withValue(compoundTag.getFloat(getNBTKey()));
    }
}
