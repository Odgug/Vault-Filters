package net.joseph.vaultfilters.mixin.compat.create;

import net.joseph.vaultfilters.CreateVersion;
import net.joseph.vaultfilters.configs.MixinConfig;


public class CreateMixinPlugin extends MixinConfig {

    @Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals(
            "net.joseph.vaultfilters.mixin.compat.create.MixinCreateFilteringBehaviourCreate051f")) {
            return CreateVersion.getLoadedVersion() == CreateVersion.CREATE_051F;
        }
        if (mixinClassName.equals(
            "net.joseph.vaultfilters.mixin.compat.create.MixinCreateFilteringBehaviourCreate051b")) {
            return CreateVersion.getLoadedVersion() == CreateVersion.CREATE_051B;
        }
        return true;
    }
}
