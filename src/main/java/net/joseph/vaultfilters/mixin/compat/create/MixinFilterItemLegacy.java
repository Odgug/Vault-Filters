package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Mixin(value = FilterItem.class)
public class MixinFilterItemLegacy {


    private static boolean testDirect(ItemStack filter, ItemStack stack, boolean matchNBT) {
        return matchNBT ? ItemHandlerHelper.canItemStacksStack(filter, stack) : ItemStack.isSame(filter, stack);
    }

    @Inject(method = "test(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z", ordinal = 1, shift = At.Shift.AFTER, remap = true), cancellable = true, remap = false)
    private static void modifyLegacyTestMethod(Level world, ItemStack stack, ItemStack filter, boolean matchNBT, CallbackInfoReturnable<Boolean> cir,
                                         @Local ItemStackHandler filterItems,
                                         @Local(ordinal = 1) boolean respectNBT,
                                         @Local(ordinal = 2) boolean blacklist) {
        VaultFilters.LOGGER.info("reached mixin");
        boolean matchAll = !filter.hasTag() ? false : filter.getTag().getBoolean("MatchAll");
        if (matchAll) {
            VaultFilters.LOGGER.info("matchAll returned true");
            boolean newIsEmpty = false;
            for (int slot = 0; slot < filterItems.getSlots(); ++slot) {
                ItemStack stackInSlot = filterItems.getStackInSlot(slot);
                if (!stackInSlot.isEmpty()) {
                    newIsEmpty = false;
                    boolean matches = FilterItem.test(world,stack,stackInSlot,respectNBT);
                    if (!matches) {
                        cir.setReturnValue(blacklist);
                    }
                }
            }

            if (newIsEmpty) {
                cir.setReturnValue(testDirect(filter, stack, matchNBT));
            } else {
                cir.setReturnValue(!blacklist);
            }
        }
    }
}
