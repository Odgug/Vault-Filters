package net.joseph.vaultfilters.mixin.compat.create;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.content.logistics.filter.AbstractFilterScreen;
import com.simibubi.create.content.logistics.filter.FilterScreenPacket;
import com.simibubi.create.content.trains.station.NoShadowFontWrapper;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.joseph.vaultfilters.network.MenuFeaturesPacket;
import net.joseph.vaultfilters.network.VFMessages;
import net.joseph.vaultfilters.textures.VFGuiTextures;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(value = AbstractFilterScreen.class, remap = false)
public abstract class MixinAbstractFilterScreen extends AbstractSimiContainerScreen {
    @Unique
    public EditBox nameBox;
    @Unique
    public String name;
    @Shadow
    protected AllGuiTextures background;
    @Unique
    public boolean isList = true;


    public MixinAbstractFilterScreen(AbstractContainerMenu container, Inventory inv, Component title) {
        super(container, inv, title);
    }
// debug for nested filters
//    @Redirect(method = "containerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;closeContainer()V"),remap = true)
//    private void dontClose(Player instance){}

    //added label for name editing

    @Inject(at = @At(value = "TAIL"), method = "init",remap = true)
    public void addEditableText(CallbackInfo ci) {
        int x = leftPos;
        int y = topPos;
        ItemStack contentHolder = ((AbstractFilterMenu) this.menu).contentHolder;
        isList = contentHolder.is(AllItems.FILTER.get());
        String text = ((AbstractFilterMenuAdvancedAccessor)(AbstractFilterMenu)this.menu ).vault_filters$getName();
        name = text;
        int color = AllItems.FILTER.isIn(contentHolder)  ? 3158064 : 5841956;
        Consumer<String> onTextChanged;

        onTextChanged = s -> nameBox.x = nameBoxX(s, nameBox);
        nameBox = new EditBox(new NoShadowFontWrapper(font),(x + (this.background.width - 8) / 2 - this.font.width(this.title) / 2), (y + 4)
                ,background.width-20,10,Component.nullToEmpty("text"));
        nameBox.setBordered(false);
        nameBox.setMaxLength(35);
        nameBox.setTextColor(color);
        nameBox.setValue(text);
        nameBox.changeFocus(false);
        nameBox.mouseClicked(0,0,0);
        nameBox.setResponder(onTextChanged);
        nameBox.x = nameBoxX(nameBox.getValue(),nameBox);
        addRenderableWidgets(nameBox);

    }
    private int nameBoxX(String s, EditBox nameBox) {
        return leftPos + background.width / 2 - (Math.min(font.width(s), nameBox.getWidth()) + 10) / 2;
    }
    @Inject(at = @At(value = "HEAD"), method = "containerTick",remap = true)
    public void checkFocus(CallbackInfo ci) {
        if (getFocused() != nameBox) {
            nameBox.setCursorPosition(nameBox.getValue().length());
            nameBox.setHighlightPos(nameBox.getCursorPosition());
        }
    }
    @Unique
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        boolean hitEnter = getFocused() instanceof EditBox
                && (pKeyCode == InputConstants.KEY_RETURN || pKeyCode == InputConstants.KEY_NUMPADENTER);
        if (hitEnter && nameBox.isFocused()) {
            nameBox.setFocus(false);
            syncName();
            return true;
        }
        return super.keyPressed(pKeyCode,pScanCode,pModifiers);
    }
    @Unique
    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (!nameBox.isFocused() && pMouseY > topPos && pMouseY < topPos + 14 && pMouseX > leftPos
                && pMouseX < leftPos + background.width) {
            nameBox.setFocus(true);
            nameBox.setHighlightPos(0);
            setFocused(nameBox);
            return true;
        }
        return super.mouseClicked(pMouseX,pMouseY,pButton);
    }
    @Unique
    @Override
    public void onClose() {
        syncName();
        super.onClose();
    }
    @Redirect(at= @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;closeContainer()V",remap = true),method = "lambda$init$1()V")
    public void sendOnClose(LocalPlayer instance) {
        syncName();
        instance.closeContainer();
    }


    @Inject(at = @At(value = "TAIL"), method = "renderBg",remap = true)
    public void renderEdit(PoseStack ms, float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
        String text = nameBox.getValue();
        if (!nameBox.isFocused()) {
            if (isList) {
                VFGuiTextures.LIST_FILTER_EDIT_BUTTON.render(ms,nameBoxX(text,nameBox)+font.width(text)+5,topPos+1);
            } else {
                AllGuiTextures.STATION_EDIT_NAME.render(ms,nameBoxX(text,nameBox) + font.width(text) +5, topPos+1);
            }

        }
    }


    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I",remap = true),method = "renderBg",remap = true)
    public int stopNameDraw(Font instance, PoseStack pPoseStack, Component pText, float pX, float pY, int pColor) {

        return pColor;
    }
    private void syncName() {
        if (nameBox == null) {
            return;
        }
        if (!nameBox.getValue().equals(name)) {
            VFMessages.VFCHANNEL.sendToServer(new MenuFeaturesPacket(MenuFeaturesPacket.MenuAction.CHANGE_NAME,nameBox.getValue()));
        }

    }
}
//unused injection points that i dont wanna delete
//    @Inject(method = "containerTick", at =
//    @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;closeContainer()V",shift = At.Shift.BEFORE),remap = true )
//    private void updateName(CallbackInfo ci){
//        syncName();
//    }
//    @Unique
//    @Override
//    public void removed() {
//        if (nameBox == null) {
//            return;
//        }
//        syncName();
//        super.removed();
//
//    }

