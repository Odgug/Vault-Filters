package net.joseph.vaultfilters.mixin.compat.refinedstorage;

import net.joseph.vaultfilters.configs.MixinConfig;
import net.minecraftforge.fml.loading.LoadingModList;

public class RefinedStorageMixinPlugin extends MixinConfig {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return LoadingModList.get().getModFileById("refinedstorage") != null;
    }
}
