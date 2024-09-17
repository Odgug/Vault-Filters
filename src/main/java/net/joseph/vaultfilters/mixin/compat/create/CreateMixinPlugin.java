package net.joseph.vaultfilters.mixin.compat.create;

import net.joseph.vaultfilters.CreateVersion;
import net.joseph.vaultfilters.configs.MixinConfig;


public class CreateMixinPlugin extends MixinConfig {
    public static final String NAME_PREFIX = "net.joseph.vaultfilters.mixin.compat.create.";

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {
            case NAME_PREFIX + "MixinCreateFilteringBehaviour",
                 NAME_PREFIX + "MixinListFilterItemStack" -> CreateVersion.getLoadedVersion() == CreateVersion.CREATE_051F;
            case NAME_PREFIX + "MixinCreateFilteringBehaviourLegacy",
                 NAME_PREFIX + "MixinFilterItemLegacy" -> CreateVersion.getLoadedVersion() == CreateVersion.LEGACY;
            default -> true;
        };
    }
}
