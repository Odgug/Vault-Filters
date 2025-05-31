package net.joseph.vaultfilters.mixin.compat.modularrouters.mixin;

import me.desht.modularrouters.logic.filter.matchers.SimpleItemMatcher;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SimpleItemMatcher.class, remap = false)
public interface AccessorSimpleItemMatcher {

    @Accessor ItemStack getFilterStack();

}
