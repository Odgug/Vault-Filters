package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.AbstractFilterScreen;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AbstractFilterScreen.class, remap = false)
public class MixinAbstractFilterScreen {
    @Redirect(method = "containerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;closeContainer()V"))
    private void dontClose(Player instance){}
}
