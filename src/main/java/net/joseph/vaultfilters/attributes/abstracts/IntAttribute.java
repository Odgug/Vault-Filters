package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class IntAttribute extends VaultAttribute<Integer> {
    private static final Map<Class<?>, Function<Integer, IntAttribute>> factories = new HashMap<>();
    public enum NumComparator {
        AT_LEAST,
        AT_MOST,
        EQUAL
    }
    protected IntAttribute(Integer value) {
        super(value);
    }


    public void register(Function<Integer, IntAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }
    protected abstract NumComparator getComparator();

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Integer value = getValue(itemStack);
        if (value == null) {
            return false;
        }
        if (this.getComparator() == NumComparator.AT_LEAST) {
            return value >= this.value;
        }
        if (this.getComparator() == NumComparator.AT_MOST) {
            return value <= this.value;
        }
        if (this.getComparator() == NumComparator.EQUAL) {
            return value.equals(this.value);
        }

        return value >= this.value;

    }

    @Override
    public IntAttribute withValue(Integer value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putInt(getNBTKey(), this.value);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getNBTKey();
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
            byte legacyType = compoundTag.getTagType(getLegacyKey());
            if (legacyType == CompoundTag.TAG_STRING) {
                IntAttribute attribute = withValue(Integer.parseInt(compoundTag.getString(getLegacyKey())));
                compoundTag.putInt(key, attribute.value);
                compoundTag.remove(getLegacyKey());
                return attribute;
            } else {
                IntAttribute attribute = withValue(compoundTag.getInt(getLegacyKey()));
                compoundTag.putInt(key, attribute.value);
                compoundTag.remove(getLegacyKey());
                return attribute;
            }

        }
    }
}
