package net.joseph.vaultfilters.mixin.compat.laserio;

import net.joseph.vaultfilters.configs.MixinConfig;
import net.minecraftforge.fml.loading.LoadingModList;

public class LaserIOMixinPlugin extends MixinConfig {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return LoadingModList.get().getModFileById("laserio") != null;
    }
}
