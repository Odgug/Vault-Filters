package net.joseph.vaultfilters.mixin.other;

import iskallia.vault.VaultMod;
import iskallia.vault.block.item.VaultOreBlockItem;
import iskallia.vault.config.Config;
import iskallia.vault.config.LootInfoConfig;
import iskallia.vault.config.VaultAltarConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.core.world.data.item.ItemPredicate;
import iskallia.vault.core.world.data.tile.OrItemPredicate;
import iskallia.vault.core.world.loot.LootTableInfo;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.item.ItemVaultFruit;
import iskallia.vault.item.ItemVaultKey;
import iskallia.vault.item.VaultXPFoodItem;
import iskallia.vault.item.modification.GearModificationItem;
import iskallia.vault.util.data.WeightedList;
import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(TagLoader.class)
public class MixinTagLoader {
    @Shadow
    @Final
    private String directory;
    @Inject(method = "build", at = @At("HEAD"))
    private void afterBuild(Map<ResourceLocation, Tag.Builder> pBuilders, CallbackInfoReturnable<Map<ResourceLocation, Tag<?>>> cir) {
        if ("tags/items".equals(this.directory)){

            Set<ResourceLocation> wooden_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("wooden_chest")));
            vaultFilters$createTag(pBuilders,"wooden_chest_loot",wooden_items);

            Set<ResourceLocation> ornate_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("ornate_chest")));
            ornate_items.addAll(vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("ornate_strongbox"))));
            vaultFilters$createTag(pBuilders,"ornate_chest_loot",ornate_items);


            Set<ResourceLocation> living_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("living_chest")));
            living_items.addAll(vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("living_strongbox"))));
            vaultFilters$createTag(pBuilders,"living_chest_loot",living_items);

            Set<ResourceLocation> gilded_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("gilded_chest")));
            gilded_items.addAll(vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("gilded_strongbox"))));
            vaultFilters$createTag(pBuilders,"gilded_chest_loot",gilded_items);


            Set<ResourceLocation> all_chest_items = Stream.of(wooden_items,ornate_items, living_items, gilded_items)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            vaultFilters$createTag(pBuilders,"all_chest_loot",all_chest_items);



            Set<ResourceLocation> treasure_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("treasure_chest")));
            vaultFilters$createTag(pBuilders,"treasure_chest_loot",treasure_items);

            Set<ResourceLocation> treasure_sand_treasure = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("treasure_sand")));
            vaultFilters$createTag(pBuilders,"treasure_sand_treasure",treasure_sand_treasure);


            Set<ResourceLocation> treasure_sand_digsite = vaultFilters$getItemsFromTables(vaultFilters$getResourceLocationsFromStrings(
                    new HashSet<>(Arrays.asList("digsite_sand_lvl0")
                    )));
            vaultFilters$createTag(pBuilders,"treasure_sand_digsite",treasure_sand_digsite);

            Set<ResourceLocation> flesh_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("flesh_chest")));
            vaultFilters$createTag(pBuilders,"flesh_chest_loot",flesh_items);

            Set<ResourceLocation> enigma_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("enigma_chest")));
            vaultFilters$createTag(pBuilders,"enigma_chest_loot",enigma_items);

            Set<ResourceLocation> hardened_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("hardened_chest")));
            vaultFilters$createTag(pBuilders,"hardened_chest_loot",hardened_items);


            Set<ResourceLocation> raw_chest_items = Stream.of(hardened_items, enigma_items, flesh_items)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            vaultFilters$createTag(pBuilders,"all_skyvault_loot",raw_chest_items);

            Set<ResourceLocation> coin_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("coin_pile")));
            vaultFilters$createTag(pBuilders,"coin_pile_loot",coin_items);



            Set<ResourceLocation> elixir_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("completion_crate_elixir")));
            vaultFilters$createTag(pBuilders,"elixir_crate_loot",elixir_items);

            Set<ResourceLocation> guardian_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("completion_crate_guardian")));
            vaultFilters$createTag(pBuilders,"guardian_crate_loot",guardian_items);

            Set<ResourceLocation> brazier_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("completion_crate_brazier")));
            vaultFilters$createTag(pBuilders,"brazier_crate_loot",brazier_items);

            Set<ResourceLocation> scavenger_crate_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("completion_crate_scavenger")));
            vaultFilters$createTag(pBuilders,"scavenger_crate_loot",scavenger_crate_items);

            Set<ResourceLocation> paradox_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("completion_crate_paradox")));
            vaultFilters$createTag(pBuilders,"paradox_crate_loot",paradox_items);

            Set<ResourceLocation> bingo_items = vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("completion_crate_bingo")));
            bingo_items.addAll(vaultFilters$getItemsFromTables(vaultFilters$getTablesFromInfo(VaultMod.id("full_bingo"))));
            vaultFilters$createTag(pBuilders,"bingo_crate_loot",bingo_items);

            Set<ResourceLocation> crate_loot = Stream.of(elixir_items,guardian_items,brazier_items,scavenger_crate_items,paradox_items,bingo_items)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            vaultFilters$createTag(pBuilders,"completion_crates_loot",crate_loot);
            //might be missing royale idk how they work

            Set<ResourceLocation> champion_loot = vaultFilters$getItemsFromTables(vaultFilters$getResourceLocationsFromStrings(
                    new HashSet<>(Arrays.asList("champion_loot_lvl0","champion_loot_lvl20","champion_loot_lvl50")
                    )));
            vaultFilters$createTag(pBuilders,"champion_loot",champion_loot);

            Set<ResourceLocation> rune_loot = vaultFilters$getItemsFromTables(vaultFilters$getResourceLocationsFromStrings(
                    new HashSet<>(Arrays.asList("rune_loot_0","rune_loot_50")
                    )));
            vaultFilters$createTag(pBuilders,"rune_loot",rune_loot);

            Set<ResourceLocation> brazier_pillage_loot = vaultFilters$getItemsFromTables(vaultFilters$getResourceLocationsFromStrings(
                    new HashSet<>(Arrays.asList("brazier_lvl0","brazier_lvl20","brazier_lvl50")
                    )));
            vaultFilters$createTag(pBuilders,"brazier_pillaging",brazier_pillage_loot);
            Optional<Map<String, WeightedList<AltarIngredientEntry>>> pool = ModConfigs.VAULT_ALTAR_INGREDIENTS.LEVELS.getForLevel(100);
            if (pool.isEmpty()) {
                VaultFilters.LOGGER.info("couldn't get ingredients");
            } else {
                Map<String, WeightedList<AltarIngredientEntry>> map = pool.get();
                Tag.Builder all = pBuilders.computeIfAbsent(VaultMod.id("vault_altar_all"), id -> Tag.Builder.tag());
                map.forEach((name, pool2) -> {
                    Tag.Builder category = pBuilders.computeIfAbsent(VaultMod.id("vault_altar_" + name), id -> Tag.Builder.tag());
                    pool2.forEach((entry,num) -> {
                        entry.getItems().forEach(itemStack -> {
                            ResourceLocation rl = itemStack.getItem().getRegistryName();
                            if (Registry.ITEM.getOptional(rl).isPresent()) {
                                all.addElement(rl,"Vault Filters dynamic tags");
                                category.addElement(rl,"Vault Filters dynamic tags");
                            }
                        });
                    });
                });
            }
            // ores
            //black market loot
            //vendoor loot

            Tag.Builder treasureKey = pBuilders.computeIfAbsent(VaultMod.id("keys"), id -> Tag.Builder.tag());
            Tag.Builder fruit = pBuilders.computeIfAbsent(VaultMod.id("fruits"), id -> Tag.Builder.tag());
            Tag.Builder focuses = pBuilders.computeIfAbsent(VaultMod.id("focuses"), id -> Tag.Builder.tag());
            Tag.Builder burgers = pBuilders.computeIfAbsent(VaultMod.id("burgers"), id -> Tag.Builder.tag());
            Tag.Builder ores = pBuilders.computeIfAbsent(VaultMod.id("vault_ores"), id -> Tag.Builder.tag());
            Registry.ITEM.keySet().stream()
                    .map(Registry.ITEM::getOptional)
                    .flatMap(Optional::stream)
                    .forEach(item -> {
                        ResourceLocation rl = item.getRegistryName();
                        if (rl == null) {
                            return;
                        }
                        if (item instanceof ItemVaultKey) {
                            treasureKey.addElement(rl, "Vault Filters dynamic tags");
                        }
                        if (item instanceof ItemVaultFruit) {
                            fruit.addElement(rl, "Vault Filters dynamic tags");
                        }
                        if (item instanceof GearModificationItem) {
                            focuses.addElement(rl,"Vault Filters dynamic tags");
                        }
                        if (item instanceof VaultXPFoodItem) {
                            burgers.addElement(rl,"Vault Filters dynamic tags");
                        }
                        if (item instanceof VaultOreBlockItem) {
                            ores.addElement(rl,"Vault Filters dynamic tags");
                        }
                    });
        }

    }

    @Unique private static Set<ResourceLocation> vaultFilters$getResourceLocationsFromStrings(Set<String> strings) {
        Set<ResourceLocation> locations = new HashSet<>();
        strings.forEach(string -> {
            locations.add(VaultMod.id(string));
        });
        return locations;
    }
    //returns set of loot tables, gets a info location
    @Unique private static Set<ResourceLocation> vaultFilters$getTablesFromInfo(ResourceLocation location) {
        LootInfoConfig.LootInfo lootInfo = ModConfigs.LOOT_INFO_CONFIG.getLootInfoMap().get(location);
        return lootInfo != null ? lootInfo.getLootTableKeys() : Collections.emptySet();
    }
    //returns set of items, gets a set of loot tables
    @Unique private static Set<ResourceLocation> vaultFilters$getItemsFromTables(Set<ResourceLocation> tables) {
        Set<ResourceLocation> allItems = new HashSet<>();
        tables.forEach( table -> {
            allItems.addAll(vaultFilters$getItemsFromTable(table));
        });
        return allItems;
    }
    //returns set of items, gets a loot table
    @Unique private static Set<ResourceLocation> vaultFilters$getItemsFromTable(ResourceLocation table) {
        return LootTableInfo.getItemsForLootTableKey(table);
    }

    @Unique private static void vaultFilters$createTag(Map<ResourceLocation,Tag.Builder> pBuilders,String tag, Set<ResourceLocation> items) {
        Tag.Builder lootTag = pBuilders.computeIfAbsent(VaultMod.id(tag),id -> Tag.Builder.tag());
        items.forEach( item -> {
            if (Registry.ITEM.getOptional(item).isPresent()) {
                lootTag.addElement(item, "Vault Filters dynamic tags");
            }
        });
    }



}
