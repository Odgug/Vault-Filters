package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The base abstract class for all Vault Attributes
 * By default every {@link ItemStack} only has one instance of the {@link VaultAttribute}
 * Which can be fetched and automatically listed by {@link VaultAttribute#getValue(ItemStack)}
 *
 * @param <V> The type of value (e.g. {@link String}) tracked by the {@link VaultAttribute}
 * @author JustAHuman
 */
public abstract class VaultAttribute<V> implements ItemAttribute {
    protected final V value;

    protected VaultAttribute(V value) {
        this.value = value;
    }

    /**
     * Used for dynamic constructors, every abstract
     * class / generic class type should have a
     * factory map for this method see
     * {@link StringAttribute#withValue(String)}
     * for an example implementation
     */
    public abstract ItemAttribute withValue(V value);

    /**
     * @return the matching value from the {@link ItemStack}
     * or null if the {@link ItemAttribute} does not apply
     */
    public abstract V getValue(ItemStack itemStack);

    public void register() {
        ItemAttribute.register(this);
    }

    /**
     * The default comparison, extending classes should
     * {@link Override} when an exact match is not needed
     * @return If the {@link ItemStack} matches values exactly
     */
    @Override
    public boolean appliesTo(ItemStack itemStack) {
        return Objects.equals(this.value, getValue(itemStack));
    }

    /**
     * With the API overhaul we standardized
     * the nbt key, any classes that didn't follow
     * the new standard should {@link Override} and
     * return the old key used.
     * @return The legacy key, defaults to the new key
     */
    public String getLegacyKey() {
        return getTranslationKey();
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.value};
    }

    public ItemAttribute getAttribute(ItemStack itemStack) {
        V value = getValue(itemStack);
        return value != null ? withValue(value) : null;
    }

    /**
     * @return a {@link List} with a single {@link ItemAttribute} if
     * applicable, any extending classes that an {@link ItemStack}
     * can have multiple instances of should {@link Override} this method
     */
    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        ItemAttribute attribute = getAttribute(itemStack);
        if (attribute != null) {
            attributes.add(attribute);
        }
        return attributes;
    }
}
