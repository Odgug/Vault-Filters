package net.joseph.vaultfilters.marker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.logistics.filter.FilterItem;
import net.joseph.vaultfilters.VFTests;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;

import java.util.Optional;

public class ClientEvents {
    //Sophisticated backpacks stashable code
    public static void onDrawScreen(ScreenEvent.DrawScreenEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = mc.screen;
        if (!(gui instanceof AbstractContainerScreen<?> containerGui) || mc.player == null) {
            return;
        }
        AbstractContainerMenu menu = containerGui.getMenu();
        ItemStack held = menu.getCarried();
        if (!held.isEmpty()) {
            PoseStack poseStack = event.getPoseStack();
            if (held.getItem() instanceof FilterItem) {
                for (Slot slot : menu.slots) {
                    ItemStack inSlot = slot.getItem();
                    if (!inSlot.isEmpty()) {
                        boolean matches = VFTests.checkFilter(inSlot,held,true,null);
                        renderOverlay(mc,containerGui,poseStack,slot,inSlot,matches);
                    }
                }
            }
            else {
                for (Slot slot : menu.slots) {
                    ItemStack inSlot = slot.getItem();
                    if (inSlot.getItem() instanceof FilterItem) {
                        boolean matches = VFTests.checkFilter(held,inSlot,true,null);
                        renderOverlay(mc,containerGui,poseStack,slot,inSlot,matches);
                    }

                }
            }
        }
    }
    public static void renderOverlay(Minecraft mc, AbstractContainerScreen<?> containerGui,PoseStack poseStack, Slot slot, ItemStack stack, boolean matches) {
        int x = containerGui.getGuiLeft() + slot.x;
        int y = containerGui.getGuiTop() + slot.y;
        poseStack.pushPose();
        poseStack.translate(0,0,300);
        int color = Optional.ofNullable(
                matches ? ChatFormatting.GREEN.getColor() : ChatFormatting.RED.getColor()
        ).orElse(0xFFFFFF);
        if (matches) {
            mc.font.drawShadow(poseStack,"✔",(float)x+10,(float) y+8,color);
        } else {
            mc.font.drawShadow(poseStack,"❌",(float)x+10,(float) y+8,color);
        }
        poseStack.popPose();
    }
}
