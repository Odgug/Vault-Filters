package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VFNestedFilters;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FilterItem.class,remap = false)
public class MixinFilterItem {
    @Inject(method = "use",at = @At(value = "INVOKE",
            target = "Lnet/minecraftforge/network/NetworkHooks;openGui(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/MenuProvider;Ljava/util/function/Consumer;)V"
    ,shift = At.Shift.BEFORE))
    private void clearPlayerStack(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (player instanceof ServerPlayer serverPlayer) {
            VFNestedFilters.clearAll(serverPlayer);
        }
    }
}
