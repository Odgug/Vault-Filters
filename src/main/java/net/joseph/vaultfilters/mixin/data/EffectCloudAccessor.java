package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.gear.attribute.custom.effect.EffectCloudAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EffectCloudAttribute.EffectCloud.class, remap = false)
public interface EffectCloudAccessor {
    @Accessor
    String getTooltip();
}