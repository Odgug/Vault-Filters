package net.joseph.vaultfilters.mixin.compat.refinedstorage.mixin;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.storage.cache.InvalidateCause;
import com.refinedmods.refinedstorage.api.util.Action;
import com.refinedmods.refinedstorage.apiimpl.network.grid.handler.ItemGridHandler;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemGridHandler.class, remap = false)
public class MixinItemGridHandler {

    /**
     * If vault filter is in exporter it will generate vault cache in the itemStack
     * but RS won't know that the stack has changed, and it will try to extract the item without the vault cache
     * that item doesn't exist in the network anymore, and it will fail to extract the item until the cache is invalidated
     * I don't want to invalidate the cache every time the item is extracted because it will cause performance issues
     * so I will invalidate the cache only if the item failed to extract
     * this is bit inconvenient for the user, because they will have to reopen the grid
     * but it's only once per cached attribute type on old items and never on new items
     * <p>
     * it would be great if we could refresh the grid screen to show the changes after the cache is invalidated
     * but I am not sure if it's possible - RS itself doesn't do it when the cache is invalidated
     * (for example if you have external storage that only works with redstone, and it's turns off when grid is open,
     * the cache is invalidated, but the grid won't show the changes until it's reopened
     * maybe we could send message to user that they need to reopen the grid
     */
    @Redirect(method = "onExtract(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/refinedmods/refinedstorage/api/network/INetwork;extractItem(Lnet/minecraft/world/item/ItemStack;ILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;",
                    ordinal = 0)
    )

    private ItemStack vaultfilters$onExtract(INetwork instance, ItemStack stack, int size, Action action) {
        ItemStack itemStack = instance.extractItem(stack, size, action);
        if (itemStack.isEmpty()) {
            instance.getItemStorageCache().invalidate(InvalidateCause.UNKNOWN);
        }
        return instance.extractItem(stack, size, action);
    }
}
