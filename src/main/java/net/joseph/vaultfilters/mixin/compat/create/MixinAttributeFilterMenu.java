package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import com.simibubi.create.foundation.utility.Pair;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = AttributeFilterMenu.class, remap = false)
public class MixinAttributeFilterMenu {
    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void injectNameSave(ItemStack filterItem, CallbackInfo ci) {
        AttributeFilterMenu instance = (AttributeFilterMenu) (Object) (this);
        String name = ((AbstractFilterMenuAdvancedAccessor) (AbstractFilterMenu) instance).vault_filters$getName();
        if (name != null && !name.isEmpty() && !name.equals(filterItem.getHoverName().getString())) {
            filterItem.setHoverName(Component.nullToEmpty(name));
        }
        if (name.isEmpty() && filterItem.hasCustomHoverName()) {
            vault_Filters$resetHoverName(filterItem);
        }
    }
    @Unique
    public void vault_Filters$resetHoverName(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("display", 10)) { // 10 = CompoundTag type
            CompoundTag displayTag = tag.getCompound("display");
            displayTag.remove("Name"); // Remove custom name
            if (displayTag.isEmpty()) {
                tag.remove("display"); // Clean up if empty
            } else {
                tag.put("display", displayTag); // Save changes
            }

            if (tag.isEmpty()) {
                stack.setTag(null); // If whole tag is now empty, remove it
            } else {
                stack.setTag(tag); // Set updated tag
            }
        }
    }
    @Inject(method = "saveData(Lnet/minecraft/world/item/ItemStack;)V", at =
    @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setTag(Lnet/minecraft/nbt/CompoundTag;)V",shift = At.Shift.BEFORE,remap = true), cancellable = true, remap = false)
    private void fixHovernameClear(ItemStack filterItem, CallbackInfo ci) {
        if (filterItem.hasCustomHoverName()) {
            ci.cancel();
        }
    }
    @Inject(method = "initAndReadInventory(Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "TAIL"),remap = false)
    private void initName(ItemStack filterItem, CallbackInfo ci) {
        AttributeFilterMenu instance = (AttributeFilterMenu) (Object) (this);
        ((AbstractFilterMenuAdvancedAccessor) instance).vault_filters$setName(filterItem.getHoverName().getString());
    }

    // Fixes crash when client connects with new attributes
    @WrapOperation(method = "lambda$initAndReadInventory$0", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),remap = false)
    private boolean addNullCheck(List<Pair<ItemAttribute, Boolean>> instance, Object e, Operation<Boolean> original) {
        @SuppressWarnings("unchecked") // generics
        var pair = (Pair<ItemAttribute, Boolean>) e;
        if (pair.getFirst() == null) {
            return false;
        }
        return original.call(instance, e);
    }

    @Inject(method = "appendSelectedAttribute", at = @At(value = "HEAD"),remap = false, cancellable = true)
    private void addNullCheck(ItemAttribute itemAttribute, boolean inverted, CallbackInfo ci) {
        if (itemAttribute == null) {
            ci.cancel();
        }
    }
}
