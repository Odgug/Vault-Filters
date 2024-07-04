package net.joseph.vaultfilters.mixin.compat;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.logistics.filter.FilterItem;
import me.desht.modularrouters.container.ContainerModule;
import me.desht.modularrouters.item.smartfilter.SmartFilterItem;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ContainerModule.class, remap = false)
public class MixinContainerModule{
    @WrapOperation(method = "isItemOKForFilter", at = @At(value = "CONSTANT", args = "classValue=me/desht/modularrouters/item/smartfilter/SmartFilterItem"))
    private boolean injected(Object object, Operation<Boolean> original,ItemStack stack) {
        //VaultFilters.LOGGER.info(stack.getItem().getClass().getSimpleName());
        return (stack.getItem() instanceof SmartFilterItem || stack.getItem() instanceof FilterItem);
    }
}
