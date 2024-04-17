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
import net.joseph.vaultfilters.attributes.other.ItemNameAttribute;
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
        new ItemNameAttribute(("the_vault:chestplate")).register(ItemNameAttribute::new);
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
        new InscriptionRoomTypeAttribute("Challenge").register(InscriptionRoomTypeAttribute::new);
        new InscriptionRoomAttribute("Wild West").register(InscriptionRoomAttribute::new);
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
        // Charms
        new CharmUsesAttribute(0).register(CharmUsesAttribute::new);
        new CharmAffinityAttribute(0).register(CharmAffinityAttribute::new);
        new CharmGodAttribute("").register(CharmGodAttribute::new);
        // Trinkets
        new TrinketUsesAttribute(0).register(TrinketUsesAttribute::new);
        new TrinketNameAttribute("").register(TrinketNameAttribute::new);
        new TrinketColorAttribute("").register(TrinketColorAttribute::new);
    }
}
