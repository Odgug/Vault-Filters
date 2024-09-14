package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.gear.attribute.custom.EffectCloudAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EffectCloudAttribute.EffectCloud.class)
public interface EffectCloudAccessor {
    @Accessor
    String getTooltip();
}