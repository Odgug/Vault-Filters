package net.joseph.vaultfilters.mixin.compat;

import com.google.common.collect.Multimap;
import com.refinedmods.refinedstorage.apiimpl.storage.disk.ItemStorageDisk;
import com.simibubi.create.content.logistics.filter.FilterItem;
import iskallia.vault.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Collection;


@Mixin(value = ItemStorageDisk.class, remap = false)
public class MixinRSDiskMatcher {
    @Unique
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
        if (item instanceof FilterItem filterItem) {
            // Need to get the filter item itself too, otherwise you won't be able to pick it up
            ArrayList<ItemStack> stacks = new ArrayList<>(instance.get(filterItem));
            for (Item gearPiece: VAULT_GEAR) {
                stacks.addAll(instance.get(gearPiece));
            }
            return stacks;
        }
        return instance.get((Item) item);
    }
}