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

    /**
     * @return the {@link VaultGearModifier.AffixType} that should be gone over
     * input null to go over everything
     */
    public abstract VaultGearModifier.AffixType getAffixType();

    /**
     * runs once for every {@link VaultGearModifier}
     * @return if an attribute should be made for it
     */
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return true;
    }

    /**
     * runs once for every {@link VaultGearModifier} that should have an affix based on it
     * @return an attribute based on the affix
     */
    public ItemAttribute withValue(VaultGearModifier<?> modifier) {
        String name = getName(modifier);
        return name.isBlank() ? null : withValue(name);
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        return hasModifier(getAffixType(), itemStack);
    }

    /**
     * runs once for every modifier
     * @return if the current attribute applies to it
     */
    public boolean checkModifier(VaultGearModifier<?> modifier) {
        return this.value.equals(getName(modifier));
    }

    public boolean hasModifier(VaultGearModifier.AffixType type, ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            Iterable<VaultGearModifier<?>> modifiers = getModifiers(itemStack,type);
            for (VaultGearModifier<?> modifier : modifiers) {
                if (checkModifier(modifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
    * @return simple name, not including number
    */
    public static <T> String getName(VaultGearModifier<T> modifier) {
        // Cloud Attributes do not return the type of cloud with getName
        if (modifier.getValue() instanceof EffectCloudAttribute cloudAttribute) {
            EffectCloudAttribute.EffectCloud cloud = ((EffectCloudAttributeAccessor) cloudAttribute).getEffectCloud();
            boolean whenHit = modifier.getAttribute().getReader().getModifierName().contains("Hit");
            String tooltip = ((EffectCloudAccessor) cloud).getTooltip();
            // Clouds can be formatted like "Fear Cloud" or "Fear II Cloud", this ensures that the level is **not** included
            String cloudType = (tooltip.contains(" ") ? tooltip.substring(0, tooltip.lastIndexOf(' ')) : tooltip) + " Cloud";
            return cloudType + (whenHit ? " when Hit" : "");
        }

        // Get name returns blank for Ability Level Attributes
        if (modifier.getValue() instanceof AbilityLevelAttribute levelAttribute) {
            String ability = levelAttribute.getAbility().equals("all_abilities")
                    ? "All Abilities"
                    : ModConfigs.ABILITIES.getAbilityById(levelAttribute.getAbility()).map(Skill::getName).orElse("");
            return  "level of "+ ability;
        }

        // Get name returns blank for Effect Avoidance Attributes as well
        if (modifier.getValue() instanceof EffectAvoidanceGearAttribute avoidanceAttribute) {
            return avoidanceAttribute.getEffect().getDisplayName().getString() + " Avoidance";
        }

        VaultGearModifierReader<T> reader = modifier.getAttribute().getReader();
        if (reader instanceof IncreasedPercentageReader) {
            return "Increased " + reader.getModifierName();
        }
        return reader.getModifierName();
    }

    /**
     * @return {@link Iterable<VaultGearModifier>} with all item modifiers with the specified type
     * null will return all types
     */
    public Iterable<VaultGearModifier<?>> getModifiers(ItemStack itemStack, VaultGearModifier.AffixType type) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            return type == null
                    ? VaultGearData.read(itemStack).getAllModifierAffixes()
                    : VaultGearData.read(itemStack).getModifiers(type);
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
        for (VaultGearModifier<?> modifier : getModifiers(itemStack, getAffixType())) {
            if (shouldList(modifier)) {
                ItemAttribute itemAtt = withValue(modifier);
                if (itemAtt != null) {attributes.add(itemAtt);}
            }
        }
        return attributes;
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        String key = getTranslationKey();
        if (compoundTag.contains(key, CompoundTag.TAG_STRING)) {
            return withValue(compoundTag.getString(key));
        }
        // Data Fixer
        else {
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
