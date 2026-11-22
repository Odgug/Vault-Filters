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
import java.util.List;

/** Matches a relic modifier (by display name) at a 1-based slot in the relic's modifier list. */
public class CompanionRelicSlotAttribute extends StringAttribute {
    protected final int slot;

    public CompanionRelicSlotAttribute(String value, int slot) {
        super(value);
        this.slot = slot;
    }

    /** Display names in slot order; unknown modifiers stay as null so later slots keep their position. */
    public static List<String> getSlotNames(ItemStack itemStack) {
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
        List<String> names = getSlotNames(itemStack);
        return slot >= 1 && slot <= names.size() && this.value.equals(names.get(slot - 1));
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        List<String> names = getSlotNames(itemStack);
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i) != null) {
                attributes.add(new CompanionRelicSlotAttribute(names.get(i), i + 1));
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
        return new Object[]{this.value, this.slot};
    }

    @Override
    public String getNBTKey() {
        return "companion_relic_slot";
    }

    @Override
    public void writeNBT(CompoundTag tag) {
        tag.putString(getNBTKey(), this.value);
        tag.putInt(getNBTKey() + "_slot", this.slot);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag tag) {
        return new CompanionRelicSlotAttribute(tag.getString(getNBTKey()), tag.getInt(getNBTKey() + "_slot"));
    }
}
