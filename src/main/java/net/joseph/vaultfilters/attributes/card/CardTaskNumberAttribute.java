package net.joseph.vaultfilters.attributes.card;

import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.CardModifier;
import iskallia.vault.core.card.TaskLootCardModifier;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.tile.TilePredicate;
import iskallia.vault.item.CardItem;
import iskallia.vault.task.KillEntityTask;
import iskallia.vault.task.LootChestTask;
import iskallia.vault.task.ProgressConfiguredTask;
import iskallia.vault.task.Task;
import iskallia.vault.task.util.TaskProgress;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static iskallia.vault.item.CardItem.getCard;

public class CardTaskNumberAttribute extends IntAttribute {
    public CardTaskNumberAttribute(Integer value) {
        super(value);
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
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
        TaskProgress progress = ((ProgressConfiguredTask)task).getCounter().getProgress();
        return progress.getTarget().intValue();
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        final Integer value = getValue(itemStack);
        return value != null && value <= this.value;
    }

    @Override
    public String getTranslationKey() {
        return "card_task_number";
    }
}