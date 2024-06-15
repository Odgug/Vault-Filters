package net.joseph.vaultfilters.mixin;

import com.simibubi.create.content.logistics.filter.FilterItem;
import me.desht.modularrouters.logic.filter.Filter;
import me.desht.modularrouters.logic.filter.matchers.BulkItemMatcher;
import me.desht.modularrouters.util.SetofItemStack;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


//Create filter integration for modular routers by radimous on GitHub rizek_ on Discord, massive thanks.

@Mixin(value = BulkItemMatcher.class, remap = false)
public class MixinBulkItemMatcher {
    @Shadow @Final private SetofItemStack stacks;

    @Inject(method = "matchItem", at = @At("HEAD"), cancellable = true)
    public void createItemMatcher(ItemStack stack, Filter.Flags flags, CallbackInfoReturnable<Boolean> cir){
        for (ItemStack filter : this.stacks) {
            if (VaultFilters.checkFilter(stack, filter)) {
                cir.setReturnValue(true);
            }
        }
    }
}