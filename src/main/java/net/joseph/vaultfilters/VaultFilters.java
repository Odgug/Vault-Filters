package net.joseph.vaultfilters;

import com.mojang.logging.LogUtils;
import net.joseph.vaultfilters.attributes.abstracts.Objects.Modifier;
import net.joseph.vaultfilters.attributes.affix.*;
import net.joseph.vaultfilters.attributes.card.*;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(VaultFilters.MOD_ID)
public class VaultFilters {
    public static final String MOD_ID = "vaultfilters";
    public static final String MOD_VERSION = "1.13.1";
    public static final int CHECK_FILTER_FLAG = 456;
    public static final int NO_CACHE_FLAG = 457;
    public static final Logger LOGGER = LogUtils.getLogger();

    public VaultFilters() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(VFCache.class);
        MinecraftForge.EVENT_BUS.register(VFTests.class);
        MinecraftForge.EVENT_BUS.register(ModPresence.class);
    }

    private void setup(FMLCommonSetupEvent event) {
        ModPresence.init();
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

        // Cards
        new CardPackOpenedAttribute(true).register(CardPackOpenedAttribute::new);
        new CardPackTypeAttribute("Stat").register(CardPackTypeAttribute::new);
        new CardAtleastTierAttribute(2).register(CardAtleastTierAttribute::new);
        new CardColorAttribute("Red").register(CardColorAttribute::new);
        new CardTypeAttribute("Foil").register(CardTypeAttribute::new);
        new CardUpgradableAttribute(true).register(CardUpgradableAttribute::new);
        new CardModifierAttribute("Lucky Hit").register(CardModifierAttribute::new);
        new CardIsScalingAttribute(true).register(CardIsScalingAttribute::new);
        new CardHasConditionAttribute(true).register(CardHasConditionAttribute::new);
        new CardScaleTypesAttribute("Diagonal").register(CardScaleTypesAttribute::new);
        new CardConditionCompAttribute("At Least").register(CardConditionCompAttribute::new);
        new CardConditionGroupsAttribute("Blue").register(CardConditionGroupsAttribute::new);
        new CardConditionNumAttribute(5).register(CardConditionNumAttribute::new);
        new CardTaskAttribute("Wooden Chests").register(CardTaskAttribute::new);
        new CardTaskNumberAttribute(5).register(CardTaskNumberAttribute::new);
        new CardModifierNumberAttribute(new Modifier("+1 Attack Damage", "Attack Damage",1)).register(CardModifierNumberAttribute::new);

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
}
