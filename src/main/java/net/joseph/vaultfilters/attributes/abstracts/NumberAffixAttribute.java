package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.custom.EffectAvoidanceGearAttribute;
import iskallia.vault.gear.attribute.custom.EffectCloudAttribute;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import net.joseph.vaultfilters.mixin.EffectCloudAccessor;
import net.joseph.vaultfilters.mixin.EffectCloudAttributeAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class NumberAffixAttribute extends AffixAttribute {
    private static final Map<Class<?>, BiFunction<String, Number, ItemAttribute>> factories = new HashMap<>();
    protected final Number level;

    protected NumberAffixAttribute(String value, Number level) {
        super(value);
        this.level = level;
    }

    public void register(BiFunction<String, Number, ItemAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    public ItemAttribute withValue(String value, Number level) {
        return factories.getOrDefault(getClass(), (o1, o2) -> null).apply(value, level);
    }

    @Override
    public boolean hasModifier(VaultGearModifier.AffixType type, ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            for (VaultGearModifier<?> modifier : VaultGearData.read(itemStack).getModifiers(type)) {
                Number level = getLevel(modifier);
                return level != null && level.floatValue() >= this.level.floatValue();
            }
        }
        return false;
    }

    public static <T> Number getLevel(VaultGearModifier<T> modifier) {
        T value = modifier.getValue();
        if (value instanceof EffectCloudAttribute cloudAttribute) {
            EffectCloudAttribute.EffectCloud cloud = ((EffectCloudAttributeAccessor) cloudAttribute).getEffectCloud();
            String tooltip = ((EffectCloudAccessor) cloud).getTooltip();
            String level = tooltip.substring(tooltip.lastIndexOf(' ') + 1);
            level = level.isBlank() ? "I" : level;
            return level.length();
        } else if (value instanceof AbilityLevelAttribute levelAttribute) {
            return levelAttribute.getLevelChange();
        } else if (value instanceof EffectAvoidanceGearAttribute avoidanceAttribute) {
            return avoidanceAttribute.getChance() * 100;
        } else if (value instanceof Number number) {
            return number;
        }

        return null;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        for (VaultGearModifier.AffixType type : VaultGearModifier.AffixType.values()) {
            for (VaultGearModifier<?> modifier : getModifiers(itemStack, type)) {
                if (!shouldList(type, modifier)) {
                    continue;
                }

                Number level = getLevel(modifier);
                if (level == null) {
                    continue;
                }

                String name = getName(type, modifier, true);
                if (name != null) {
                    attributes.add(withValue(name, level));
                }
            }
        }
        return attributes;
    }

    @Override
    public void writeNBT(CompoundTag compoundTag) {
        super.writeNBT(compoundTag);
        String key = getSubNBTKey() + "_level";
        if (this.level instanceof Float f) {
            compoundTag.putFloat(key, f);
        } else if (this.level instanceof Double d) {
            compoundTag.putDouble(key, d);
        } else if (this.level instanceof Integer i) {
            compoundTag.putFloat(key, i);
        }
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getSubNBTKey() + "_level";
        Number level = null;

        if (compoundTag.contains(key, CompoundTag.TAG_FLOAT)) {
            level = compoundTag.getFloat(key);
        } else if (compoundTag.contains(key, CompoundTag.TAG_DOUBLE)) {
            level = compoundTag.getDouble(key);
        } else if (compoundTag.contains(key, CompoundTag.TAG_INT)) {
            level = compoundTag.getInt(key);
        }

        return withValue(compoundTag.getString(getSubNBTKey()), level);
    }
}
