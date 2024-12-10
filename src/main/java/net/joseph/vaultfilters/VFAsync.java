package net.joseph.vaultfilters;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static net.joseph.vaultfilters.VFCache.iterateCache;


public class VFAsync {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static Future<?> currentTask;
    private static boolean isShuttingDown = false;

    public static void asyncIterateCache() {
        if (currentTask != null && !currentTask.isDone()) {
            currentTask.cancel(true);
        }
        currentTask = EXECUTOR.submit(()-> {
            try {
                iterateCache();
            } catch(Exception e) {
                VaultFilters.LOGGER.info("Cache clearing failed");
            }
        });
    }
    public static void shutdownAsync() {
        if (!isShuttingDown) {
            EXECUTOR.shutdownNow();
            isShuttingDown = true;
        }

    }
    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        shutdownAsync();
    }
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getInstance().level == null) {
            shutdownAsync();
        } else if (Minecraft.getInstance().level != null) {
            isShuttingDown = false;
        }
    }


}
