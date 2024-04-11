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
        // Charm Attributes
        new CharmAffinityAttribute(0).register(CharmAffinityAttribute::new);
        new CharmGodAttribute("").register(CharmGodAttribute::new);
        new CharmRarityAttribute("").register(CharmRarityAttribute::new);
        new CharmUsesAttribute(0).register(CharmUsesAttribute::new);
        // Inscription Attributes
        new InscriptionCompletionAttribute(0).register(InscriptionCompletionAttribute::new);
        new InscriptionInstabilityAttribute(0D).register(InscriptionInstabilityAttribute::new);
        new InscriptionRoomAttribute("").register(InscriptionRoomAttribute::new);
        new InscriptionRoomTypeAttribute("").register(InscriptionRoomTypeAttribute::new);
        new InscriptionTimeAttribute(0).register(InscriptionTimeAttribute::new);
        // Jewel Attributes
        new JewelCutsAttribute(0).register(JewelCutsAttribute::new);
        new JewelRarityAttribute("").register(JewelRarityAttribute::new);
        new JewelSizeAttribute(0).register(JewelSizeAttribute::new);
        // Soul Attributes
        new AtleastSoulAttribute(0).register(AtleastSoulAttribute::new);
        new ExactSoulAttribute(0).register(ExactSoulAttribute::new);
        new HasSoulValueAttribute(true).register(HasSoulValueAttribute::new);
        // Trinket Attributes
        new TrinketColorAttribute("").register(TrinketColorAttribute::new);
        new TrinketNameAttribute("").register(TrinketNameAttribute::new);
        new TrinketUsesAttribute(0).register(TrinketUsesAttribute::new);
        // Affix Attributes
        new ImplicitAttribute("").register(ImplicitAttribute::new);
        new PrefixAttribute("").register(PrefixAttribute::new);
        new SuffixAttribute("").register(SuffixAttribute::new);
        new LegendaryPrefixAttribute("").register(LegendaryPrefixAttribute::new);
        new LegendarySuffixAttribute("").register(LegendarySuffixAttribute::new);
        new HasLegendaryAttribute(true).register(HasLegendaryAttribute::new);
        new NumberImplicitAttribute("").register(NumberImplicitAttribute::new);
        new NumberPrefixAttribute("").register(NumberPrefixAttribute::new);
        new NumberSuffixAttribute("").register(NumberSuffixAttribute::new);
        // Gear Attributes
        new GearLevelAttribute(0).register(GearLevelAttribute::new);
        new GearRarityAttribute("").register(GearRarityAttribute::new);
        new GearRepairSlotAttribute(0).register(GearRepairSlotAttribute::new);
        new GearTransmogAttribute("").register(GearTransmogAttribute::new);
        new IsUnidentifiedAttribute(true).register(IsUnidentifiedAttribute::new);
        new ItemTypeAttribute("").register(ItemTypeAttribute::new);
    }
}
