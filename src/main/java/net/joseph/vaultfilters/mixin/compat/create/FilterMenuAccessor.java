package net.joseph.vaultfilters.mixin.compat.create;


import com.simibubi.create.content.logistics.filter.FilterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FilterMenu.class)
public interface FilterMenuAccessor {
    @Accessor
    boolean getRespectNBT();
    @Accessor
    public void setRespectNBT(boolean respectNBT);

    @Accessor
    boolean getBlacklist();
    @Accessor
    public void setBlacklist(boolean blacklist);



}
