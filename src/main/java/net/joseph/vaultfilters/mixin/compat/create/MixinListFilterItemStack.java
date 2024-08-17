package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FilterItemStack.ListFilterItemStack.class, remap = false)
public class MixinListFilterItemStack {
    @Unique
    public boolean isMatchAll;
    @Shadow
    public boolean isBlacklist;
    @Shadow
    public boolean shouldRespectNBT;

    @Inject(method = "<init>", at = @At("TAIL"), cancellable = true)
    public void initMatchALl(ItemStack filter, CallbackInfo ci, @Local boolean defaults) {
        isMatchAll = defaults ? false
                : filter.getTag().getBoolean("MatchAll");
    }

    @Inject(method = "test(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Z)Z", at = @At("HEAD"), cancellable = true)
    private void modifyTestMethod(Level world, ItemStack stack, boolean matchNBT, CallbackInfoReturnable<Boolean> cir) {
        if (((FilterItemStack.ListFilterItemStack) (Object) this).containedItems.isEmpty()) {
            return;
        }

        // Injected code to handle `isMatchAll`
        if (isMatchAll) {
            boolean result = true;
            for (FilterItemStack filterItemStack : ((FilterItemStack.ListFilterItemStack) (Object) this).containedItems) {
                if (!(filterItemStack.test(world, stack, shouldRespectNBT))) {
                    result = isBlacklist;
                    break;
                }
            }
            cir.setReturnValue(result ? !isBlacklist : isBlacklist);
        }
    }
}
