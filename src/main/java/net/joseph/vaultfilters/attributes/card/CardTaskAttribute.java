package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.*;
import iskallia.vault.core.random.JavaRandom;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.tile.TilePredicate;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.entry.LootEntry;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.gear.attribute.VaultGearAttributeInstance;
import iskallia.vault.gear.attribute.ability.AbilityLevelAttribute;
import iskallia.vault.gear.attribute.talent.RandomVaultModifierAttribute;
import iskallia.vault.gear.reader.IncreasedPercentageReader;
import iskallia.vault.gear.reader.VaultGearModifierReader;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.CardItem;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.task.KillEntityTask;
import iskallia.vault.task.LootChestTask;
import iskallia.vault.task.Task;
import iskallia.vault.util.GroupUtils;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.mixin.data.GearCardModifierAccesor;
import net.joseph.vaultfilters.mixin.data.LootCardModifierConfigAccessor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static iskallia.vault.item.CardItem.getCard;

public class CardTaskAttribute extends StringAttribute {
    public CardTaskAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
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
        if (entry == null) {
            return null;
        }
        CardModifier<?> modifier = entry.getModifier();
        if (modifier == null) {
            return null;
        }
        if (!(modifier instanceof TaskLootCardModifier lootModifier)) {
            return null;
        }
        Task task = lootModifier.getTask();
        if (task == null) {
            return null;
        }
        if (task instanceof KillEntityTask killTask) {
            KillEntityTask.Config config = killTask.getConfig();
            if (config == null) {
                return null;
            }
            EntityPredicate filter = config.filter;
            if (filter == null) {
                return null;
            }
            return filter.toString();
        }
        if (task instanceof LootChestTask lootChestTask) {
            LootChestTask.Config config = lootChestTask.getConfig();
            if (config == null) {
                return null;
            }
            TilePredicate filter = config.filter;
            if (filter == null) {
                return null;
            }
            return filter.toString();
        }
        return null;
    }

    @Override
    public Object[] getTranslationParameters() {

        return new Object[]{nameFromRawPredicate(this.value)};
    }
    public static String nameFromRawPredicate(String val) {
        switch (val) {
            case "@the_vault:mobs":
                return  "Mobs killed";
            case "@the_vault:fighter":
                return  "Dwellers killed";
            case "@the_vault:horde":
                return  "Horde Mobs killed";
            case "@the_vault:tank":
                return  "Tanks killed";
            case "@the_vault:assassin":
                //not sure this one exists
                return  "Assassins killed";
            case "@the_vault:chest":
                return  "chests looted";
            case "@the_vault:wooden":
                return  "Wooden Chests looted";
            case "@the_vault:gilded":
                return  "Gilded Chests looted";
            case "@the_vault:ornate":
                return  "Ornate Chests looted";
            case "@the_vault:living":
                return  "Living Chests looted";
        }
        return val;
    }


    @Override
    public String getTranslationKey() {
        return "card_task";
    }


}