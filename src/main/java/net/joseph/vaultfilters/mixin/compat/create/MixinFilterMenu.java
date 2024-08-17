package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FilterMenu.class, remap = false)



public class MixinFilterMenu implements FilterMenuAdvancedAccessor{


    @Shadow private boolean respectNBT;
    @Shadow private boolean blacklist;

    @Unique
    boolean matchAll;
    @Inject(method = "initAndReadInventory(Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"), cancellable = true)
    public void initMatchALl(ItemStack filterItem, CallbackInfo ci, @Local CompoundTag tag) {
        matchAll = tag.getBoolean("MatchAll");
    }

    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V", ordinal = 1), cancellable = true)
    private void injectSaveData(ItemStack filterItem, CallbackInfo ci, @Local CompoundTag tag) {
        tag.putBoolean("MatchAll", matchAll);
        if (blacklist || respectNBT || matchAll) {
            ci.cancel();
        }
    }

    @Override
    public boolean getMatchAll() {
        return matchAll;
    }

    @Override
    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }
}
