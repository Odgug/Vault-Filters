package net.joseph.vaultfilters.access;

import com.simibubi.create.foundation.gui.menu.GhostItemMenu;
import net.minecraft.world.item.ItemStack;

public interface GhostItemMenuAdvancedAccessor {
    void vaultfilters$setPreviousMenu(GhostItemMenu<ItemStack> menu);
    GhostItemMenu<ItemStack> vaultfilters$getPreviousMenu();
    void vaultfilters$setCurrent(boolean current);
    boolean vaultfilters$getCurrent();
}
