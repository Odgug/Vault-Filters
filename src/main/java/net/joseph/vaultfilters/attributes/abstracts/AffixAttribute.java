package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.custom.EffectAvoidanceGearAttribute;
import iskallia.vault.gear.attribute.custom.EffectCloudAttribute;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.gear.reader.IncreasedPercentageReader;
import iskallia.vault.gear.reader.VaultGearModifierReader;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.Skill;
import net.joseph.vaultfilters.mixin.EffectCloudAccessor;
import net.joseph.vaultfilters.mixin.EffectCloudAttributeAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AffixAttribute extends StringAttribute {
    protected AffixAttribute(String value) {
        super(value);
    }

    public abstract boolean shouldList(VaultGearModifier.AffixType type, VaultGearModifier<?> modifier);

    public boolean appliesTo(VaultGearModifier.AffixType type, ItemStack itemStack) {
        return hasModifier(type, itemStack);
    }

    public boolean checkModifier(VaultGearModifier<?> modifier) {
        return this.value.equals(getName(modifier));
    }

    public boolean hasModifier(VaultGearModifier.AffixType type, ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            Iterable<VaultGearModifier<?>> modifiers = type == null
                    ? VaultGearData.read(itemStack).getAllModifierAffixes()
                    : VaultGearData.read(itemStack).getModifiers(type);

            for (VaultGearModifier<?> modifier : modifiers) {
                if (checkModifier(modifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> String getName(VaultGearModifier<T> modifier) {
        if (modifier.getValue() instanceof EffectCloudAttribute cloudAttribute) {
            EffectCloudAttribute.EffectCloud cloud = ((EffectCloudAttributeAccessor) cloudAttribute).getEffectCloud();
            boolean whenHit = modifier.getAttribute().getReader().getModifierName().contains("Hit");
            String tooltip = ((EffectCloudAccessor) cloud).getTooltip();
            String cloudType = (tooltip.contains(" ") ? tooltip.substring(0, tooltip.lastIndexOf(' ')) : tooltip) + " Cloud";
            return cloudType + (whenHit ? " when Hit" : "");
        }

        if (modifier.getValue() instanceof AbilityLevelAttribute levelAttribute) {
            String ability = levelAttribute.getAbility().equals("all_abilities")
                    ? "All Abilities"
                    : ModConfigs.ABILITIES.getAbilityById(levelAttribute.getAbility()).map(Skill::getName).orElse("");
            return  "level of "+ ability;
        }

        if (modifier.getValue() instanceof EffectAvoidanceGearAttribute avoidanceAttribute) {
            return avoidanceAttribute.getEffect().getDisplayName().getString() + " Avoidance";
        }

        VaultGearModifierReader<T> reader = modifier.getAttribute().getReader();
        if (reader instanceof IncreasedPercentageReader) {
            return "Increased " + reader.getModifierName();
        }
        return reader.getModifierName();
    }

    public List<VaultGearModifier<?>> getModifiers(ItemStack itemStack, VaultGearModifier.AffixType type) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            return new ArrayList<>(VaultGearData.read(itemStack).getModifiers(type));
        }
        return new ArrayList<>();
    }

    @Override
    public String getValue(ItemStack itemStack) {
        // Affix attributes can have multiple instances per item
        // So we override this here and return null as extending
        // classes do not need to implement it
        return null;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        for (VaultGearModifier.AffixType type : VaultGearModifier.AffixType.values()) {
            for (VaultGearModifier<?> modifier : getModifiers(itemStack, type)) {
                if (shouldList(type, modifier)) {
                    attributes.add(withValue(getName(modifier)));
                }
            }
        }
        return attributes;
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        if (compoundTag.contains(key, CompoundTag.TAG_STRING)) {
            return withValue(compoundTag.getString(key));
        } else {
            String affix = compoundTag.getString(getLegacyKey());
            if (affix.contains("level")) {
                affix = affix.substring(6);
            }
            
            compoundTag.putString(key, affix);
            compoundTag.remove(getLegacyKey());
            return withValue(affix);
        }
    }
}
