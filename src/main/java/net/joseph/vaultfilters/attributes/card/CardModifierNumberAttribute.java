package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.*;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.gear.attribute.VaultGearAttributeInstance;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.custom.EffectAvoidanceGearAttribute;
import iskallia.vault.gear.attribute.talent.RandomVaultModifierAttribute;
import iskallia.vault.gear.reader.VaultGearModifierReader;
import iskallia.vault.item.CardItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.attributes.abstracts.ModifierAttribute;
import net.joseph.vaultfilters.attributes.abstracts.Objects.Modifier;
import net.joseph.vaultfilters.mixin.data.CardEntryAccessor;
import net.joseph.vaultfilters.mixin.data.GearCardModifierAccesor;
import net.joseph.vaultfilters.mixin.data.TaskLootCardModifierAccessor;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static iskallia.vault.item.CardItem.getCard;
import static net.joseph.vaultfilters.attributes.card.CardModifierAttribute.*;

public class CardModifierNumberAttribute extends ModifierAttribute {
    public CardModifierNumberAttribute(Modifier value) {
        super(value);
    }

    @Override
    public Modifier getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }
        Card card = getCard(itemStack);
        List<CardEntry> entries = card.getEntries();
        if (entries == null) {
            return null;
        }
        if (entries.isEmpty()) {
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
            simpleName = getName(gearModifier);
            level = getLevel(gearModifier,tier);
            displayName = getDisplayName(gearModifier,tier);
        }
        if (modifier instanceof TaskLootCardModifier lootModifier) {
            simpleName = getName(lootModifier);
            level = getLevel(lootModifier, tier);
            displayName = getDisplayName(level,simpleName);
        }

        if (level == null) {
            level = 0;
        }
        if (displayName == null & simpleName == null) {
            return null;
        }
        if (displayName == null) {
            displayName = simpleName;
        }
        if (simpleName == null) {
            simpleName = displayName;
        }
        return new Modifier(displayName,simpleName,level);
    }

    public static <T> String getDisplayName(GearCardModifier<T> cardModifier, int tier) {
        Optional<Object> valueOpt = CardEntry.getForTier(((GearCardModifierAccesor) cardModifier).getValues(), tier);
        if (valueOpt.isEmpty()) {
            return null;
        }
        Object valueFinal = valueOpt.get();
        VaultGearAttributeInstance<T> modifier = new VaultGearAttributeInstance(cardModifier.getAttribute(), valueFinal);

        VaultGearModifierReader<T> reader = modifier.getAttribute().getReader();
        MutableComponent displayName = reader.getDisplay(modifier, VaultGearModifier.AffixType.PREFIX);
        return displayName == null ? getName(cardModifier) : displayName.getString();
    }
    public static String getDisplayName(Number level, String simpleName) {
        if (simpleName == null || level == null) {
            return null;
        }
        return "+" + level.toString() + " " + simpleName;
    }

    public static <T> Number getLevel(GearCardModifier<?> cardModifier, int tier) {
        Optional<Object> valueOpt = CardEntry.getForTier(((GearCardModifierAccesor) cardModifier).getValues(), tier);
        if (valueOpt.isEmpty()) {
            return null;
        }
        Object valueFinal = valueOpt.get();
        VaultGearAttributeInstance<T> modifier = new VaultGearAttributeInstance(cardModifier.getAttribute(), valueFinal);
        T value = modifier.getValue();
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
    public String getTranslationKey() {
        return "card_modifier_number";
    }

}