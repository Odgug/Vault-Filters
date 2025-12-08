package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.*;
import iskallia.vault.core.card.modifier.card.CardModifier;
import iskallia.vault.core.card.modifier.card.GearCardModifier;
import iskallia.vault.core.card.modifier.card.TaskLootCardModifier;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.gear.attribute.VaultGearAttributeInstance;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.custom.effect.EffectAvoidanceGearAttribute;
import iskallia.vault.gear.attribute.talent.RandomVaultModifierAttribute;
import iskallia.vault.gear.reader.VaultGearModifierReader;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.ModifierAttribute;
import net.joseph.vaultfilters.attributes.abstracts.Objects.Modifier;
import net.joseph.vaultfilters.mixin.data.GearCardModifierAccesor;
import net.joseph.vaultfilters.mixin.data.TaskLootCardModifierAccessor;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CardModifierNumberAttribute extends ModifierAttribute {
    public CardModifierNumberAttribute(Modifier value) {
        super(value);
    }

    @Override
    public Modifier getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }

        Card card = CardItem.getCard(itemStack);
        List<CardEntry> entries = card.getEntries();
        if (entries == null || entries.isEmpty()) {
            return null;
        }

        CardEntry entry = entries.get(0);
        CardModifier<?> modifier = entry.getModifier();
        if (modifier == null) {
            return null;
        }

        String simpleName = null;
        String displayName = null;
        Number level = null;
        int tier = card.getTier();
        if (modifier instanceof GearCardModifier<?> gearModifier) {
            VaultGearAttribute<?> attribute = gearModifier.getAttribute();
            if (attribute == null) {
                return null;
            }

            simpleName = CardModifierAttribute.getName(gearModifier);
            level = getLevel(gearModifier, tier);
            displayName = getDisplayName(gearModifier, tier);
        } else if (modifier instanceof TaskLootCardModifier lootModifier) {
            simpleName = CardModifierAttribute.getName(lootModifier);
            level = getLevel(lootModifier, tier);
            displayName = getDisplayName(level, simpleName);
        }

        if (displayName == null && simpleName == null) {
            return null;
        }

        displayName = displayName == null ? simpleName : displayName;
        simpleName = simpleName == null ? displayName : simpleName;
        level = level == null ? 0 : level;
        return new Modifier(displayName, simpleName, level);
    }

    public static <T> String getDisplayName(GearCardModifier<T> cardModifier, int tier) {
        Optional<T> valueOpt = CardEntry.getForTier(((GearCardModifierAccesor) cardModifier).getValues(), tier);
        if (valueOpt.isEmpty()) {
            return null;
        }

        T valueFinal = valueOpt.get();
        VaultGearAttributeInstance<T> modifier = new VaultGearAttributeInstance<>(cardModifier.getAttribute(), valueFinal);
        VaultGearModifierReader<T> reader = modifier.getAttribute().getReader();
        MutableComponent displayName = reader.getDisplay(modifier, VaultGearModifier.AffixType.PREFIX);
        return displayName == null ? CardModifierAttribute.getName(cardModifier) : displayName.getString();
    }

    public static String getDisplayName(Number level, String simpleName) {
        if (simpleName == null || level == null) {
            return null;
        }
        return "+" + level + " " + simpleName;
    }

    public static <T> Number getLevel(GearCardModifier<T> cardModifier, int tier) {
        Optional<T> valueOpt = CardEntry.getForTier(((GearCardModifierAccesor) cardModifier).getValues(), tier);
        if (valueOpt.isEmpty()) {
            return null;
        }

        T value = valueOpt.get();
        if (value instanceof AbilityLevelAttribute levelAttribute) {
            return levelAttribute.getLevelChange();
        } else if (value instanceof EffectAvoidanceGearAttribute avoidanceAttribute) {
            return avoidanceAttribute.getChance();
        } else if (value instanceof RandomVaultModifierAttribute randomModAttribute) {
            return randomModAttribute.getTime();
        } else if (value instanceof Number number) {
            return number;
        }
        return null;
    }

    public static Number getLevel(TaskLootCardModifier lootModifier, int tier) {
        Map<Integer, Integer> counts = ((TaskLootCardModifierAccessor) lootModifier).getCounts();
        return counts.get(tier);
    }

    @Override
    public String getNBTKey() {
        return "card_modifier_number";
    }
}