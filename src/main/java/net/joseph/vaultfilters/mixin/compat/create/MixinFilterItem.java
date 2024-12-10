package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import com.simibubi.create.foundation.utility.Components;
import net.joseph.vaultfilters.ModPresence;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.attributes.abstracts.VaultAttribute;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FilterItem.class)
public abstract class MixinFilterItem {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (ModPresence.playerHasVaultFilters(player.getUUID())) {
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

        Component s2cUIAttemptNoVF = new TextComponent("This filter has Vault Filters features selected on it. " +
                "Install Vault Filters version " + VaultFilters.MOD_VERSION + " " +
                "to open the UI").withStyle(ChatFormatting.RED);

        CompoundTag tag = heldItem.getTag();
        if (tag.contains("MatchAll", CompoundTag.TAG_BYTE) && tag.getBoolean("MatchAll")) {
            player.displayClientMessage(s2cUIAttemptNoVF,false);
            cir.setReturnValue(InteractionResultHolder.pass(heldItem));
            return;
        }

        ListTag attributes = tag.getList("MatchedAttributes", CompoundTag.TAG_COMPOUND);
        for (Tag attribute : attributes) {
            if (attribute instanceof CompoundTag compound && ItemAttribute.fromNBT(compound) instanceof VaultAttribute<?>) {
                player.displayClientMessage(s2cUIAttemptNoVF,false);
                cir.setReturnValue(InteractionResultHolder.pass(heldItem));
                break;
            }
        }
    }


    @WrapOperation(method = "makeSummary", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"))
    private boolean recursiveSummary(ItemStack instance, Operation<Boolean> original, @Local List<Component> list, @Local(ordinal = 0) LocalIntRef count) {
        if (original.call(instance)) {
            return true;
        }

        if (!Screen.hasControlDown()) {
            return false;
        }

        count.set(1); // expand infinitely instead of limiting to 3 lines

        if (instance.getItem() instanceof FilterItem fi) {
            MutableComponent firstComp = Components.literal("- ").append(instance.getHoverName()).append(" ").withStyle(ChatFormatting.GRAY);
            List<Component> innerSummary = ((FilterItemInvoker) fi).invokeMakeSummary(instance);
            boolean isFst = true;
            for (Component component : innerSummary) {
                list.add((isFst ? firstComp : Components.literal("   ")).append(component).withStyle(ChatFormatting.GRAY));
                isFst = false;
            }
            return true;

        }
        return false;
    }

    @Inject(method = "makeSummary", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/filter/ItemAttribute;fromNBT(Lnet/minecraft/nbt/CompoundTag;)Lcom/simibubi/create/content/logistics/filter/ItemAttribute;", remap = false), remap = false)
    private void unlimitedAttributeFilterTooltip(ItemStack filter, CallbackInfoReturnable<List<Component>> cir, @Local LocalIntRef count){
        if (Screen.hasControlDown()) {
            count.set(1); // expand infinitely instead of limiting to 3 lines
        }
    }

    @Inject(method = "appendHoverText", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/filter/FilterItem;makeSummary(Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", remap = false))
    private void addCtrlHint(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn, CallbackInfo ci) {
        ChatFormatting color = Screen.hasControlDown() ? ChatFormatting.WHITE : ChatFormatting.GRAY;
        tooltip.add(Components.literal("Hold [").append(Components.translatable("create.tooltip.keyCtrl").withStyle(color)).append("] to show nested filters.").withStyle(ChatFormatting.DARK_GRAY));
    }

    @ModifyArg(method = "makeSummary", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), remap = false)
    private <E> E showAndAnyInListFilterTooltip(E e, @Local LocalBooleanRef blacklist,
                                             @Local(argsOnly = true) ItemStack filter) {

        boolean matchAll = filter.getTag().getBoolean("MatchAll");
        MutableComponent filterTypeComp = (MutableComponent) e;
        if (matchAll) {
            filterTypeComp.append(Components.literal(" (All)").withStyle(ChatFormatting.GOLD));
        } else {
            filterTypeComp.append(Components.literal(" (Any)").withStyle(ChatFormatting.GOLD));
        }
        return (E) filterTypeComp;
    }

}
