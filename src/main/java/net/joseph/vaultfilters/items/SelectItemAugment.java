package net.joseph.vaultfilters.items;

import me.desht.modularrouters.item.augment.AugmentItem;
import me.desht.modularrouters.item.module.ActivatorModule;
import me.desht.modularrouters.item.module.ModuleItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

public class SelectItemAugment extends AugmentItem {
    @Override
    public int getMaxAugments(ModuleItem moduleType) {
        return moduleType instanceof ActivatorModule ? 1 : 0;
    }
}
