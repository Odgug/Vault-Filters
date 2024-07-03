package net.joseph.vaultfilters;

import com.simibubi.create.content.logistics.filter.FilterItem;
import iskallia.vault.gear.data.GearDataCache;
import iskallia.vault.gear.item.VaultGearItem;
import net.joseph.vaultfilters.attributes.affix.*;
import net.joseph.vaultfilters.attributes.catalysts.CatalystHasModifierAttribute;
import net.joseph.vaultfilters.attributes.catalysts.CatalystModifierCategoryAttribute;
import net.joseph.vaultfilters.attributes.catalysts.CatalystSizeAttribute;
import net.joseph.vaultfilters.attributes.charm.*;
import net.joseph.vaultfilters.attributes.inscription.*;
import net.joseph.vaultfilters.attributes.jewel.*;
import net.joseph.vaultfilters.attributes.gear.*;
import net.joseph.vaultfilters.attributes.old.InscriptionCompletionAttribute;
import net.joseph.vaultfilters.attributes.old.InscriptionInstabilityAttribute;
import net.joseph.vaultfilters.attributes.old.InscriptionTimeAttribute;
import net.joseph.vaultfilters.attributes.other.*;
import net.joseph.vaultfilters.attributes.soul.*;
import net.joseph.vaultfilters.attributes.tool.ToolMaterialAttribute;
import net.joseph.vaultfilters.attributes.trinket.*;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.ref.WeakReference;

import static net.minecraftforge.forgespi.Environment.Keys.DIST;


//import com.simibubi.create.content.logistics.filter.FilterItemStack;


@Mod(VaultFilters.MOD_ID)
public class VaultFilters {
    public static final String MOD_ID = "vaultfilters";
    public static final int CHECK_FILTER_FLAG = 456;
    public static final int NO_CACHE_FLAG = 457;
    public VaultFilters() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        //eventBus.addListener(this::onWorldLoad);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final ThreadLocal<WeakReference<Level>> LEVEL_REF = new ThreadLocal<>();


    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        MinecraftServer server = event.getWorld().getServer();
        if (server != null) {
            LEVEL_REF.set(new WeakReference<>(server.getLevel(Level.OVERWORLD)));
        }
    }

    private void setup(FMLCommonSetupEvent event) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, VFServerConfig.SPEC, "vaultfilters-server.toml");
        // This has a specific order as this controls the order displayed in the filters
        new ItemNameAttribute(("Vault Helmet")).register(ItemNameAttribute::new);
        // Soul Attributes
        new HasSoulValueAttribute(true).register(HasSoulValueAttribute::new);
        new ExactSoulAttribute(0).register(ExactSoulAttribute::new);
        new AtleastSoulAttribute(0).register(AtleastSoulAttribute::new);
        // Common
        new ItemTypeAttribute("Jewel").register(ItemTypeAttribute::new);
        new IsUnidentifiedAttribute(true).register(IsUnidentifiedAttribute::new);
        // Rarities
        new GearRarityAttribute("Omega").register(GearRarityAttribute::new);
        new JewelRarityAttribute("Chipped").register(JewelRarityAttribute::new);
        new CharmRarityAttribute("Regal").register(CharmRarityAttribute::new);
        // Inscription Attributes
        new InscriptionSizeAttribute(10).register(InscriptionSizeAttribute::new);
        new InscriptionRoomTypeAttribute("Challenge").register(InscriptionRoomTypeAttribute::new);
        new InscriptionRoomAttribute("Wild West").register(InscriptionRoomAttribute::new);

        // Old (register anyway for data fixing purposes)
        new InscriptionTimeAttribute(0).register(InscriptionTimeAttribute::new);
        new InscriptionCompletionAttribute(0).register(InscriptionCompletionAttribute::new);
        new InscriptionInstabilityAttribute(0D).register(InscriptionInstabilityAttribute::new);
        // Gear
        new GearLevelAttribute(0).register(GearLevelAttribute::new);
        new GearTransmogAttribute("Sword_4").register(GearTransmogAttribute::new);
        new GearRepairSlotAttribute(0).register(GearRepairSlotAttribute::new);
        // Jewels
        new JewelSizeAttribute(0).register(JewelSizeAttribute::new);
        new JewelCutsAttribute(0).register(JewelCutsAttribute::new);
        //Tools
        new ToolMaterialAttribute("Chromatic Steel").register(ToolMaterialAttribute::new);
        // Affixes
        new HasLegendaryAttribute(true).register(HasLegendaryAttribute::new);
        new LegendaryPrefixAttribute("Attack Damage").register(LegendaryPrefixAttribute::new);
        new LegendarySuffixAttribute("Trap Disarm").register(LegendarySuffixAttribute::new);

        new ImplicitAttribute("Armor").register(ImplicitAttribute::new);
        new PrefixAttribute("Durability").register(PrefixAttribute::new);
        new SuffixAttribute("Poison Cloud").register(SuffixAttribute::new);

        new NumberImplicitAttribute("", "", 0).register(NumberImplicitAttribute::new);
        new NumberPrefixAttribute("", "", 0).register(NumberPrefixAttribute::new);
        new NumberSuffixAttribute("", "", 0).register(NumberSuffixAttribute::new);

        new ModifierGroupAttribute("ModAbility").register(ModifierGroupAttribute::new);
        // Charms
        new CharmUsesAttribute(0).register(CharmUsesAttribute::new);
        new CharmAffinityAttribute(0).register(CharmAffinityAttribute::new);
        new CharmGodAttribute("").register(CharmGodAttribute::new);
        // Trinkets
        new TrinketUsesAttribute(0).register(TrinketUsesAttribute::new);
        new TrinketNameAttribute("").register(TrinketNameAttribute::new);
        new TrinketColorAttribute("").register(TrinketColorAttribute::new);

        // Catalysts
        new CatalystSizeAttribute(10).register(CatalystSizeAttribute::new);
        new CatalystHasModifierAttribute("Ornate").register(CatalystHasModifierAttribute::new);
        new CatalystModifierCategoryAttribute("Bonus Chests").register(CatalystModifierCategoryAttribute::new);


    }
    public static boolean checkFilter(ItemStack stack, ItemStack filterStack, boolean useCache, Level level) {
        if (!useCache) {
            return filterTest(stack,filterStack,level);
        }
        if (!(stack.getItem() instanceof VaultGearItem)) {
            return filterTest(stack,filterStack,level);
        }
        if (filterStack.getDisplayName().getString().equals("Ignore Caching")) {
            return filterTest(stack,filterStack,level);
        }
        //return FilterItemStack.of(filterStack).test(null, stack);
        return cacheTest(stack, filterStack, VFServerConfig.MAX_CACHES.get(),level);
    }

    @OnlyIn(Dist.CLIENT)
    public static Level getClientLevel() {
        return net.minecraft.client.Minecraft.getInstance().level;
    };


    public static String filterKey = "hashes";
    public static boolean filterTest(ItemStack stack, ItemStack filterStack, Level level) {


        if (level == null) {
            level = DistExecutor.safeRunForDist(
                    () -> VaultFilters::getClientLevel,
                    () -> () -> LEVEL_REF.get().get()
            );
        }
        return FilterItem.test(level,stack, filterStack);
    }
    public static boolean cacheTest(ItemStack stack, ItemStack filterStack, int maxHashes, Level level) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!(stack.getOrCreateTag().contains("clientCache"))) {
            GearDataCache.createCache(stack);
        }

        tag = (CompoundTag) tag.get("clientCache");
        ListTag filterHashes = tag.get(filterKey) instanceof ListTag listTag ? listTag : new ListTag();
        if (!tag.contains(filterKey)) {
            tag.put(filterKey,filterHashes);
        }

        int hashCount = filterHashes.size();
        while (hashCount > maxHashes) {
            filterHashes.remove(0);
            hashCount--;
        }

        if (maxHashes == 0) {
            return VaultFilters.filterTest(stack,filterStack,level);
        }

        int hash = filterStack.hashCode();
        IntTag passedHash = IntTag.valueOf(hash);
        IntTag failedHash = IntTag.valueOf(-hash);
        if (filterHashes.contains(failedHash)) {
            return false;
        } else if (filterHashes.contains(passedHash)) {
            return true;
        }

        if (hashCount == maxHashes) {
            filterHashes.remove(0);
        }

        boolean result = VaultFilters.filterTest(stack,filterStack,level);

        if (result) {
            filterHashes.add(passedHash);
        } else {
            filterHashes.add(failedHash);
        }

        return result;
    }
}
