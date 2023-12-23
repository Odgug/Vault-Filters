package net.joseph.vaultfilters.mixin;

import iskallia.vault.item.data.InscriptionData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InscriptionData.class)
public interface InscriptionDataAccessor {
    @Accessor
    float getCompletion();

    @Accessor
    int getTime();

    @Accessor
    float getInstability();
}
