package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.custom.EffectCloudAttribute;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.gear.reader.VaultGearModifierReader;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.Skill;
import it.unimi.dsi.fastutil.Pair;
import net.joseph.vaultfilters.mixin.EffectCloudAccessor;
import net.joseph.vaultfilters.mixin.EffectCloudAttributeAccessor;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AffixAttribute extends StringAttribute {
    protected final Float level;
    protected final String basicName;

    protected AffixAttribute(String value) {
        super(value);

        Pair<String, Float> pieces = separateName(value);
        this.basicName = pieces.left();
        this.level = pieces.right();
    }

    public abstract boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier, boolean includeLevel);

    public boolean appliesTo(ItemStack itemStack, VaultGearModifier.AffixType type, boolean includeLevel) {
        return hasModifier(itemStack, type, includeLevel);
    }

    public boolean hasModifier(ItemStack itemStack, VaultGearModifier.AffixType type, boolean includeLevel) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            for (VaultGearModifier<?> modifier : VaultGearData.read(itemStack).getModifiers(type)) {
                String name = getName(type, modifier, includeLevel);
                Pair<String, Float> pieces = separateName(name);
                return includeLevel
                    ? this.basicName.equals(pieces.left()) && this.level <= pieces.right()
                    : this.value.equals(name);
            }
        }
        return false;
    }

    public static <T> String getName(VaultGearModifier.AffixType type,  VaultGearModifier<T> modifier, boolean includeLevel) {
        if (modifier.getValue() instanceof EffectCloudAttribute cloudAttribute) {
            EffectCloudAttribute.EffectCloud cloud = ((EffectCloudAttributeAccessor) cloudAttribute).getEffectCloud();
            boolean whenHit = modifier.getAttribute().getReader().getModifierName().contains("Hit");
            String tooltip = ((EffectCloudAccessor) cloud).getTooltip();
            String cloudType = tooltip.substring(0, tooltip.lastIndexOf(' '));
            String level = tooltip.substring(tooltip.lastIndexOf(' ') + 1);
            level = level.isBlank() ? "I" : level;
            return cloudType + (includeLevel ? level : "") + (whenHit ? " when Hit" : "");
        }

        if (modifier.getValue() instanceof AbilityLevelAttribute levelAttribute) {
            String ability = levelAttribute.getAbility().equals("all_abilities")
                    ? "All Abilities"
                    : ModConfigs.ABILITIES.getAbilityById(levelAttribute.getAbility()).map(Skill::getName).orElse("");
            int levelChange = levelAttribute.getLevelChange();
            return (includeLevel ? "+" + levelChange : "Adds") + " to level of " + ability;
        }

        VaultGearModifierReader<T> reader = modifier.getAttribute().getReader();
        MutableComponent levelDisplay = reader.getDisplay(modifier, type);
        return includeLevel && levelDisplay != null
                ? levelDisplay.getString()
                : reader.getModifierName();
    }

    public static Pair<String, Float> separateName(String name) {
        int startIndex = -1;
        int endIndex = 0;

        int i = 0;
        for (char character : name.toCharArray()) {
            if (Character.isDigit(character)) {
                if (startIndex == -1) {
                    startIndex = i;
                }
                endIndex = i;
            } else if (character == ' ') {
                break;
            }
            i++;
        }

        if (startIndex == -1) {
            return Pair.of(name, 0F);
        }

        String basicName = name.substring(0, startIndex) + name.substring(endIndex + 1);
        float level = Float.parseFloat(name.substring(startIndex, endIndex));
        return Pair.of(basicName, level);
    }

    public List<VaultGearModifier<?>> getModifiers(ItemStack itemStack, VaultGearModifier.AffixType type) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            return new ArrayList<>(VaultGearData.read(itemStack).getModifiers(type));
        }
        return new ArrayList<>();
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return null;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        for (VaultGearModifier.AffixType type : VaultGearModifier.AffixType.values()) {
            for (VaultGearModifier<?> modifier : getModifiers(itemStack, type)) {
                if (shouldList(type, modifier, true)) {
                    attributes.add(withValue(getName(type, modifier, true)));
                }

                if (shouldList(type, modifier, false)) {
                    attributes.add(withValue(getName(type, modifier, false)));
                }
            }
        }
        return attributes;
    }
}
