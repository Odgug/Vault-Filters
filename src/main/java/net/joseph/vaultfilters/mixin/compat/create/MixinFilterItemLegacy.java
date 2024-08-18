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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Mixin(value = FilterItem.class)
public class MixinFilterItemLegacy {

    @Unique
    private static Method testMethodMatchNBT;

    private static boolean testDirect(ItemStack filter, ItemStack stack, boolean matchNBT) {
        return matchNBT ? ItemHandlerHelper.canItemStacksStack(filter, stack) : ItemStack.isSame(filter, stack);
    }

    @Inject(method = "test(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getBoolean(Ljava/lang/String;)Z", ordinal = 0, shift = At.Shift.BEFORE, remap = true), cancellable = true, remap = false)
    private static void modifyLegacyTestMethod(Level world, ItemStack stack, ItemStack filter, boolean matchNBT, CallbackInfoReturnable<Boolean> cir,
                                               @Local ItemStackHandler filterItems,
                                               @Local(ordinal = 1) boolean defaults) {

        //VaultFilters.LOGGER.info("reached mixin");
        boolean matchAll = defaults ? false : filter.getTag().getBoolean("MatchAll");
        if (matchAll) {
            boolean respectNBT = defaults ? false : filter.getTag().getBoolean("RespectNBT");
            boolean blacklist = defaults ? false : filter.getTag().getBoolean("Blacklist");
            boolean isEmpty = true;
            //VaultFilters.LOGGER.info("matchAll returned true");
            if (testMethodMatchNBT == null) {
                // try to find the method
                try {
                    testMethodMatchNBT = FilterItem.class.getMethod("test", Level.class, ItemStack.class, ItemStack.class, boolean.class);
                } catch (NoSuchMethodException e) {
                    VaultFilters.LOGGER.error("[0.5.1.b-e] could not find test method", e);
                    // wrap it in unchecked exception
                    throw new IllegalStateException(e);
                }
                //VaultFilters.LOGGER.info("testMethod was defined");
            }
            for (int slot = 0; slot < filterItems.getSlots(); ++slot) {
                ItemStack stackInSlot = filterItems.getStackInSlot(slot);
                //VaultFilters.LOGGER.info("going over item");
                if (!stackInSlot.isEmpty()) {
                    isEmpty = false;
                    //boolean matches = FilterItem.test(world, stack, stackInSlot, respectNBT);
                    boolean matches;
                    try {
                        matches = (boolean) testMethodMatchNBT.invoke(null, world, stack, stackInSlot,respectNBT);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        VaultFilters.LOGGER.error("[0.5.1.b-e] could not invoke test method", e);
                        // wrap it in unchecked exception
                        throw new IllegalStateException(e);

                    }
                    //VaultFilters.LOGGER.info("item returned " + matches);
                    //VaultFilters.LOGGER.info("blacklist is " + blacklist);
                    if (!matches) {
                        cir.setReturnValue(blacklist);
                        return;
                    }
                }
            }

            if (isEmpty) {
                cir.setReturnValue(testDirect(filter, stack, matchNBT));
                return;
            } else {
                cir.setReturnValue(!blacklist);
                return;
            }
        }
    }
}
