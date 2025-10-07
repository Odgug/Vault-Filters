package net.joseph.vaultfilters.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public abstract class AbstractCopyRecipe extends CustomRecipe {
    public AbstractCopyRecipe(ResourceLocation location) {
        super(location);
    }
    public abstract boolean isEmptyFilter(ItemStack stack);
    public abstract boolean isCopyableFilter(ItemStack stack);
    public abstract ItemStack getFilteredCopy(ItemStack stack, int i);
    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        int i = 0;
        ItemStack toCopy = ItemStack.EMPTY;

        for(int j = 0; j < pInv.getContainerSize(); ++j) {
            ItemStack itemstack = pInv.getItem(j);
            if (!itemstack.isEmpty()) {
                if (isCopyableFilter(itemstack)) {
                    if (!toCopy.isEmpty()) {
                        return false;
                    }

                    toCopy = itemstack;
                } else {
                    if (!isEmptyFilter(itemstack)) {
                        return false;
                    }

                    ++i;
                }
            }
        }

        return !toCopy.isEmpty() && isCopyableFilter(toCopy) && i > 0;
    }
    @Override
    public ItemStack assemble(CraftingContainer pInv) {
        int i = 0;
        ItemStack toCopy = ItemStack.EMPTY;

        for(int j = 0; j < pInv.getContainerSize(); ++j) {
            ItemStack itemstack = pInv.getItem(j);
            if (!itemstack.isEmpty()) {
                if (isCopyableFilter(itemstack)) {
                    if (!toCopy.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    toCopy = itemstack;
                } else {
                    if (!isEmptyFilter(itemstack)) {
                        return ItemStack.EMPTY;
                    }

                    ++i;
                }
            }
        }
        if (!toCopy.isEmpty() && isCopyableFilter(toCopy) && i > 0) {
            return getFilteredCopy(toCopy,i);
        } else {
            return ItemStack.EMPTY;
        }
    }
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer pInv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(pInv.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = pInv.getItem(i);
            if (itemstack.hasContainerItem()) {
                nonnulllist.set(i, itemstack.getContainerItem());
            } else if (isCopyableFilter(itemstack)) {
                ItemStack itemstack1 = itemstack.copy();
                itemstack1.setCount(1);
                nonnulllist.set(i, itemstack1);
                break;
            }
        }

        return nonnulllist;
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 2 && pHeight >= 2;
    }
}
