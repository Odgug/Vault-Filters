package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.attributes.abstracts.VaultAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FilterItem.class)
public class MixinFilterItem {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (VaultFilters.PLAYERS_WITH_VAULT_FILTERS.contains(player.getUUID())) {
            return;
        }

        ItemStack heldItem = player.getItemInHand(hand);
        if (player.isShiftKeyDown()
                || hand != InteractionHand.MAIN_HAND
                || world.isClientSide
                || !(player instanceof ServerPlayer)
                || !heldItem.hasTag()) {
            return;
        }

        ListTag attributes = heldItem.getTag().getList("MatchedAttributes", CompoundTag.TAG_COMPOUND);
        for (Tag attribute : attributes) {
            if (attribute instanceof CompoundTag compound && ItemAttribute.fromNBT(compound) instanceof VaultAttribute<?>) {
                // TODO: send a message to the player
                cir.setReturnValue(InteractionResultHolder.pass(heldItem));
                break;
            }
        }
    }
}
