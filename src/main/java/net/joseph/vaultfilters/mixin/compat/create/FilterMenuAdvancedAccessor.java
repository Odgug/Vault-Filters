package net.joseph.vaultfilters.mixin.compat.create;


import com.simibubi.create.content.logistics.filter.FilterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

public interface FilterMenuAdvancedAccessor {
    public boolean getMatchAll();

    public void setMatchAll(boolean matchAny);
}
