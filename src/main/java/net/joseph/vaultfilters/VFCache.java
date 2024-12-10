package net.joseph.vaultfilters;

import appeng.api.stacks.AEItemKey;
import iskallia.vault.item.BoosterPackItem;
import iskallia.vault.item.JewelPouchItem;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.ConcurrentHashMap;

public class VFCache {
    private static final ConcurrentHashMap<Integer, VFCache> ITEM_CACHES = new ConcurrentHashMap<>();
    private static int ticks = 0;

    private final int itemHash;
    private final ConcurrentHashMap<Integer, Boolean> filterMap;
    private int ttk; // ttk = time to kill

    public VFCache(int itemHash) {
        this.itemHash = itemHash;
        this.filterMap = new ConcurrentHashMap<>();
        this.ttk = VFServerConfig.CACHE_TTK.get();
    }

    public VFCache addFilter(int filterHash, boolean result) {
        filterMap.put(filterHash, result);
        resetTTK();
        return this;
    }

    public Boolean result(int filterHash) {
        Boolean result = filterMap.get(filterHash);
        if (result != null) {
            resetTTK();
        }
        return result;
    }

    public void resetTTK() {
        this.ttk = VFServerConfig.CACHE_TTK.get();
    }

    public void tick() {
        if (this.ttk == 0) {
            ITEM_CACHES.remove(this.itemHash);
            return;
        }
        this.ttk--;
    }

    public static boolean getOrCreateFilter(ItemStack stack, Object filterStack, Level level) {
        int itemHash = stack.hashCode();
        VFCache cache = ITEM_CACHES.get(itemHash);
        if (cache == null) {
            boolean result = VFTests.noCacheDetailedTest(stack,filterStack,level);
            ITEM_CACHES.put(itemHash, new VFCache(itemHash).addFilter(filterStack.hashCode(), result));
            return result;
        }
        int filterHash = filterStack.hashCode();
        Boolean cachedResult = cache.result(filterHash);
        if (cachedResult != null) {
            return cachedResult;
        }
        boolean result = VFTests.basicFilterTest(stack, filterStack, level);
        cache.addFilter(filterHash, result);
        return result;
    }
    public static boolean getOrCreateFilter(AEItemKey stack, Object filterStack, Level level) {
        int itemHash = stack.hashCode();
        VFCache cache = ITEM_CACHES.get(itemHash);
        if (cache == null) {
            boolean result = VFTests.noCacheDetailedTest(stack.toStack(),filterStack,level);
            ITEM_CACHES.put(itemHash, new VFCache(itemHash).addFilter(filterStack.hashCode(), result));
            return result;
        }
        int filterHash = filterStack.hashCode();
        Boolean cachedResult = cache.result(filterHash);
        if (cachedResult != null) {
            return cachedResult;
        }
        boolean result = VFTests.basicFilterTest(stack.toStack(), filterStack, level);
        cache.addFilter(filterHash, result);
        return result;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (++ticks >= 60 * 20 ) { // 60 seconds * 20 tps
                ITEM_CACHES.values().forEach(VFCache::tick);
                ticks = 0;
            }
        }
    }
}
