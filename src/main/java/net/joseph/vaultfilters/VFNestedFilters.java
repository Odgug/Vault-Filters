package net.joseph.vaultfilters;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import oshi.util.tuples.Pair;

import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VFNestedFilters {
    public static ConcurrentHashMap<UUID, Stack<Pair<Integer, ItemStack>>> NestLog = new ConcurrentHashMap<>();

    public static Stack<Pair<Integer,ItemStack>> getPlayerStack(ServerPlayer player) {
        UUID uuid = player.getGameProfile().getId();
        Stack<Pair<Integer, ItemStack>> playerStack;
        if (NestLog.containsKey(uuid)) {
            playerStack = NestLog.get(uuid);
        } else {
            playerStack = new Stack<>();
            NestLog.put(uuid,playerStack);
        }
        return playerStack;
    }
    public static void openNextMenu(ServerPlayer player, ItemStack currentItem, ItemStack nextItem, int slot) {
        Stack<Pair<Integer, ItemStack>> playerStack = getPlayerStack(player);
        playerStack.push(new Pair<>(slot,currentItem));
        openMenu(player,nextItem);

    }
    public static void openMenu(ServerPlayer player, ItemStack itemStack ) {
        MenuProvider provider = new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return itemStack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inv, Player p) {
                if (itemStack.getItem() instanceof FilterItem filterItem) {
                    // Use the stack directly instead of player.getMainHandItem()
                    //VaultFilters.LOGGER.info(stack.getDisplayName().getString());
                    return itemStack.is(AllItems.FILTER.get())
                            ? FilterMenu.create(id, inv, itemStack)
                            : AttributeFilterMenu.create(id, inv, itemStack);
                }
                return null;
            }

        };
        //VaultFilters.LOGGER.info(stack.getDisplayName().getString());
        NetworkHooks.openGui(player, provider, buf -> buf.writeItem(itemStack));
    }
    public static void openPreviousMenu(ServerPlayer player, ItemStack currentItem) {
        Stack<Pair<Integer, ItemStack>> playerStack = getPlayerStack(player);
        if (playerStack.isEmpty()) {
            player.closeContainer();
            return;
        }
        Pair<Integer, ItemStack> entry = playerStack.pop();
        int slot = entry.getA();
        ItemStack previousItem = entry.getB();
        saveItem(previousItem,slot,currentItem);
        openMenu(player,previousItem);
    }
    public static void saveItem(ItemStack itemToModify, int slot, ItemStack itemToAdd) {
        if (!(itemToModify.is(AllItems.FILTER.get()))) {
            return;
        }
        ItemStackHandler items = FilterItem.getFilterItems(itemToModify);
        items.setStackInSlot(slot,itemToAdd);
        itemToModify.getOrCreateTag().put("Items",items.serializeNBT());
    }
    public static void popAll(ServerPlayer player, ItemStack currentItem) {
        Stack<Pair<Integer, ItemStack>> playerStack = getPlayerStack(player);
        while (!playerStack.isEmpty()) {
            Pair<Integer, ItemStack> entry = playerStack.pop();
            int slot = entry.getA();
            ItemStack previousItem = entry.getB();
            saveItem(previousItem,slot,currentItem);
            currentItem = previousItem;
        }
    }
    public static void clearAll(ServerPlayer player) {
        Stack<Pair<Integer, ItemStack>> playerStack = getPlayerStack(player);
        playerStack.clear();
    }

}
