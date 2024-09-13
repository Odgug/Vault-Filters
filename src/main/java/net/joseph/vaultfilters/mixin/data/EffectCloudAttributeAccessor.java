package net.joseph.vaultfilters.mixin.data;

import iskallia.vault.gear.attribute.custom.EffectCloudAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EffectCloudAttribute.class)
public interface EffectCloudAttributeAccessor {
    @Accessor(remap = false)
    EffectCloudAttribute.EffectCloud getEffectCloud();
}
