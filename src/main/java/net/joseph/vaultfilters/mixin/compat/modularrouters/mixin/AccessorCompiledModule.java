package net.joseph.vaultfilters.mixin.compat.modularrouters.mixin;

import me.desht.modularrouters.logic.compiled.CompiledModule;
import me.desht.modularrouters.logic.filter.Filter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CompiledModule.class, remap = false)
public interface AccessorCompiledModule {
    @Accessor Filter getFilter();
}
