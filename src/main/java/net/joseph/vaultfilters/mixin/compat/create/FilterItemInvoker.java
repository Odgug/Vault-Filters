package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.FilterItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(value = FilterItem.class, remap = false)
public interface FilterItemInvoker {
    @Invoker
    List<Component> invokeMakeSummary(ItemStack stack);
}
