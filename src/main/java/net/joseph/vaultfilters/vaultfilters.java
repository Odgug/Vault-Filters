package net.joseph.vaultfilters;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllItems;
import iskallia.vault.config.ResearchConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.Restrictions;
import iskallia.vault.research.type.Research;
import net.joseph.vaultfilters.ItemAttributes.ModItemAttributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static iskallia.vault.init.ModConfigs.RESEARCHES;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("vaultfilters")
public class vaultfilters
{
    // Directly reference a slf4j logger
    public static final String MOD_ID = "vaultfilters";
    private static final Logger LOGGER = LogUtils.getLogger();

    public vaultfilters()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the enqueueIMC method for modloading
        // Register the processIMC method for modloading

        // Register ourselves for server and other game events we are interested in
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ModItemAttributes.register();

    }


}
