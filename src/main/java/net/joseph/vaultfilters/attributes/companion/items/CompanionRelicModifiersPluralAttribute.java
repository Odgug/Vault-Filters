package net.joseph.vaultfilters.attributes.companion.items;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.CompanionRelicItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/** Matches relics with at least {@code count} copies of a modifier (by display name). */
public class CompanionRelicModifiersPluralAttribute extends StringAttribute {
    protected final int count;

    public CompanionRelicModifiersPluralAttribute(String value, int count) {
        super(value);
        this.count = count;
    }

    /** Display names of the relic's modifiers; unknown modifiers are null. */
    private static List<String> getModifierNames(ItemStack itemStack) {
        List<String> names = new ArrayList<>();
        if (!(itemStack.getItem() instanceof CompanionRelicItem)) {
            return names;
        }
        for (ResourceLocation id : CompanionRelicItem.getModifiers(itemStack)) {
            VaultModifier<?> modifier = VaultModifierRegistry.get(id);
            names.add(modifier == null ? null : modifier.getDisplayName());
        }
        return names;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        List<String> names = getModifierNames(itemStack);
        return this.count >= 1 && Collections.frequency(names, this.value) >= this.count;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        List<String> names = getModifierNames(itemStack);
        // Challenges fill the remaining slots, so a count option for them only repeats the relic's size
        VaultModifier<?> challengeModifier = VaultModifierRegistry.get(CompanionRelicPerfectAttribute.CHALLENGE);
        String challenge = challengeModifier == null ? null : challengeModifier.getDisplayName();
        for (String name : new LinkedHashSet<>(names)) {
            int count = Collections.frequency(names, name);
            // A count of 1 is the same as the "has modifier" option
            if (name != null && !name.equals(challenge) && count >= 2) {
                attributes.add(new CompanionRelicModifiersPluralAttribute(name, count));
            }
        }
        return attributes;
    }

    @Override
    public StringAttribute withValue(String value) {
        return new CompanionRelicModifiersPluralAttribute(value, this.count);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        // Multiple instances per item, handled by listAttributesOf
        return null;
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.count, this.value};
    }

    @Override
    public String getNBTKey() {
        return "companion_relic_modifiers_plural";
    }

    @Override
    public void writeNBT(CompoundTag tag) {
        tag.putString(getNBTKey(), this.value);
        tag.putInt(getNBTKey() + "_count", this.count);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag tag) {
        return new CompanionRelicModifiersPluralAttribute(tag.getString(getNBTKey()), tag.getInt(getNBTKey() + "_count"));
    }
}
