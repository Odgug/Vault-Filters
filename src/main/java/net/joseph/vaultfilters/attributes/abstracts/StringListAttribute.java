package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class StringListAttribute extends ListAttribute<String> {
    private static final Map<Class<?>, Function<String, StringListAttribute>> factories = new HashMap<>();

    protected StringListAttribute(String value) {
        super(value);
    }

    public void register(Function<String, StringListAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    @Override
    public StringListAttribute withValue(String value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putString(getTranslationKey(), this.value);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        byte type = compoundTag.getTagType(key);
        if (type == CompoundTag.TAG_STRING) {
            return withValue(compoundTag.getString(key));
        }
        // Data Fixer
        else {
            StringListAttribute attribute = withValue(compoundTag.getString(getLegacyKey()));
            compoundTag.putString(key, attribute.value);
            compoundTag.remove(getLegacyKey());
            return attribute;
        }
    }
}
