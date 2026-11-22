package net.joseph.vaultfilters.attributes.companion.items;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/** Matches relics with at least {@code count} copies of a modifier (by display name). */
public class CompanionRelicModifierCountAttribute extends StringAttribute {
    protected final int count;

    public CompanionRelicModifierCountAttribute(String value, int count) {
        super(value);
        this.count = count;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        List<String> names = CompanionRelicSlotAttribute.getSlotNames(itemStack);
        return this.count >= 1 && Collections.frequency(names, this.value) >= this.count;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        List<String> names = CompanionRelicSlotAttribute.getSlotNames(itemStack);
        for (String name : new LinkedHashSet<>(names)) {
            if (name != null) {
                attributes.add(new CompanionRelicModifierCountAttribute(name, Collections.frequency(names, name)));
            }
        }
        return attributes;
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
        return "companion_relic_modifier_count";
    }

    @Override
    public void writeNBT(CompoundTag tag) {
        tag.putString(getNBTKey(), this.value);
        tag.putInt(getNBTKey() + "_count", this.count);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag tag) {
        return new CompanionRelicModifierCountAttribute(tag.getString(getNBTKey()), tag.getInt(getNBTKey() + "_count"));
    }
}
