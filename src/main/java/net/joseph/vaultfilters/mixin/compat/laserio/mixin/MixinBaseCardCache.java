package net.joseph.vaultfilters.mixin.compat.laserio.mixin;

import com.direwolf20.laserio.common.items.filters.FilterMod;
import com.direwolf20.laserio.common.items.filters.FilterNBT;
import com.direwolf20.laserio.common.items.filters.FilterTag;
import com.direwolf20.laserio.util.BaseCardCache;
import com.direwolf20.laserio.util.ItemStackKey;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VFTests;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;


@Mixin(value = BaseCardCache.class, remap = false)
public class MixinBaseCardCache {

    @Shadow @Final public List<ItemStack> filteredItems;

    @Shadow @Final public ItemStack filterCard;

    @Shadow @Final public Map<ItemStackKey, Boolean> filterCache;

    @Shadow @Final public boolean isCompareNBT;

    @Shadow @Final public boolean isAllowList;

    @Inject(method = "isStackValidForCard(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), remap = false, cancellable = true)
    private void checkCreateFilterForCard(ItemStack testStack, CallbackInfoReturnable<Boolean> cir) {
        if(!VFServerConfig.LASERIO_COMPAT.get()) {
            return;
        }

        if(this.filterCard.isEmpty()) {
            cir.setReturnValue(true);
        }

        //Ignore non-basic Filter Cards
        if(!(this.filterCard.getItem() instanceof FilterMod || this.filterCard.getItem() instanceof FilterTag || this.filterCard.getItem() instanceof FilterNBT)) {
            ItemStackKey key = new ItemStackKey(testStack, this.isCompareNBT);

            if (this.filterCache.containsKey(key)) {
                cir.setReturnValue(this.filterCache.get(key));
            }

            for(ItemStack stack : this.filteredItems) {
                if(stack.getItem() instanceof FilterItem) {
                    if(VFTests.checkFilter(key.getStack(), stack, true, null)) {
                        this.filterCache.put(key, this.isAllowList);
                        cir.setReturnValue(this.isAllowList);
                    }
                    else {
                        if (key.equals(new ItemStackKey(stack, this.isCompareNBT))) {
                            this.filterCache.put(key, this.isAllowList);
                            cir.setReturnValue(this.isAllowList);
                        }
                    }
                }
            }
        }
    }
}
