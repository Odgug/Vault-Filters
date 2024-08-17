package net.joseph.vaultfilters.mixin.compat.create;


import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraftforge.network.NetworkEvent.Context;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FilterScreenPacket.class, remap = false)
public class MixinFilterScreenPacket {
    @Final
    @Shadow
    private FilterScreenPacket.Option option;


    @ModifyVariable(method = "lambda$handle$0", at = @At(value = "STORE", ordinal = 0), name = "c")
    private FilterMenu modifyFilterMenu(FilterMenu c) {
        // Modify or use the FilterMenu instance `c` here
        if (this.option == FilterScreenPacket.Option.ADD_TAG) {
            ((FilterMenuAdvancedAccessor)c).setMatchAll(true);
        }
        if (this.option == FilterScreenPacket.Option.ADD_INVERTED_TAG) {
            ((FilterMenuAdvancedAccessor)c).setMatchAll(false);
        }
        return c;
    }


}
