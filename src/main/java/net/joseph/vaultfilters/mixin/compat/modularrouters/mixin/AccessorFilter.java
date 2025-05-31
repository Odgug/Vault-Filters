package net.joseph.vaultfilters.mixin.compat.modularrouters.mixin;

import me.desht.modularrouters.logic.filter.Filter;
import me.desht.modularrouters.logic.filter.matchers.IItemMatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = Filter.class, remap = false)
public interface AccessorFilter {
    @Accessor List<IItemMatcher> getMatchers();
}
