package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = FilterItemStack.ListFilterItemStack.class, remap = false)
public class MixinListFilterItemStack {
    @Unique
    public boolean vault_filters$isMatchAll;
    @Shadow
    public boolean isBlacklist;
    @Shadow
    public boolean shouldRespectNBT;

    @Shadow public List<FilterItemStack> containedItems;

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void initMatchALl(ItemStack filter, CallbackInfo ci, @Local boolean defaults) {
        vault_filters$isMatchAll = !defaults && filter.getTag().getBoolean("MatchAll");
    }

    @Inject(method = "test(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Z)Z", at = @At("HEAD"), cancellable = true)
    private void modifyTestMethod(Level world, ItemStack stack, boolean matchNBT, CallbackInfoReturnable<Boolean> cir) {
        if (this.containedItems.isEmpty()) {
            return;
        }

        // Injected code to handle `isMatchAll`
        if (vault_filters$isMatchAll) {
            for (FilterItemStack filterItemStack : this.containedItems) {
                if (!(filterItemStack.test(world, stack, shouldRespectNBT))) {
                    cir.setReturnValue(isBlacklist);
                    return;
                }
            }
            cir.setReturnValue(!isBlacklist);
        }
    }
}
