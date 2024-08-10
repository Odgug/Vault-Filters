package net.joseph.vaultfilters;

import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.CardItem;
import iskallia.vault.item.InfusedCatalystItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.joseph.vaultfilters.VaultFilters.LEVEL_REF;

public class VFTests {
    public static boolean checkFilter(ItemStack stack, Object filterStack, boolean useCache, Level level) {
        if (!useCache) {
            return basicFilterTest(stack,filterStack,level);
        }
        Item stackItem = stack.getItem();
        if (! (stackItem instanceof VaultGearItem || stackItem instanceof InscriptionItem ||
                stackItem instanceof InfusedCatalystItem ||stackItem instanceof CharmItem ||
                stackItem instanceof TrinketItem || stackItem instanceof CardItem)) {
            return basicFilterTest(stack,filterStack,level);
        }
        if (VFServerConfig.CACHE_DATAFIX.get() && filterStack instanceof ItemStack) {
            DataFixers.clearNBTCache(stack);
        }
        //if (filterStack.getDisplayName().getString().equals("Ignore Caching")) {
        //return basicFilterTest(stack,filterStack,level);
        //}

        //return FilterItemStack.of(filterStack).test(null, stack);
        //return cacheTest(stack, filterStack, VFServerConfig.MAX_CACHES.get(),level);
        return VFCache.getOrCreateFilter(stack,filterStack,level);
    }

    @OnlyIn(Dist.CLIENT)
    public static Level getClientLevel() {
        return (Level) net.minecraft.client.Minecraft.getInstance().level;
    };


    public static String filterKey = "hashes";

    private static Method testMethod;

    private static boolean basicFilterTestLegacy(Object filterStack, ItemStack stack, Level level) {
        if (testMethod == null) {
            // try to find the method
            try {
                testMethod = FilterItem.class.getMethod("test", Level.class, ItemStack.class, ItemStack.class);
            } catch (NoSuchMethodException e) {
                VaultFilters.LOGGER.error("[0.5.1.b-e] could not find test method: {}", e.getMessage());
                // wrap it in unchecked exception
                throw new IllegalStateException(e);
            }
        }

        try {
            return (boolean) testMethod.invoke(null, level, stack, filterStack);
        } catch (IllegalAccessException | InvocationTargetException e) {
            VaultFilters.LOGGER.error("[0.5.1.b-e] could not invoke test method: {}", e.getMessage());
            // wrap it in unchecked exception
            throw new IllegalStateException(e);

        }
    }

    public static boolean basicFilterTest(ItemStack stack, Object filterStack, Level level) {


        if (level == null) {
            level = LEVEL_REF;
            if (FMLEnvironment.dist.isClient()) {
                DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> VFTests::getClientLevel);
            }

        }
        if (CreateVersion.getLoadedVersion() == CreateVersion.CREATE_051F) {
            if (filterStack instanceof ItemStack stackFilter) {
                return FilterItemStack.of(stackFilter).test(level, stack);
            }
            if (filterStack instanceof FilterItemStack filterItemStack) {
                return filterItemStack.test(level, stack);
            }
            VaultFilters.LOGGER.debug("[0.5.1.f] invalid filter entered");
            return false;
        }

        if (CreateVersion.getLoadedVersion() == CreateVersion.LEGACY) {
            return basicFilterTestLegacy(filterStack, stack, level);
        }
        return false;

    }
}
