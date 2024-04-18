package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class BooleanAttribute extends VaultAttribute<Boolean> {
    private static final Map<Class<?>, Function<Boolean, BooleanAttribute>> factories = new HashMap<>();

    protected BooleanAttribute(Boolean value) {
        super(value);
    }

    public void register(Function<Boolean, BooleanAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();

    }

    @Override
    public BooleanAttribute withValue(Boolean value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public ItemAttribute getAttribute(ItemStack itemStack) {
        return Boolean.TRUE.equals(getValue(itemStack)) ? withValue(true) : null;
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putBoolean(getTranslationKey(), this.value);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        byte type = compoundTag.getTagType(key);
        if (type == CompoundTag.TAG_BYTE) {
            return withValue(true);
        }
        // Data Fixer
        else if (type == CompoundTag.TAG_STRING) {
            compoundTag.putBoolean(key, true);
            return withValue(true);
        } else {
            compoundTag.putBoolean(key, true);
            compoundTag.remove(getLegacyKey());
            return withValue(true);
        }
    }
}
