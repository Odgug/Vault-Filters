package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.custom.EffectAvoidanceGearAttribute;
import iskallia.vault.gear.attribute.custom.EffectCloudAttribute;
import iskallia.vault.gear.reader.VaultGearModifierReader;
import net.joseph.vaultfilters.DataFixers;
import net.joseph.vaultfilters.mixin.data.EffectCloudAccessor;
import net.joseph.vaultfilters.mixin.data.EffectCloudAttributeAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.Map;

public abstract class NumberAffixAttribute extends AffixAttribute {
    private static final Map<Class<?>, TriFunction<String, String, Number, ItemAttribute>> factories = new HashMap<>();
    protected final String name;
    protected final Number level;

    protected NumberAffixAttribute(String value, String name, Number level) {
        super(value);
        this.name = name;
        this.level = level;
    }

    @Override
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return getLevel(modifier) != null;
    }

    @Override
    public ItemAttribute withValue(VaultGearModifier<?> modifier) {
        String displayName = getDisplayName(modifier, getAffixType());
        String name = getName(modifier);
        Number level = getLevel(modifier);
        return withValue(displayName, name, level);
    }

    public void register(TriFunction<String, String, Number, ItemAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    public ItemAttribute withValue(String displayName, String name, Number level) {
        return factories.getOrDefault(getClass(), (o1, o2, o3) -> null).apply(displayName, name, level);
    }

    @Override
    public boolean checkModifier(VaultGearModifier<?> modifier) {
        Number level = getLevel(modifier);
        return this.level.getClass().isInstance(level)
            && level.floatValue() >= this.level.floatValue()
            && this.name.equals(getName(modifier));
    }

    public static <T> String getDisplayName(VaultGearModifier<T> modifier, VaultGearModifier.AffixType type) {
        VaultGearModifierReader<T> reader = modifier.getAttribute().getReader();
        MutableComponent displayName = reader.getDisplay(modifier, type);
        return displayName == null ? getName(modifier) : displayName.getString();
    }

    public static <T> Number getLevel(VaultGearModifier<T> modifier) {
        T value = modifier.getValue();
        if (value instanceof EffectCloudAttribute cloudAttribute) {
            EffectCloudAttribute.EffectCloud cloud = ((EffectCloudAttributeAccessor) cloudAttribute).getEffectCloud();
            String tooltip = ((EffectCloudAccessor) cloud).getTooltip();
            int index = tooltip.lastIndexOf(' ');
            if (index == -1) {
                return 1;
            }

            return switch (tooltip.substring(index + 1)) {
                case "II" -> 2;
                case "III" -> 3;
                case "IV" -> 4;
                case "V" -> 5;
                default -> 1;
            };
        } else if (value instanceof AbilityLevelAttribute levelAttribute) {
            return levelAttribute.getLevelChange();
        } else if (value instanceof EffectAvoidanceGearAttribute avoidanceAttribute) {
            return avoidanceAttribute.getChance();
        } else if (value instanceof Number number) {
            return number;
        }

        return null;
    }


    @Override
    public void writeNBT(CompoundTag compoundTag) {
        super.writeNBT(compoundTag);
        writeLevel(compoundTag, this.level);
        compoundTag.putString(getTranslationKey() + "_simple", this.name);
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
        // If it's legacy data, data fixer
        String key = getTranslationKey();
        if (compoundTag.contains(getLegacyKey(), CompoundTag.TAG_STRING) && !compoundTag.contains(key)) {
            return readLegacyNBT(compoundTag);
        }

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

        return withValue(compoundTag.getString(key), compoundTag.getString(simpleKey), level);
    }

    public ItemAttribute readLegacyNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        String simpleKey = key + "_simple";

        String displayName = compoundTag.getString(getLegacyKey());
        String name = DataFixers.getModifierName(displayName);
        Number level = DataFixers.parseLevel(name, displayName);

        compoundTag.remove(getLegacyKey());
        compoundTag.putString(key, displayName);
        compoundTag.putString(simpleKey, name);
        writeLevel(compoundTag, level);

        return withValue(displayName, name, level);
    }
}
