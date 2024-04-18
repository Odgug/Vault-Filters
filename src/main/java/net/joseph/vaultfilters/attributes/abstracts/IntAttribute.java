package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class IntAttribute extends VaultAttribute<Integer> {
    private static final Map<Class<?>, Function<Integer, IntAttribute>> factories = new HashMap<>();

    protected IntAttribute(Integer value) {
        super(value);
    }

    public void register(Function<Integer, IntAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Integer value = getValue(itemStack);
        return value != null && value >= this.value;
    }

    @Override
    public IntAttribute withValue(Integer value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putInt(getLegacyKey(), this.value);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        byte type = compoundTag.getTagType(key);
        if (type == CompoundTag.TAG_INT) {
            return withValue(compoundTag.getInt(key));
        }
        // Data Fixer
        else if (type == CompoundTag.TAG_STRING) {
            IntAttribute attribute = withValue(Integer.parseInt(compoundTag.getString(key)));
            compoundTag.putInt(key, attribute.value);
            return attribute;
        } else {
            IntAttribute attribute = withValue(Integer.parseInt(compoundTag.getString(getLegacyKey())));
            compoundTag.putInt(key, attribute.value);
            compoundTag.remove(getLegacyKey());
            return attribute;
        }
    }
}
