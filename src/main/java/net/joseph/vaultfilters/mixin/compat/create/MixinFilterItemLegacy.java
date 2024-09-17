package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

@Mixin(FilterItem.class)
public class MixinFilterItemLegacy {
    @Unique
    private static MethodHandle testMethodMatchNBT;
    static {
        try {
            MethodType methodType = MethodType.methodType(boolean.class, Level.class, ItemStack.class, ItemStack.class, boolean.class);
            testMethodMatchNBT = MethodHandles.lookup().findStatic(FilterItem.class, "test", methodType);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            VaultFilters.LOGGER.error("[0.5.1.b-e] could not find test method", e);
            // wrap it in unchecked exception
            throw new IllegalStateException(e);
        }
    }

    @Inject(method = "test(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z", ordinal = 0, shift = At.Shift.BEFORE, remap = true), cancellable = true, remap = false)
    private static void modifyLegacyTestMethod(Level world, ItemStack stack, ItemStack filter, boolean matchNBT, CallbackInfoReturnable<Boolean> cir,
                                               @Local ItemStackHandler filterItems, @Local(ordinal = 1) boolean defaults) {
        boolean matchAll = !defaults && filter.getOrCreateTag().getBoolean("MatchAll");
        if (!matchAll) {
            return;
        } else if (testMethodMatchNBT == null) {
            throw new IllegalStateException("[0.5.1.b-e] could not find test method");
        }

        boolean isEmpty = true;
        boolean respectNBT = filter.getTag().getBoolean("RespectNBT");
        boolean blacklist = filter.getTag().getBoolean("Blacklist");
        for (int slot = 0; slot < filterItems.getSlots(); ++slot) {
            ItemStack stackInSlot = filterItems.getStackInSlot(slot);
            if (stackInSlot.isEmpty()) {
                continue;
            }

            isEmpty = false;
            try {
                if (!(boolean) testMethodMatchNBT.invoke(world, stack, stackInSlot, respectNBT)) {
                    cir.setReturnValue(blacklist);
                    return;
                }
            } catch (Throwable e) {
                VaultFilters.LOGGER.error("[0.5.1.b-e] could not invoke test method", e);
                // wrap it in unchecked exception
                throw new IllegalStateException(e);
            }
        }

        if (isEmpty) {
            cir.setReturnValue(vault_filters$testDirect(filter, stack, matchNBT));
        } else {
            cir.setReturnValue(!blacklist);
        }
    }

    @Unique
    private static boolean vault_filters$testDirect(ItemStack filter, ItemStack stack, boolean matchNBT) {
        return matchNBT ? ItemHandlerHelper.canItemStacksStack(filter, stack) : ItemStack.isSame(filter, stack);
    }
}
