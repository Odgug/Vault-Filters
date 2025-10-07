package net.joseph.vaultfilters.recipes;

import com.simibubi.create.AllItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ListFilterCopyRecipe extends AbstractCopyRecipe{

    public ListFilterCopyRecipe(ResourceLocation location) {
        super(location);
    }

    @Override
    public boolean isEmptyFilter(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        return (stack.is(AllItems.FILTER.get()) && !stack.hasTag());
    }

    @Override
    public boolean isCopyableFilter(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        return (stack.is(AllItems.FILTER.get()) && stack.hasTag());
    }

    @Override
    public ItemStack getFilteredCopy(ItemStack stack, int i) {
        ItemStack itemstack2 = new ItemStack(AllItems.FILTER.get(), i);
        CompoundTag compoundtag = stack.getTag().copy();
        itemstack2.setTag(compoundtag);
        return itemstack2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VFRecipes.ATTRIBUTE_FILTER_COPY.get();
    }
}
