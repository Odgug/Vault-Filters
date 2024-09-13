package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.*;
import iskallia.vault.core.random.JavaRandom;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.entry.LootEntry;
import iskallia.vault.gear.attribute.VaultGearAttributeInstance;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.talent.RandomVaultModifierAttribute;
import iskallia.vault.gear.reader.IncreasedPercentageReader;
import iskallia.vault.gear.reader.VaultGearModifierReader;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.CardItem;
import iskallia.vault.skill.base.Skill;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.mixin.data.GearCardModifierAccesor;
import net.joseph.vaultfilters.mixin.data.LootCardModifierConfigAccessor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class CardModifierAttribute extends StringAttribute {
    public CardModifierAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CardItem)) {
            return null;
        }

        Card card = CardItem.getCard(itemStack);
        List<CardEntry> entries = card.getEntries();
        if (entries == null || entries.isEmpty()) {
            return null;
        }

        CardEntry entry = entries.get(0);
        if (entry == null) {
            return null;
        }

        CardModifier<?> modifier = entry.getModifier();
        if (modifier == null) {
            return null;
        }

        if (modifier instanceof GearCardModifier<?> gearModifier) {
            return gearModifier.getAttribute() == null ? null : getName(gearModifier);
        } else if (modifier instanceof TaskLootCardModifier lootModifier) {
            return getName(lootModifier);
        }
        return null;
    }

    public static List<ItemStack> getItemStacks(TaskLootCardModifier lootModifier) {
        TaskLootCardModifier.Config config = lootModifier.getConfig();
        if (config == null) {
            return null;
        }

        LootPool lootPool = ((LootCardModifierConfigAccessor)config).getLoot();
        if (lootPool == null) {
            return null;
        }

        Optional<LootEntry> optLootEntry = lootPool.getRandom();
        if (optLootEntry.isEmpty()) {
            return null;
        }

        LootEntry lootEntry = optLootEntry.get();
        return lootEntry.getStack(JavaRandom.ofNanoTime());
    }

    public static String getName(TaskLootCardModifier lootModifier) {
        List<ItemStack> itemStacks = getItemStacks(lootModifier);
        if (itemStacks == null || itemStacks.isEmpty()) {
            return null;
        }
        return new TranslatableComponent(itemStacks.get(0).getItem().getDescriptionId()).getString();
    }

    public static <T> String getName(GearCardModifier<T> cardModifier) {
        Optional<T> valueOpt = CardEntry.getForTier(((GearCardModifierAccesor) cardModifier).getValues(), 1);
        if (valueOpt.isEmpty()) {
            return null;
        }

        T valueFinal = valueOpt.get();
        VaultGearAttributeInstance<T> instance = new VaultGearAttributeInstance<>(cardModifier.getAttribute(), valueFinal);

        // Temporal card usage
        if (instance.getValue() instanceof RandomVaultModifierAttribute modifierAttribute) {
            ResourceLocation modifierLocation = modifierAttribute.getModifier();
            if (modifierLocation == null) {
                return null;
            }

            Optional<VaultModifier<?>> optModifier = VaultModifierRegistry.getOpt(modifierLocation);
            return optModifier.map(VaultModifier::getDisplayName).orElse(null);
        }

        // Get name returns blank for Ability Level Attributes
        if (instance.getValue() instanceof AbilityLevelAttribute levelAttribute) {
            String ability = levelAttribute.getAbility().equals("all_abilities")
                    ? "All Abilities"
                    : ModConfigs.ABILITIES.getAbilityById(levelAttribute.getAbility()).map(Skill::getName).orElse("");
            return  "level of "+ ability;
        }

        VaultGearModifierReader<T> reader = instance.getAttribute().getReader();
        if (reader instanceof IncreasedPercentageReader) {
            return "Increased " + reader.getModifierName();
        }
        return reader.getModifierName();
    }

    @Override
    public String getTranslationKey() {
        return "card_modifier";
    }
}