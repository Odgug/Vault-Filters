package net.joseph.vaultfilters.recipes;

import com.simibubi.create.AllItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.crafting.BookCloningRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

public class AttributeFilterCopyRecipe extends AbstractCopyRecipe{

    public AttributeFilterCopyRecipe(ResourceLocation location) {
        super(location);
    }

    @Override
    public boolean isEmptyFilter(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        return (stack.is(AllItems.ATTRIBUTE_FILTER.get()) && !stack.hasTag());
    }

    @Override
    public boolean isCopyableFilter(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        return (stack.is(AllItems.ATTRIBUTE_FILTER.get()) && stack.hasTag());
    }

    @Override
    public ItemStack getFilteredCopy(ItemStack stack, int i) {
        ItemStack itemstack2 = new ItemStack(AllItems.ATTRIBUTE_FILTER.get(), i);
        CompoundTag compoundtag = stack.getTag().copy();
        itemstack2.setTag(compoundtag);
        return itemstack2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VFRecipes.ATTRIBUTE_FILTER_COPY.get();
    }
}
