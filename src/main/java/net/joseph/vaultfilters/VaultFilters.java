package net.joseph.vaultfilters;

import com.mojang.logging.LogUtils;
import net.joseph.vaultfilters.attributes.abstracts.Objects.Modifier;
import net.joseph.vaultfilters.attributes.affix.*;
import net.joseph.vaultfilters.attributes.artifact.ArtifactIDAttribute;
import net.joseph.vaultfilters.attributes.backpacks.IsEmptyAttribute;
import net.joseph.vaultfilters.attributes.companion.*;
import net.joseph.vaultfilters.attributes.companion.items.CompanionEggSeriesAttribute;
import net.joseph.vaultfilters.attributes.companion.items.CompanionRelicAtleastAttribute;
import net.joseph.vaultfilters.attributes.companion.items.CompanionRelicAttribute;
import net.joseph.vaultfilters.attributes.companion.items.CompanionTrailAttribute;
import net.joseph.vaultfilters.attributes.deck.CardDeckHasModifierAttribute;
import net.joseph.vaultfilters.attributes.deck.CardDeckTypeAttribute;
import net.joseph.vaultfilters.attributes.inner.InnerCardPackAttribute;
import net.joseph.vaultfilters.attributes.inner.InnerJewelPouchAttribute;
import net.joseph.vaultfilters.attributes.inner.InnerRuneItemAttribute;
import net.joseph.vaultfilters.attributes.rune.BossRuneModifierAttribute;
import net.joseph.vaultfilters.attributes.rune.BossRuneGivesItemAttribute;
import net.joseph.vaultfilters.attributes.rune.BossRuneGearRarityAttribute;
import net.joseph.vaultfilters.attributes.rune.BossRuneBoosterPackTypeAttribute;
import net.joseph.vaultfilters.attributes.rune.BossRuneInscriptionTypeAttribute;

import net.joseph.vaultfilters.attributes.backpacks.HasUUIDAttribute;
import net.joseph.vaultfilters.attributes.backpacks.HasUpgradeAttribute;

import net.joseph.vaultfilters.attributes.card.*;
import net.joseph.vaultfilters.attributes.catalysts.CatalystHasModifierAttribute;
import net.joseph.vaultfilters.attributes.catalysts.CatalystModifierCategoryAttribute;
import net.joseph.vaultfilters.attributes.catalysts.CatalystSizeAttribute;
import net.joseph.vaultfilters.attributes.charm.*;
import net.joseph.vaultfilters.attributes.inscription.*;
import net.joseph.vaultfilters.attributes.jewel.*;
import net.joseph.vaultfilters.attributes.gear.*;
import net.joseph.vaultfilters.attributes.old.GearIsUniqueAttribute;
import net.joseph.vaultfilters.attributes.old.InscriptionCompletionAttribute;
import net.joseph.vaultfilters.attributes.old.InscriptionInstabilityAttribute;
import net.joseph.vaultfilters.attributes.old.InscriptionTimeAttribute;
import net.joseph.vaultfilters.attributes.other.*;
import net.joseph.vaultfilters.attributes.packs.CardPackChooseAttribute;
import net.joseph.vaultfilters.attributes.packs.CardPackOpenedAttribute;
import net.joseph.vaultfilters.attributes.packs.CardPackTypeAttribute;
import net.joseph.vaultfilters.attributes.pouch.JewelPouchChooseAttribute;
import net.joseph.vaultfilters.attributes.pouch.JewelPouchOpenedAttribute;
import net.joseph.vaultfilters.attributes.scav.IsRottenScavAttribute;
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
    public static final int CHECK_FILTER_FLAG = 456;
    public static final Logger LOGGER = LogUtils.getLogger();

    public VaultFilters() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(VFCache.class);
        MinecraftForge.EVENT_BUS.register(VFTests.class);
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


        // Gear
        new GearLevelAttribute(0).register(GearLevelAttribute::new);
        new HasLivingAttribute(true).register(HasLivingAttribute::new);
        new GearTransmogAttribute("Sword_4").register(GearTransmogAttribute::new);
        new GearRepairSlotAttribute(0).register(GearRepairSlotAttribute::new);
        new IsCraftedAttribute(true).register(IsCraftedAttribute::new);
        new GearUniqueNameAttribute("Baguette").register(GearUniqueNameAttribute::new);
        new PotentialCurrentAttribute(255).register(PotentialCurrentAttribute::new);
        new PotentialMaxAttribute(256).register(PotentialMaxAttribute::new);
        new HasSoulboundAttribute(true).register(HasSoulboundAttribute::new);

        // Jewel Pouches
        new JewelPouchOpenedAttribute(true).register(JewelPouchOpenedAttribute::new);
        new JewelPouchChooseAttribute(true).register(JewelPouchChooseAttribute::new);

        // Jewels
        new JewelSizeAttribute(0).register(JewelSizeAttribute::new);
        new JewelCutsAttribute(0).register(JewelCutsAttribute::new);

        //Tools
        new ToolMaterialAttribute("Chromatic Steel").register(ToolMaterialAttribute::new);

        // Affixes
        new HasLegendaryAttribute(true).register(HasLegendaryAttribute::new);
        new HasCorruptedAttribute(true).register(HasCorruptedAttribute::new);
        new HasGreaterAttribute(true).register(HasGreaterAttribute::new);
        new HasFrozenAttribute(true).register(HasFrozenAttribute::new);
        new HasCraftedAttribute(true).register(HasCraftedAttribute::new);
        new LegendaryPrefixAttribute("Attack Damage").register(LegendaryPrefixAttribute::new);
        new LegendarySuffixAttribute("Trap Disarm").register(LegendarySuffixAttribute::new);

        new ImplicitAttribute("Armor").register(ImplicitAttribute::new);
        new PrefixAttribute("Durability").register(PrefixAttribute::new);
        new SuffixAttribute("Poison Cloud").register(SuffixAttribute::new);

        new NumberImplicitAttribute("", "", 0).register(NumberImplicitAttribute::new);
        new NumberPrefixAttribute("", "", 0).register(NumberPrefixAttribute::new);
        new NumberSuffixAttribute("", "", 0).register(NumberSuffixAttribute::new);


        new CorruptedImplicitAttribute("Attack Damage").register(CorruptedImplicitAttribute::new);
        new CorruptedPrefixAttribute("Attack Damage").register(CorruptedPrefixAttribute::new);
        new CorruptedSuffixAttribute("Trap Disarm").register(CorruptedSuffixAttribute::new);
        new IsCorruptedAttribute(true).register(IsCorruptedAttribute::new);


        new ModifierGroupAttribute("ModAbility").register(ModifierGroupAttribute::new);


        // Card Packs

        new CardPackOpenedAttribute(true).register(CardPackOpenedAttribute::new);
        new CardPackTypeAttribute("Stat").register(CardPackTypeAttribute::new);
        new CardPackChooseAttribute(true).register(CardPackChooseAttribute::new);

        // Cards

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

        
        // Backpacks
        new IsEmptyAttribute(true).register(IsEmptyAttribute::new);


        new HasUpgradeAttribute("Backpack Upgrade").register(HasUpgradeAttribute::new);
        new HasUUIDAttribute("Backpack UUID").register(HasUUIDAttribute::new);

        // Runes
        new BossRuneModifierAttribute("Strength").register(BossRuneModifierAttribute::new);
        new BossRuneGivesItemAttribute("the_vault:helmet").register(BossRuneGivesItemAttribute::new);
        new BossRuneGearRarityAttribute("Omega").register(BossRuneGearRarityAttribute::new);
        new BossRuneBoosterPackTypeAttribute("the_vault:mega_stat_pack").register(BossRuneBoosterPackTypeAttribute::new);
        new BossRuneInscriptionTypeAttribute("Laboratory").register(BossRuneInscriptionTypeAttribute::new);

        //Inner checks
        new InnerCardPackAttribute(null).register(InnerCardPackAttribute::new);
        new InnerJewelPouchAttribute(null).register(InnerJewelPouchAttribute::new);
        new InnerRuneItemAttribute(null).register(InnerRuneItemAttribute::new);
        //Artifacts
        new ArtifactIDAttribute(0).register(ArtifactIDAttribute::new);

        //Scav
        new IsRottenScavAttribute(true).register(IsRottenScavAttribute::new);

        //Companion
        new CompanionRelicAttribute("").register(CompanionRelicAttribute::new);
        new CompanionTrailAttribute("").register(CompanionTrailAttribute::new);
        new CompanionEggSeriesAttribute("").register(CompanionEggSeriesAttribute::new);
        new CompanionCooldownAttribute(true).register(CompanionCooldownAttribute::new);
        new CompanionCooldownAttribute(true).register(CompanionCooldownAttribute::new);
        new CompanionHeartAttribute(1).register(CompanionHeartAttribute::new);
        new CompanionLevelAttribute(1).register(CompanionLevelAttribute::new);
        new CompanionMaxHeartAttribute(1).register(CompanionMaxHeartAttribute::new);
        new CompanionSeriesAttribute("").register(CompanionSeriesAttribute::new);
        new CompanionSkinAttribute("").register(CompanionSkinAttribute::new);
        new CompanionTemporalAttribute("").register(CompanionTemporalAttribute::new);
        new CompanionRelicAtleastAttribute(1).register(CompanionRelicAtleastAttribute::new);

        //Deck
        new CardDeckHasModifierAttribute(true).register(CardDeckHasModifierAttribute::new);
        new CardDeckTypeAttribute("").register(CardDeckTypeAttribute::new);


        //Old
        new InscriptionTimeAttribute(0).register(InscriptionTimeAttribute::new);
        new InscriptionCompletionAttribute(0).register(InscriptionCompletionAttribute::new);
        new InscriptionInstabilityAttribute(0D).register(InscriptionInstabilityAttribute::new);
        new GearIsUniqueAttribute(true).register(GearIsUniqueAttribute::new);
    }
}
