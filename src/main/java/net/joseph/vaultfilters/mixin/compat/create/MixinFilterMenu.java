package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.joseph.vaultfilters.access.FilterMenuAdvancedAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FilterMenu.class, remap = false)
public class MixinFilterMenu implements FilterMenuAdvancedAccessor {
    @Shadow
    boolean respectNBT;
    @Shadow
    boolean blacklist;

    @Unique
    boolean vf$matchAll;

    @Inject(method = "initAndReadInventory(Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    public void initMatchALl(ItemStack filterItem, CallbackInfo ci, @Local CompoundTag tag) {
        vf$matchAll = tag.getBoolean("MatchAll");
    }

    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V", ordinal = 1,shift = At.Shift.AFTER), cancellable = true, remap = true)
    private void injectSaveData(ItemStack filterItem, CallbackInfo ci, @Local CompoundTag tag) {
        tag.putBoolean("MatchAll", vf$matchAll);
        //fixes hovername clear too
        if (blacklist || respectNBT || vf$matchAll || filterItem.hasCustomHoverName()) {
            ci.cancel();
        }
    }

    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/nbt/CompoundTag;putBoolean(Ljava/lang/String;Z)V", ordinal = 0,shift = At.Shift.BEFORE), cancellable = true, remap = true)
    private void injectNameSave(ItemStack filterItem, CallbackInfo ci, @Local CompoundTag tag) {
        FilterMenu instance = (FilterMenu) (Object) (this);
        String name = ((AbstractFilterMenuAdvancedAccessor) (AbstractFilterMenu) instance).vault_filters$getName();
        if (name != null && !name.isEmpty()) {
            filterItem.setHoverName(Component.nullToEmpty(name));
        }

    }

    @Override
    public boolean vault_filters$getMatchAll() {
        return vf$matchAll;
    }

    @Override
    public void vault_filters$setMatchAll(boolean matchAll) {
        this.vf$matchAll = matchAll;
    }
}
