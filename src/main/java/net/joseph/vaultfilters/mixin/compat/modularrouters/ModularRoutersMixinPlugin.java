package net.joseph.vaultfilters.mixin.compat.modularrouters;

import net.joseph.vaultfilters.configs.MixinConfig;
import net.minecraftforge.fml.loading.LoadingModList;

public class ModularRoutersMixinPlugin extends MixinConfig {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return LoadingModList.get().getModFileById("modularrouters") != null;
    }
}
