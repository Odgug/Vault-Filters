package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.*;
import iskallia.vault.core.card.modifier.card.CardModifier;
import iskallia.vault.core.card.modifier.card.TaskLootCardModifier;
import iskallia.vault.item.CardItem;
import iskallia.vault.task.KillEntityTask;
import iskallia.vault.task.LootChestTask;
import iskallia.vault.task.Task;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CardTaskAttribute extends StringAttribute {
    public CardTaskAttribute(String value) {
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
        if (!(modifier instanceof TaskLootCardModifier lootModifier)) {
            return null;
        }

        Task task = lootModifier.getTask();
        if (task instanceof KillEntityTask killTask) {
            KillEntityTask.Config config = killTask.getConfig();
            if (config == null || config.filter == null) {
                return null;
            }
            return config.filter.toString();
        } else if (task instanceof LootChestTask lootChestTask) {
            LootChestTask.Config config = lootChestTask.getConfig();
            if (config == null || config.filter == null) {
                return null;
            }
            return config.filter.toString();
        }
        return null;
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[] { nameFromRawPredicate(this.value) };
    }

    public static String nameFromRawPredicate(String val) {
        return switch (val) {
            case "@the_vault:mobs" -> "Mobs killed";
            case "@the_vault:fighter" -> "Dwellers killed";
            case "@the_vault:horde" -> "Horde Mobs killed";
            case "@the_vault:tank" -> "Tanks killed";
            case "@the_vault:assassin" -> "Assassins killed"; // not sure this one exists
            case "@the_vault:chest" -> "chests looted";
            case "@the_vault:wooden" -> "Wooden Chests looted";
            case "@the_vault:gilded" -> "Gilded Chests looted";
            case "@the_vault:ornate" -> "Ornate Chests looted";
            case "@the_vault:living" -> "Living Chests looted";
            default -> val;
        };
    }

    @Override
    public String getTranslationKey() {
        return "card_task";
    }
}