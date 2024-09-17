package net.joseph.vaultfilters.mixin.compat.tomsstorage;

import net.joseph.vaultfilters.configs.MixinConfig;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraftforge.fml.loading.LoadingModList;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;

public class TomsStorageMixinPlugin extends MixinConfig {
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return LoadingModList.get().getModFileById("toms_storage") != null;
    }
}
