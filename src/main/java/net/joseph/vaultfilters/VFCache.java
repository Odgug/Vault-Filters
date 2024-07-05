package net.joseph.vaultfilters;

import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.ConcurrentHashMap;

public class VFCache {
    public static ConcurrentHashMap<Integer,VFCache> cacheMap = new ConcurrentHashMap<Integer, VFCache>();
    public static boolean getOrCreateFilter(ItemStack stack, ItemStack filterStack, Level level) {
        int itemHash = stack.hashCode();
        if (!(cacheMap.containsKey(itemHash))) {
            boolean testResult = VaultFilters.basicFilterTest(stack,filterStack,level);
            cacheMap.put(itemHash, new VFCache(filterStack, testResult));
            return testResult;
        }
        VFCache cache = cacheMap.get(itemHash);
        int filterHash = filterStack.hashCode();
        ConcurrentHashMap<Integer, Boolean> innerMap = cache.filterMap;
        if (innerMap.containsKey(filterHash)) {
            cache.resetTTK();
            return innerMap.get(filterHash);
        }
        boolean testResult = VaultFilters.basicFilterTest(stack,filterStack,level);
        cache.resetTTK();
        innerMap.put(filterHash,testResult);
        return testResult;
    }
    public int TTK = VFServerConfig.CACHE_TTK.get();
    public ConcurrentHashMap<Integer, Boolean> filterMap = new ConcurrentHashMap<Integer,Boolean>();
    public VFCache(ItemStack filterStack, boolean result) {
        int filterHash = filterStack.hashCode();
        if (!(filterMap.containsKey(filterHash))) {
            filterMap.put(filterHash,result);
        }
    }
    public void addFilter(ItemStack filterStack, boolean result) {
        int filterHash = filterStack.hashCode();
        if (!(filterMap.containsKey(filterHash))) {
            filterMap.put(filterHash,result);
        }
    }
    public void resetTTK() {
        this.TTK = VFServerConfig.CACHE_TTK.get();
    }


}
