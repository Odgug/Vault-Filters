package net.joseph.vaultfilters.mixin.compat.create;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.logistics.filter.AbstractFilterScreen;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.AttributeFilterScreen;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import com.simibubi.create.content.logistics.filter.FilterScreenPacket;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Pair;
import net.joseph.vaultfilters.attributes.abstracts.VaultAttribute;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = AttributeFilterScreen.class, remap = false)
public abstract class MixinAttributeFilterScreen extends AbstractFilterScreen<AttributeFilterMenu> {
    protected MixinAttributeFilterScreen(AttributeFilterMenu menu, Inventory inv,
                                         Component title, AllGuiTextures background) {
        super(menu, inv, title, background);
    }

    // placing attr filter inside empty filter will take its attributes
//    @Inject(at = @At(value = "HEAD"), method = "referenceItemChanged")
//    public void copyCurrentAttrFilter(ItemStack stack, CallbackInfo ci) {
//        if (((AttributeFilterMenuAccessor)this.menu).getSelectedAttributes().isEmpty()
//            && FilterItemStack.of(stack) instanceof FilterItemStack.AttributeFilterItemStack attrFilter) {
//
//            var currentTests = attrFilter.attributeTests;
//            for (var test: currentTests){
//                this.vault_Filters$addAttr(test.getFirst(), test.getSecond());
//            }
//        }
//    }
    @Shadow
    private List<ItemAttribute> attributesOfItem = new ArrayList<>();
    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;stream()Ljava/util/stream/Stream;",shift = At.Shift.BEFORE), method = "referenceItemChanged")
    public void addAttributesFromFilter(ItemStack stack, CallbackInfo ci) {
        if (stack.is(AllItems.ATTRIBUTE_FILTER.get())) {
            boolean defaults = !stack.hasTag();
            ListTag attributes = defaults ? new ListTag() : stack.getTag().getList("MatchedAttributes", 10);
            if (attributes.isEmpty()) {
                return;
            }
            for (Tag inbt : attributes) {
                CompoundTag compound = (CompoundTag)inbt;
                ItemAttribute attribute = ItemAttribute.fromNBT(compound);
                if (attribute != null) {
                    attributesOfItem.add(attribute);
                }
            }
        }
    }


    // deletion logic below
    @Shadow private List<Component> selectedAttributes;
    @Shadow private Component selectedT;

    @Unique private int vault_Filters$selectedAttrIndex = 0;
    @Unique private int vault_Filters$deletionLastTick = 0;
    @Unique private int vault_Filters$deletionProgressTick = 0;
    
    // scrolling of selected attributes
    @Override public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        var res = super.mouseScrolled(pMouseX, pMouseY, pDelta);
        if (this.hoveredSlot != null && this.hoveredSlot.index == 37) {
            var idx = vault_Filters$selectedAttrIndex;
            if (pDelta < 0 && idx + 1 < this.selectedAttributes.size() - 2) {
                vault_Filters$selectedAttrIndex = idx + 1;
                var oldAt = this.selectedAttributes.get(idx + 1);
                this.selectedAttributes.set(idx + 1, Components.literal(oldAt.getString()).withStyle(ChatFormatting.GRAY));
                var newAt = this.selectedAttributes.get(idx + 2);
                this.selectedAttributes.set(idx + 2, Components.literal(newAt.getString()).withStyle(ChatFormatting.WHITE));
                vault_Filters$deletionProgressTick = 0;
            } else if (pDelta > 0 && idx > 0) {
                vault_Filters$selectedAttrIndex = idx - 1;
                if (this.selectedAttributes.size() > idx + 1) {
                    var oldAt = this.selectedAttributes.get(idx + 1);
                    this.selectedAttributes.set(idx + 1, Components.literal(oldAt.getString()).withStyle(ChatFormatting.GRAY));
                }
                var newAt = this.selectedAttributes.get(idx);
                this.selectedAttributes.set(idx, Components.literal(newAt.getString()).withStyle(ChatFormatting.WHITE));
                vault_Filters$deletionProgressTick = 0;
            }
        }
        return res;
    }

    // deletion tooltip
    @Unique private static final Component vault_Filters$delTooltipLine =  Components.literal("Hold [").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)
        .append(Components.literal("DEL").withStyle(ChatFormatting.WHITE))
        .append(Components.literal("] to remove attribute"));

    @Inject(method = "init", at = @At("TAIL"), remap = true)
    private void addDelTooltipLine(CallbackInfo ci) {
        if (this.selectedAttributes.size() > 1) {
            this.selectedAttributes.add(vault_Filters$delTooltipLine);
        }
    }
    @Inject(method = "handleAddedAttibute", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private void rmDelTooltipLine(boolean inverted, CallbackInfoReturnable<Boolean> cir) {
        this.selectedAttributes.remove(vault_Filters$delTooltipLine);
    }

    @Inject(method = "handleAddedAttibute", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
    private void addDelTooltipLine(boolean inverted, CallbackInfoReturnable<Boolean> cir) {
        this.selectedAttributes.add(vault_Filters$delTooltipLine);
    }
    @WrapOperation(method = "renderForeground", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private int renderForeground(List<Component> instance, Operation<Integer> original) {
        if (!instance.isEmpty() && instance.get(instance.size() - 1) == vault_Filters$delTooltipLine) {
            return instance.size() - 1; // don't count the deletion tooltip line
        }
        return original.call(instance);
    }

    // handle hold to delete and highlighting
    @Inject(method = "containerTick", at = @At("TAIL"), remap = true)
    private void attributeDeletionTick(CallbackInfo ci) {
        if (vault_Filters$pressDel()) {
            int idx = vault_Filters$selectedAttrIndex;
            var selectedAttrs = ((AttributeFilterMenuAccessor) this.menu).getSelectedAttributes();
            if (idx >= 0 && idx < selectedAttrs.size()) {
                Pair<ItemAttribute, Boolean> selectedAttr = selectedAttrs.get(idx);
                this.vault_Filters$removeAttr(selectedAttr.getFirst(), selectedAttr.getSecond());
                vault_Filters$selectedAttrIndex = Math.max(0, idx - 1);
                return;
            }
        }
        int idx = vault_Filters$selectedAttrIndex;
        if (idx >= 0 && idx + 1 < this.selectedAttributes.size()) {
            float progress = Math.min(Math.max(0, vault_Filters$deletionProgressTick), 20) / 20f;
            var selected = this.selectedAttributes.get(idx + 1);
            String text = selected.getString();
            int redChars = (int) (text.length() * progress);
            Component redPart = Components.literal(text.substring(0, redChars)).withStyle(ChatFormatting.RED);
            Component whitePart = Components.literal(text.substring(redChars)).withStyle(ChatFormatting.WHITE);
            this.selectedAttributes.set(idx + 1, Components.literal("").append(redPart).append(whitePart));
        }
    }

    // more hold to delete logic
    @Unique private boolean vault_Filters$pressDel() {
        var pl = Minecraft.getInstance().player;
        if (pl == null) {
            return false;
        }
        var tc = pl.tickCount;
        if (tc < vault_Filters$deletionLastTick || vault_Filters$deletionProgressTick < 0) {
            vault_Filters$deletionProgressTick = 0;
        }
        if (tc != vault_Filters$deletionLastTick) {
            if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_DELETE)) {
                vault_Filters$deletionProgressTick++;
            } else {
                vault_Filters$deletionProgressTick--;
            }
        }
        vault_Filters$deletionLastTick = tc;

        if (vault_Filters$deletionProgressTick > 20) {
            vault_Filters$deletionProgressTick = 0;
            return true;
        }
        return false;
    }

    @Unique private void vault_Filters$addAttr(ItemAttribute itemAttribute, boolean inverted) {
        CompoundTag tag = new CompoundTag();
        itemAttribute.serializeNBT(tag);
        AllPackets.getChannel()
            .sendToServer(new FilterScreenPacket(inverted ? FilterScreenPacket.Option.ADD_INVERTED_TAG : FilterScreenPacket.Option.ADD_TAG, tag));
        this.menu.appendSelectedAttribute(itemAttribute, inverted);
        if (((AttributeFilterMenuAccessor) this.menu).getSelectedAttributes().size() == 1) {
            this.selectedAttributes.set(0, this.selectedT.plainCopy().withStyle(ChatFormatting.YELLOW));
        }
        this.selectedAttributes.remove(vault_Filters$delTooltipLine);
        this.selectedAttributes.add(Components.literal("- ").append(itemAttribute.format(inverted)).withStyle(ChatFormatting.GRAY));
        this.selectedAttributes.add(vault_Filters$delTooltipLine);
    }

    // deletes all attrs and readds everything except the one that should be removed
    @Unique private void vault_Filters$removeAttr(ItemAttribute itemAttribute, boolean inverted) {
        var toRemove = Pair.of(itemAttribute, inverted);
        var currentAttributes = new ArrayList<>(((AttributeFilterMenuAccessor) this.menu).getSelectedAttributes());
        if (currentAttributes.remove(toRemove)) {
            this.menu.clearContents();
            this.contentsCleared();
            this.menu.sendClearPacket();
            for (Pair<ItemAttribute, Boolean> pair : currentAttributes) {
                this.vault_Filters$addAttr(pair.getFirst(), pair.getSecond());
            }
        }
    }
}
