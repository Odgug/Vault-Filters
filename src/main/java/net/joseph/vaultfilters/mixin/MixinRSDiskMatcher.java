package net.joseph.vaultfilters.mixin;

import com.google.common.collect.Multimap;
import com.refinedmods.refinedstorage.api.util.Action;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.storage.disk.ItemStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.util.Comparer;
import com.simibubi.create.content.logistics.filter.FilterItem;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.gear.VaultArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import org.checkerframework.checker.units.qual.K;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;


@Mixin(value = ItemStorageDisk.class, remap = false)
public class MixinRSDiskMatcher {
    private static final Item[] VAULT_GEAR = {
            ModItems.HELMET,
            ModItems.CHESTPLATE,
            ModItems.LEGGINGS,
            ModItems.BOOTS,
            ModItems.SWORD,
            ModItems.AXE,
            ModItems.SHIELD,
            ModItems.IDOL_BENEVOLENT,
            ModItems.IDOL_MALEVOLENCE,
            ModItems.IDOL_OMNISCIENT,
            ModItems.IDOL_TIMEKEEPER,
            ModItems.JEWEL,
            ModItems.MAGNET,
            ModItems.WAND,
            ModItems.FOCUS,
            ModItems.SMALL_CHARM,
            ModItems.LARGE_CHARM,
            ModItems.GRAND_CHARM,
            ModItems.MAJESTIC_CHARM,
            ModItems.TRINKET,
            ModItems.INSCRIPTION,
    };
    @Redirect(method = "extract(Lnet/minecraft/world/item/ItemStack;IILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;get(Ljava/lang/Object;)Ljava/util/Collection;"))
    private Collection<ItemStack> getAllItemsIfFilter(Multimap<Item, ItemStack> instance, Object item) {
        if (item instanceof FilterItem) {
            // Need to get the filter item itself too, otherwise you won't be able to pick it up
            ArrayList<ItemStack> stacks = new ArrayList<>(instance.get((Item) item));
            for (var gearPiece: VAULT_GEAR) {
                stacks.addAll(instance.get(gearPiece));
            }
            return stacks;
        }
        return instance.get((Item) item);
    }
}