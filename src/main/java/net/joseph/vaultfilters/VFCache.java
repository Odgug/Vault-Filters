package net.joseph.vaultfilters;
import appeng.api.stacks.AEItemKey;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class VFCache {
    //First object is either ItemStack or ae2Key
    //Second object is either a filter itemStack or a FilterStack
    private static final Cache<Object, ConcurrentHashMap<Object, Boolean>> ITEM_OUTER_CACHE = CacheBuilder.newBuilder()
            .weakKeys()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();
    public static ItemStack getStackFromObject(Object object) {
        if (object instanceof ItemStack stack) {
            return stack;
        }
        if (object instanceof AEItemKey key) {
            return key.toStack();
        }
        VaultFilters.LOGGER.warn("UNDETECTED Object type");
        return ItemStack.EMPTY;
    }
    public static boolean getOrCreateFilter(Object stack, Object filterStack, Level level) {
        ConcurrentHashMap<Object, Boolean> FILTER_INNER_CACHE = ITEM_OUTER_CACHE.getIfPresent(stack);
        if (FILTER_INNER_CACHE == null) {
            FILTER_INNER_CACHE = new ConcurrentHashMap<>();
            boolean result = VFTests.basicFilterTest(getStackFromObject(stack),filterStack,level);
            FILTER_INNER_CACHE.put(filterStack,result);
            ITEM_OUTER_CACHE.put(stack,FILTER_INNER_CACHE);
            return result;
        }
        if (FILTER_INNER_CACHE.containsKey(filterStack)) {
            return FILTER_INNER_CACHE.get(filterStack);
        }
        boolean result = VFTests.basicFilterTest(getStackFromObject(stack),filterStack,level);
        FILTER_INNER_CACHE.put(filterStack,result);
        return result;
    }

}
