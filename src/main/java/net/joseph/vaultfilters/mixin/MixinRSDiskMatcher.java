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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(value = ItemStorageDisk.class, remap = false)
public abstract class MixinRSDiskMatcher {
    @Final
    @Shadow
    private Multimap<Item, ItemStack> stacks;

    @Shadow
    private int itemCount;

    @Shadow
    protected abstract void onChanged();

    @Inject(method = "extract(Lnet/minecraft/world/item/ItemStack;IILcom/refinedmods/refinedstorage/api/util/Action;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    public void checkFilter(ItemStack stack, int size, int flags, Action action, CallbackInfoReturnable<ItemStack> cir) {



        if (stack.getItem() instanceof FilterItem) {
            Collection<ItemStack> finalstack = stacks.get(ModItems.SMALL_CHARM);
            finalstack.addAll(stacks.get(ModItems.LARGE_CHARM));
            finalstack.addAll(stacks.get(ModItems.GRAND_CHARM));
            finalstack.addAll(stacks.get(ModItems.MAJESTIC_CHARM));

            finalstack.addAll(stacks.get(ModItems.TRINKET));
            finalstack.addAll(stacks.get(ModItems.INSCRIPTION));
            finalstack.addAll(stacks.get(ModItems.JEWEL));

            finalstack.addAll(stacks.get(ModItems.CHESTPLATE));
            finalstack.addAll(stacks.get(ModItems.HELMET));
            finalstack.addAll(stacks.get(ModItems.BOOTS));
            finalstack.addAll(stacks.get(ModItems.LEGGINGS));

            finalstack.addAll(stacks.get(ModItems.SWORD));
            finalstack.addAll(stacks.get(ModItems.AXE));

            finalstack.addAll(stacks.get(ModItems.FOCUS));
            finalstack.addAll(stacks.get(ModItems.WAND));
            finalstack.addAll(stacks.get(ModItems.SHIELD));
            for (ItemStack otherStack : finalstack) {
                if (API.instance().getComparer().isEqual(otherStack, stack, flags)) {
                    if (size > otherStack.getCount()) {
                        size = otherStack.getCount();
                    }

                    if (action == Action.PERFORM) {
                        if (otherStack.getCount() - size == 0) {
                            stacks.remove(otherStack.getItem(), otherStack);
                        } else {
                            otherStack.shrink(size);
                        }

                        itemCount -= size;

                        onChanged();
                    }

                    cir.setReturnValue(ItemHandlerHelper.copyStackWithSize(otherStack, size));
                }
            }

            cir.setReturnValue(ItemStack.EMPTY); ;
        }
        }
    }
