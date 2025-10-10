package net.joseph.vaultfilters.mixin.compat.create;


import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.*;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.joseph.vaultfilters.access.FilterMenuAdvancedAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FilterScreenPacket.class, remap = false)
public class MixinFilterScreenPacket {
    @Final @Shadow
    private FilterScreenPacket.Option option;
    @Final @Shadow
    private CompoundTag data;
    //allows match any and all in filters
    @SuppressWarnings("target")
    @ModifyVariable(method = "lambda$handle$0(Lnet/minecraftforge/network/NetworkEvent$Context;)V", at = @At(value = "STORE", ordinal = 0), name = "c", remap = false)
    private FilterMenu modifyFilterMenu(FilterMenu c) {
        // Modify or use the FilterMenu instance `c` here
        if (this.option == FilterScreenPacket.Option.ADD_TAG) {
            ((FilterMenuAdvancedAccessor) c).vault_filters$setMatchAll(true);
        } else if (this.option == FilterScreenPacket.Option.ADD_INVERTED_TAG) {
            ((FilterMenuAdvancedAccessor) c).vault_filters$setMatchAll(false);
        }
        return c;
    }
    //allows name change requests
    @Inject(method = "lambda$handle$0(Lnet/minecraftforge/network/NetworkEvent$Context;)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraftforge/network/NetworkEvent$Context;getSender()Lnet/minecraft/server/level/ServerPlayer;",shift = At.Shift.AFTER) , remap = false, cancellable = true)
    public void checkForNameChange(NetworkEvent.Context context, CallbackInfo ci, @Local ServerPlayer player) {
        if (player != null) {

            if (player.containerMenu instanceof AbstractFilterMenu c) {
                if (this.option == FilterScreenPacket.Option.UPDATE_FILTER_ITEM && this.data.contains("josephname")) {
                    ((AbstractFilterMenuAdvancedAccessor)c).vault_filters$setName(data.getString("josephname"));
                    ci.cancel();
                }
            }
        }

    }
}
