package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FilterMenu.class, remap = false)



public class MixinFilterMenu implements FilterMenuAdvancedAccessor{


    @Shadow
    boolean respectNBT;
    @Shadow
    boolean blacklist;

    @Unique
    boolean vault_filters$matchAll;
    @Inject(method = "initAndReadInventory(Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    public void initMatchALl(ItemStack filterItem, CallbackInfo ci, @Local CompoundTag tag) {
        vault_filters$matchAll = tag.getBoolean("MatchAll");
    }

    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V", ordinal = 1), cancellable = true)
    private void injectSaveData(ItemStack filterItem, CallbackInfo ci, @Local CompoundTag tag) {
        tag.putBoolean("MatchAll", vault_filters$matchAll);
        if (blacklist || respectNBT || vault_filters$matchAll) {
            ci.cancel();
        }
    }

    @Override
    public boolean vault_filters$getMatchAll() {
        return vault_filters$matchAll;
    }

    @Override
    public void vault_filters$setMatchAll(boolean matchAll) {
        this.vault_filters$matchAll = matchAll;
    }
}
