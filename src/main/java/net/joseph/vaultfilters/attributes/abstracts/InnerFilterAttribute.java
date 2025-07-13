package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.joseph.vaultfilters.VFTests;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class InnerFilterAttribute extends VaultAttribute<ItemStack>{
    private static final Map<Class<?>, Function<ItemStack, InnerFilterAttribute>> factories = new HashMap<>();

    protected InnerFilterAttribute(ItemStack value) {super(value);}

    public abstract List<ItemStack> getInnerItems(ItemStack stack);
    public void register(Function<ItemStack, InnerFilterAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {
        List<ItemStack> innerItems = getInnerItems(itemStack);
        if (innerItems == null) {
            return false;
        }
        for (ItemStack item : innerItems) {
            if (VFTests.basicFilterTest(item,this.value,null)) {
                return true;
            }
        }
        return false;
    }
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        if (itemStack.getItem() instanceof FilterItem) {
            attributes.add(withValue(itemStack));
        }
        return attributes;
    }
    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{this.value.getDisplayName().getString()};
    }

    @Override
    public ItemAttribute withValue(ItemStack value) {
        return factories.getOrDefault(getClass(), ignored -> null).apply(value);
    }
    @Override
    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.put(getTranslationKey(), this.value.save(new CompoundTag()));
    }

    @Override
    public ItemAttribute readNBT(CompoundTag compoundTag) {
        return withValue(ItemStack.of(compoundTag.getCompound(getTranslationKey())));
    }
    @Override
    public ItemStack getValue(ItemStack itemStack) {
        return itemStack;
    }
}
