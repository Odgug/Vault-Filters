package net.joseph.vaultfilters.mixin.other;

import iskallia.vault.VaultMod;
import iskallia.vault.config.LootInfoConfig;
import iskallia.vault.core.world.loot.LootTableInfo;
import iskallia.vault.init.ModConfigs;
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


            // ores
            //black market loot
            //vendoor loot
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
