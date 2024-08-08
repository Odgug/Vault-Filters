package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.joseph.vaultfilters.attributes.abstracts.Objects.Modifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class ModifierAttribute extends VaultAttribute<Modifier> {
    private static final Map<Class<?>, Function<Modifier, ModifierAttribute>> factories = new HashMap<>();

    protected ModifierAttribute(Modifier value) {
        super(value);
    }

    public void register(Function<Modifier, ModifierAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Modifier value = getValue(itemStack);
        if (value == null) {
            return false;
        }

        return this.value.getLevel().getClass().isInstance(value.getLevel())
                && value.getLevel().floatValue() >= this.value.getLevel().floatValue()
                && this.value.getNormalName().equals(value.getNormalName());
    }

    @Override
    public ModifierAttribute withValue(Modifier value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.putString(getTranslationKey(), this.value.getDisplayName());
        writeLevel(compoundTag, this.value.getLevel());
        compoundTag.putString(getTranslationKey() + "_simple", this.value.getNormalName());
    }

    public void writeLevel(CompoundTag compoundTag, Number level) {
        String levelKey = getTranslationKey() + "_level";
        if (level instanceof Float f) {
            compoundTag.putFloat(levelKey, f);
        } else if (level instanceof Double d) {
            compoundTag.putDouble(levelKey, d);
        } else if (level instanceof Integer i) {
            compoundTag.putInt(levelKey, i);
        }
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        String simpleKey = key + "_simple";
        String levelKey = key + "_level";
        Number level = null;

        byte levelType = compoundTag.getTagType(levelKey);
        if (levelType == CompoundTag.TAG_FLOAT) {
            level = compoundTag.getFloat(levelKey);
        } else if (levelType == CompoundTag.TAG_DOUBLE) {
            level = compoundTag.getDouble(levelKey);
        } else if (levelType == CompoundTag.TAG_INT) {
            level = compoundTag.getInt(levelKey);
        }

        return withValue(new Modifier(compoundTag.getString(key), compoundTag.getString(simpleKey), level));
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.value.getDisplayName()};
    }
}
