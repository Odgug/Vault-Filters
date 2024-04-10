package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class VaultAttribute<V> implements ItemAttribute {
    protected final V value;

    protected VaultAttribute(V value) {
        this.value = value;
    }

    public abstract ItemAttribute withValue(V value);
    public abstract V getValue(ItemStack itemStack);

    public void register() {
        ItemAttribute.register(this);
    }

    @Override
    public boolean appliesTo(ItemStack stack) {
        return Objects.equals(this.value, getValue(stack));
    }

    public String getSubNBTKey() {
        return getTranslationKey();
    };

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.value};
    }

    public ItemAttribute getAttribute(ItemStack itemStack) {
        V value = getValue(itemStack);
        return value != null ? withValue(value) : null;
    }

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
