package net.joseph.vaultfilters;

import appeng.api.stacks.AEItemKey;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.*;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TrinketItem;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

public class VFTests {
    public static String filterKey = "hashes";
    private static MethodHandle testMethodHandle;
    private static Level level;

    static {
        if (CreateVersion.getLoadedVersion() == CreateVersion.LEGACY) {
            try {
                MethodType methodType = MethodType.methodType(boolean.class, Level.class, ItemStack.class, ItemStack.class);
                testMethodHandle = MethodHandles.lookup().findStatic(FilterItem.class, "test", methodType);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                VaultFilters.LOGGER.error("[0.5.1.b-e] could not find test method", e);
                throw new IllegalStateException(e);
            }
        }
    }

    public static boolean checkFilter(ItemStack stack, Object filterStack, boolean useCache, Level level) {
        if (!useCache) {
            return basicFilterTest(stack,filterStack,level);
        }

        Item stackItem = stack.getItem();
        //if (!(stackItem instanceof VaultGearItem || stackItem instanceof InscriptionItem ||
        //        stackItem instanceof InfusedCatalystItem ||stackItem instanceof CharmItem ||
        //        stackItem instanceof TrinketItem || stackItem instanceof CardItem ||
        //        stackItem instanceof BoosterPackItem || stackItem instanceof JewelPouchItem)) {
        //    return basicFilterTest(stack,filterStack,level);
        //}

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
    public static boolean checkFilter(AEItemKey stack, Object filterStack, boolean useCache, Level level) {
        if (!useCache) {
            return basicFilterTest(stack.toStack(),filterStack,level);
        }
        return VFCache.getOrCreateFilter(stack,filterStack,level);
    }

    public static boolean testCardPack(ItemStack stack, Object filterStack, Level level) {
        List<ItemStack> cardPack = BoosterPackItem.getOutcomes(stack);
        boolean packMatch = basicFilterTest(stack, filterStack, level);
        if (cardPack == null || packMatch) {
            return packMatch;
        }

        for (ItemStack card : cardPack) {
            if (basicFilterTest(card,filterStack,level)) {
                return true;
            }
        }
        return false;
    }
    public static boolean testJewelPouch(ItemStack stack, Object filterStack, Level level) {
        List<JewelPouchItem.RolledJewel> jewelPouch= JewelPouchItem.getJewels(stack);
        List<ItemStack> jewelOptions = new ArrayList<>();
        for (JewelPouchItem.RolledJewel rolledJewel : jewelPouch) {
            if (rolledJewel.identified()) {
                jewelOptions.add(rolledJewel.stack());
            }
        }
        boolean pouchMatch = basicFilterTest(stack,filterStack,level);
        if (jewelOptions.isEmpty() || pouchMatch) {
            return pouchMatch;
        }

        for (ItemStack jewel : jewelOptions) {
            if (basicFilterTest(jewel,filterStack,level)) {
                return true;
            }
        }
        return false;
    }

    public static boolean noCacheDetailedTest(ItemStack stack, Object filterStack, Level level) {
        if (stack.getItem() instanceof BoosterPackItem) {
            return VFTests.testCardPack(stack,filterStack,level);
        } else if (stack.getItem() instanceof JewelPouchItem) {
            return VFTests.testJewelPouch(stack,filterStack,level);
        } else {
            return VFTests.basicFilterTest(stack, filterStack, level);
        }
    }

    public static boolean basicFilterTest(ItemStack stack, Object filterStack, Level level) {
        if (level == null) {
            level = DistExecutor.unsafeRunForDist(() -> VFTests::getClientLevel, () -> () -> VFTests.level);
        }

        if (CreateVersion.getLoadedVersion() == CreateVersion.CREATE_051F) {
            if (filterStack instanceof ItemStack stackFilter) {
                return FilterItemStack.of(stackFilter).test(level, stack);
            } else if (filterStack instanceof FilterItemStack filterItemStack) {
                return filterItemStack.test(level, stack);
            } else if (filterStack instanceof AEItemKey aeItemKey) {
                return FilterItemStack.of(aeItemKey.toStack()).test(level,stack);
            }
            VaultFilters.LOGGER.debug("[0.5.1.f] invalid filter entered");
            return false;
        }

        if (CreateVersion.getLoadedVersion() == CreateVersion.LEGACY) {
            return basicFilterTestLegacy(filterStack, stack, level);
        }
        return false;
    }

    private static boolean basicFilterTestLegacy(Object filterStack, ItemStack stack, Level level) {
        if (testMethodHandle == null) {
            throw new IllegalStateException("[0.5.1.b-e] could not find test method");
        }

        try {
            return (boolean) testMethodHandle.invoke(level, stack, filterStack);
        } catch (Throwable e) {
            VaultFilters.LOGGER.error("[0.5.1.b-e] could not invoke test method", e);
            // wrap it in unchecked exception
            throw new IllegalStateException(e);
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        MinecraftServer server = event.getWorld().getServer();
        if (server != null) {
            level = server.getLevel(Level.OVERWORLD);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}
