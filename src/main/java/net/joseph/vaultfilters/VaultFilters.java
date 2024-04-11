package net.joseph.vaultfilters;

import net.joseph.vaultfilters.attributes.charm.CharmAffinityAttribute;
import net.joseph.vaultfilters.attributes.charm.CharmGodAttribute;
import net.joseph.vaultfilters.attributes.charm.CharmRarityAttribute;
import net.joseph.vaultfilters.attributes.charm.CharmUsesAttribute;
import net.joseph.vaultfilters.attributes.inscription.InscriptionCompletionAttribute;
import net.joseph.vaultfilters.attributes.inscription.InscriptionInstabilityAttribute;
import net.joseph.vaultfilters.attributes.inscription.InscriptionRoomAttribute;
import net.joseph.vaultfilters.attributes.inscription.InscriptionRoomTypeAttribute;
import net.joseph.vaultfilters.attributes.inscription.InscriptionTimeAttribute;
import net.joseph.vaultfilters.attributes.jewel.JewelCutsAttribute;
import net.joseph.vaultfilters.attributes.jewel.JewelRarityAttribute;
import net.joseph.vaultfilters.attributes.jewel.JewelSizeAttribute;
import net.joseph.vaultfilters.attributes.affix.ImplicitAttribute;
import net.joseph.vaultfilters.attributes.gear.GearLevelAttribute;
import net.joseph.vaultfilters.attributes.affix.PrefixAttribute;
import net.joseph.vaultfilters.attributes.gear.GearRarityAttribute;
import net.joseph.vaultfilters.attributes.gear.GearRepairSlotAttribute;
import net.joseph.vaultfilters.attributes.affix.SuffixAttribute;
import net.joseph.vaultfilters.attributes.gear.GearTransmogAttribute;
import net.joseph.vaultfilters.attributes.affix.HasLegendaryAttribute;
import net.joseph.vaultfilters.attributes.gear.IsUnidentifiedAttribute;
import net.joseph.vaultfilters.attributes.gear.ItemTypeAttribute;
import net.joseph.vaultfilters.attributes.affix.LegendaryPrefixAttribute;
import net.joseph.vaultfilters.attributes.affix.LegendarySuffixAttribute;
import net.joseph.vaultfilters.attributes.affix.NumberImplicitAttribute;
import net.joseph.vaultfilters.attributes.affix.NumberPrefixAttribute;
import net.joseph.vaultfilters.attributes.affix.NumberSuffixAttribute;
import net.joseph.vaultfilters.attributes.soul.AtleastSoulAttribute;
import net.joseph.vaultfilters.attributes.soul.ExactSoulAttribute;
import net.joseph.vaultfilters.attributes.soul.HasSoulValueAttribute;
import net.joseph.vaultfilters.attributes.trinket.TrinketColorAttribute;
import net.joseph.vaultfilters.attributes.trinket.TrinketNameAttribute;
import net.joseph.vaultfilters.attributes.trinket.TrinketUsesAttribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VaultFilters.MOD_ID)
public class VaultFilters {
    public static final String MOD_ID = "vaultfilters";
    public static final int CHECK_FILTER_FLAG = 456;

    public VaultFilters() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        // This has a specific order as this controls the order displayed in the filters
        // Soul Attributes
        new HasSoulValueAttribute(true).register(HasSoulValueAttribute::new);
        new ExactSoulAttribute(0).register(ExactSoulAttribute::new);
        new AtleastSoulAttribute(0).register(AtleastSoulAttribute::new);
        // Common
        new ItemTypeAttribute("").register(ItemTypeAttribute::new);
        new IsUnidentifiedAttribute(true).register(IsUnidentifiedAttribute::new);
        // Rarities
        new GearRarityAttribute("").register(GearRarityAttribute::new);
        new JewelRarityAttribute("").register(JewelRarityAttribute::new);
        new CharmRarityAttribute("").register(CharmRarityAttribute::new);
        // Inscription Attributes
        new InscriptionRoomTypeAttribute("").register(InscriptionRoomTypeAttribute::new);
        new InscriptionRoomAttribute("").register(InscriptionRoomAttribute::new);
        new InscriptionTimeAttribute(0).register(InscriptionTimeAttribute::new);
        new InscriptionCompletionAttribute(0).register(InscriptionCompletionAttribute::new);
        new InscriptionInstabilityAttribute(0D).register(InscriptionInstabilityAttribute::new);
        // More Common
        new GearLevelAttribute(0).register(GearLevelAttribute::new);
        new GearTransmogAttribute("").register(GearTransmogAttribute::new);
        // Affixes (Legendary, Jewel Specific, All Gear)
        new HasLegendaryAttribute(true).register(HasLegendaryAttribute::new);
        new LegendaryPrefixAttribute("").register(LegendaryPrefixAttribute::new);
        new LegendarySuffixAttribute("").register(LegendarySuffixAttribute::new);
        new JewelSizeAttribute(0).register(JewelSizeAttribute::new);
        new JewelCutsAttribute(0).register(JewelCutsAttribute::new);
        new GearRepairSlotAttribute(0).register(GearRepairSlotAttribute::new);
        new ImplicitAttribute("").register(ImplicitAttribute::new);
        new NumberImplicitAttribute("").register(NumberImplicitAttribute::new);
        new PrefixAttribute("").register(PrefixAttribute::new);
        new NumberPrefixAttribute("").register(NumberPrefixAttribute::new);
        new SuffixAttribute("").register(SuffixAttribute::new);
        new NumberSuffixAttribute("").register(NumberSuffixAttribute::new);
        // Charm & Trinkets
        new CharmUsesAttribute(0).register(CharmUsesAttribute::new);
        new TrinketUsesAttribute(0).register(TrinketUsesAttribute::new);
        new CharmAffinityAttribute(0).register(CharmAffinityAttribute::new);
        new CharmGodAttribute("").register(CharmGodAttribute::new);
        new TrinketNameAttribute("").register(TrinketNameAttribute::new);
        new TrinketColorAttribute("").register(TrinketColorAttribute::new);
    }
}
