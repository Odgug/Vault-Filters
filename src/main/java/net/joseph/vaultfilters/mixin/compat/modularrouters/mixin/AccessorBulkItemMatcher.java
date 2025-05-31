package net.joseph.vaultfilters.mixin.compat.modularrouters.mixin;

import me.desht.modularrouters.logic.filter.matchers.BulkItemMatcher;
import me.desht.modularrouters.util.SetofItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BulkItemMatcher.class, remap = false)
public interface AccessorBulkItemMatcher {
    @Accessor SetofItemStack getStacks();
}
