package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.foundation.gui.menu.MenuBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MenuBase.class)
public interface MenuBaseAccessor<T> {
    @Invoker("saveData")
    void vaultfilters$saveData(T contentHolder);
}
