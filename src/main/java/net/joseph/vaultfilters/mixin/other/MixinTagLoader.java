package net.joseph.vaultfilters.mixin.other;

import iskallia.vault.VaultMod;
import iskallia.vault.core.world.loot.LootTableInfo;
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
            Set<String> ornate_chests = new HashSet<>(Arrays.asList(
                    "ornate_chest_lvl0", "ornate_chest_lvl20", "ornate_chest_lvl30","ornate_chest_lvl50","ornate_chest_lvl65",
                    "ornate_strongbox_lvl50","ornate_strongbox_lvl65"));
            Set<ResourceLocation> ornate_items = vaultFilters$getItemsFromTable(ornate_chests);
            vaultFilters$createTag(pBuilders,"ornate_chest_loot",ornate_items);

            Set<String> living_chests = new HashSet<>(Arrays.asList(
                    "living_chest_lvl0", "living_chest_lvl10", "living_chest_lvl20","living_chest_lvl30","living_chest_lvl40",
                    "living_strongbox_lvl50"));
            Set<ResourceLocation> living_items = vaultFilters$getItemsFromTable(living_chests);
            vaultFilters$createTag(pBuilders,"living_chest_loot",living_items);

            Set<String> gilded_chests = new HashSet<>(Arrays.asList(
                    "gilded_chest_lvl0", "gilded_chest_lvl20", "gilded_chest_lvl50",
                    "gilded_strongbox_lvl50"));
            Set<ResourceLocation> gilded_items = vaultFilters$getItemsFromTable(gilded_chests);
            vaultFilters$createTag(pBuilders,"gilded_chest_loot",gilded_items);


            Set<ResourceLocation> all_chest_items = Stream.of(ornate_items, living_items, gilded_items)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            vaultFilters$createTag(pBuilders,"all_chest_loot",all_chest_items);
        }

    }
    @Unique private static Set<ResourceLocation> vaultFilters$getItemsFromTable(String table) {
        return LootTableInfo.getItemsForLootTableKey(VaultMod.id(table));
    }
    @Unique private static Set<ResourceLocation> vaultFilters$getItemsFromTable(Set<String> tables) {
        Set<ResourceLocation> allItems = new HashSet<>();
        tables.forEach( table -> {
            allItems.addAll(LootTableInfo.getItemsForLootTableKey(VaultMod.id(table)));
        });
        return allItems;
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
