package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AttributeFilterMenu.class, remap = false)
public class MixinAttributeFilterMenu {
    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void injectNameSave(ItemStack filterItem, CallbackInfo ci) {
        AttributeFilterMenu instance = (AttributeFilterMenu) (Object) (this);
        String name = ((AbstractFilterMenuAdvancedAccessor) (AbstractFilterMenu) instance).vault_filters$getName();
        if (name != null && !name.isEmpty()) {
            filterItem.setHoverName(Component.nullToEmpty(name));
        }
    }
    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at =
    @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getOrCreateTag()Lnet/minecraft/nbt/CompoundTag;",ordinal = 1,shift = At.Shift.AFTER), cancellable = true, remap = false)
    private void fixHovernameClear(ItemStack filterItem, CallbackInfo ci) {
        if (filterItem.hasCustomHoverName()) {
            ci.cancel();
        }
    }
}
