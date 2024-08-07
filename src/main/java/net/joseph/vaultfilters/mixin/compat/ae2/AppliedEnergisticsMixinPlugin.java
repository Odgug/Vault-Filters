package net.joseph.vaultfilters.mixin.compat.ae2;

import net.joseph.vaultfilters.configs.MixinConfig;
import net.minecraftforge.fml.loading.LoadingModList;

public class AppliedEnergisticsMixinPlugin extends MixinConfig {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
    {
        return LoadingModList.get().getModFileById("ae2") != null;
    }
}
