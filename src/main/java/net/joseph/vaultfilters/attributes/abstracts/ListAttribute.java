package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ListAttribute<V> extends VaultAttribute<V> {

    protected ListAttribute(V value) {
        super(value);
    }

    public abstract ArrayList<V> getValues(ItemStack itemStack);

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        ArrayList<V> values = getValues(itemStack);
        if (values == null) {
            return false;
        }
        for(V singleValue : values) {
            if (singleAppliesTo(singleValue)) {
                return true;
            }
        }
        return false;
    }

    public boolean singleAppliesTo(V singleValue) {
        return (Objects.equals(this.value,singleValue));
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        ArrayList<V> values = getValues(itemStack);
        if (values == null) {
            return attributes;
        }
        for (V singleValue : values) {
            ItemAttribute attribute = singleValue != null ? withValue(singleValue) : null;
            if (attribute != null) {
                attributes.add(attribute);
            }
        }
        return attributes;
    }
}
