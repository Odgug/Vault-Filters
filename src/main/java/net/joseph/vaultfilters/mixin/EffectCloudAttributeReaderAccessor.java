package net.joseph.vaultfilters.mixin;

import iskallia.vault.gear.attribute.custom.EffectCloudAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EffectCloudAttribute.Reader.class)
public interface EffectCloudAttributeReaderAccessor {
    @Accessor
    boolean getIsWhenHit();
}
