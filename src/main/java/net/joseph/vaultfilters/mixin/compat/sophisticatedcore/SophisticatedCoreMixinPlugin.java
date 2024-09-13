package net.joseph.vaultfilters.mixin.compat.sophisticatedcore;

import net.joseph.vaultfilters.configs.MixinConfig;
import net.minecraftforge.fml.loading.LoadingModList;

public class SophisticatedCoreMixinPlugin extends MixinConfig {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return LoadingModList.get().getModFileById("sophisticatedcore") != null;
    }
}
