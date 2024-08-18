package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import net.joseph.vaultfilters.VFTests;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.joseph.vaultfilters.VFTests.testMethodMatchNBT;

@Mixin(value = FilterItem.class, remap = false)
public class MixinFilterItemLegacy {


    @Inject(method = "test(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z", ordinal = 1, shift = At.Shift.AFTER), cancellable = true, remap = false)
    private static void modifyLegacyTestMethod(Level world, ItemStack stack, ItemStack filter, boolean matchNBT, CallbackInfoReturnable<Boolean> cir,
                                         @Local ItemStackHandler filterItems,
                                         @Local(ordinal = 1) boolean respectNBT,
                                         @Local(ordinal = 2) boolean blacklist) {
        boolean matchAll = !filter.hasTag() ? false : filter.getTag().getBoolean("MatchAll");
        if (matchAll) {
            if (testMethodMatchNBT == null) {
                // try to find the method
                try {
                    testMethodMatchNBT = FilterItem.class.getMethod("test", Level.class, ItemStack.class, ItemStack.class, Boolean.class);
                } catch (NoSuchMethodException e) {
                    VaultFilters.LOGGER.error("[0.5.1.b-e] could not find test method: {}", e.getMessage());
                    // wrap it in unchecked exception
                    throw new IllegalStateException(e);
                }
            }
            boolean newIsEmpty = false;
            for (int slot = 0; slot < filterItems.getSlots(); ++slot) {
                ItemStack stackInSlot = filterItems.getStackInSlot(slot);
                if (!stackInSlot.isEmpty()) {
                    newIsEmpty = false;
                    //boolean matches = FilterItem.test(world, stack, stackInSlot, respectNBT);
                    boolean matches;
                    try {
                        matches = (boolean) testMethodMatchNBT.invoke(null, world, stack, stackInSlot,respectNBT);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        VaultFilters.LOGGER.error("[0.5.1.b-e] could not invoke test method: {}", e.getMessage());
                        // wrap it in unchecked exception
                        throw new IllegalStateException(e);

                    }
                    if (!matches) {
                        cir.setReturnValue(blacklist);
                    }
                }
            }

            if (newIsEmpty) {
                cir.setReturnValue(VFTests.testDirect(filter, stack, matchNBT));
            } else {
                cir.setReturnValue(!blacklist);
            }
        }
    }
}
