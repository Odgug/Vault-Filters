package net.joseph.vaultfilters.mixin.compat.modularrouters.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import iskallia.vault.item.BoosterPackItem;
import iskallia.vault.item.JewelPouchItem;
import iskallia.vault.util.LootInitialization;
import iskallia.vault.world.data.PlayerVaultStatsData;
import me.desht.modularrouters.block.tile.ModularRouterBlockEntity;
import me.desht.modularrouters.logic.compiled.CompiledActivatorModule;
import me.desht.modularrouters.logic.filter.matchers.BulkItemMatcher;
import me.desht.modularrouters.logic.filter.matchers.IItemMatcher;
import me.desht.modularrouters.logic.filter.matchers.SimpleItemMatcher;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.attributes.old.CardPackChooseAttribute;
import net.joseph.vaultfilters.attributes.old.JewelPouchChooseAttribute;
import net.joseph.vaultfilters.items.VFItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(value = CompiledActivatorModule.class, remap = false)
public abstract class MixinCompiledActivatorModule {

    @ModifyExpressionValue(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lme/desht/modularrouters/logic/filter/Filter;test(Lnet/minecraft/world/item/ItemStack;)Z"
            )
    )
    private boolean activateOnPacks1(boolean original,ModularRouterBlockEntity router) {
        if (vault_Filters$shouldChooseJewel(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers()) && router.getBufferItemStack().getItem() instanceof JewelPouchItem) {
            return true;
        }
        if (vault_Filters$shouldChooseCard(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers()) && router.getBufferItemStack().getItem() instanceof BoosterPackItem)  {
            return true;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lme/desht/modularrouters/logic/filter/Filter;isBlacklist()Z"
            )
    )
    private boolean activateOnPacks2(boolean original,ModularRouterBlockEntity router) {
        if (vault_Filters$shouldChooseJewel(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers()) && router.getBufferItemStack().getItem() instanceof JewelPouchItem) {
            return true;
        }
        if (vault_Filters$shouldChooseCard(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers()) && router.getBufferItemStack().getItem() instanceof BoosterPackItem)  {
            return true;
        }
        return original;
    }

    @Inject(method = "doUseItem", at = @At("HEAD"), cancellable = true)
    private void vaultOpenables(ModularRouterBlockEntity router, FakePlayer fakePlayer, CallbackInfoReturnable<Boolean> cir) {
        ItemStack routerStack = router.getBufferItemStack();
        if (routerStack.getItem() instanceof BoosterPackItem packItem) {
            if (!vault_Filters$shouldChooseCard(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers())) {
                return;
            }
            var outcomes = BoosterPackItem.getOutcomes(routerStack);
            //boolean crackedPack = false;
            if (outcomes == null || outcomes.isEmpty()) {
                if (router.getLevel() != null) {
                    InteractionResultHolder<ItemStack> result = packItem.use(router.getLevel(), fakePlayer, InteractionHand.MAIN_HAND);
                    //fakePlayer.setItemInHand(InteractionHand.MAIN_HAND,result.getObject().copy());
                    outcomes = BoosterPackItem.getOutcomes(routerStack);
                    //crackedPack = true;
                }
            }
            var filter = ((AccessorCompiledModule) this).getFilter();
            for (ItemStack outcome : outcomes) {
                if (filter.test(outcome)) {
                    fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, outcome.copy());
                    cir.setReturnValue(true);
                    return;
                }
            }
//            if (crackedPack) {
//                cir.setReturnValue(true);
//                return;
//            }
        }

        if (routerStack.getItem() instanceof JewelPouchItem pouchItem) {
            if (!vault_Filters$shouldChooseJewel(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers())) {
                return;
            }
            var outcomes = JewelPouchItem.getJewels(routerStack);
            //boolean crackedJewel = false;
            if (outcomes.isEmpty()) {
                if (router.getLevel() != null) {
                    InteractionResultHolder<ItemStack> result = pouchItem.use(router.getLevel(), fakePlayer, InteractionHand.MAIN_HAND);
                    //fakePlayer.setItemInHand(InteractionHand.MAIN_HAND,result.getObject().copy());
                    outcomes = JewelPouchItem.getJewels(routerStack);
                    //crackedJewel = true;
                }
            }
            var filter = ((AccessorCompiledModule) this).getFilter();
            for (JewelPouchItem.RolledJewel outcome : outcomes) {
                ItemStack result = outcome.stack().copy();
                if (filter.test(result)) {
                    if (!outcome.identified()) {
                        int vaultLevel = JewelPouchItem.getStoredLevel(routerStack).orElseGet(() -> PlayerVaultStatsData.get(fakePlayer.getLevel()).getVaultStats(fakePlayer).getVaultLevel());
                        result = LootInitialization.initializeVaultLoot(result, vaultLevel);
                    }
                    fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, result.copy());
                    cir.setReturnValue(true);
                    return;
                }
            }
//            if (crackedJewel) {
//                cir.setReturnValue(true);
//                return;
//            }
        }
    }

    @Unique private boolean vault_Filters$shouldChooseCard(List<IItemMatcher> matchers) {
        if (((AccessorCompiledModule) this).getAugmentCounter().getAugmentCount(VFItems.CARD_PACK_AUGMENT.get()) > 0) {
            return true;
        }
        for (var matcher : matchers){
            if (matcher instanceof SimpleItemMatcher simpleItemMatcher){
                var stack = ((AccessorSimpleItemMatcher)simpleItemMatcher).getFilterStack();
                if (FilterItemStack.of(stack) instanceof FilterItemStack.AttributeFilterItemStack filterItemStack) {
                    for (var test : filterItemStack.attributeTests) {
                        if (test.getFirst() instanceof CardPackChooseAttribute && !test.getSecond()) {
                            return true;
                        }
                    }
                }
            }
            if (matcher instanceof BulkItemMatcher bulkItemMatcher){
                var stacks = ((AccessorBulkItemMatcher)bulkItemMatcher).getStacks();
                for (ItemStack stack : stacks) {
                    if (FilterItemStack.of(stack) instanceof FilterItemStack.AttributeFilterItemStack filterItemStack) {
                        for (var test : filterItemStack.attributeTests) {
                            if (test.getFirst() instanceof CardPackChooseAttribute && !test.getSecond()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Unique private boolean vault_Filters$shouldChooseJewel(List<IItemMatcher> matchers) {
        if (((AccessorCompiledModule) this).getAugmentCounter().getAugmentCount(VFItems.JEWEL_POUCH_AUGMENT.get()) > 0) {
            return true;
        }

        for (var matcher : matchers){
            if (matcher instanceof SimpleItemMatcher simpleItemMatcher){
                var stack = ((AccessorSimpleItemMatcher)simpleItemMatcher).getFilterStack();
                if (FilterItemStack.of(stack) instanceof FilterItemStack.AttributeFilterItemStack filterItemStack) {
                    for (var test : filterItemStack.attributeTests) {
                        if (test.getFirst() instanceof JewelPouchChooseAttribute && !test.getSecond()) {
                            return true;
                        }
                    }
                }
            }
            if (matcher instanceof BulkItemMatcher bulkItemMatcher){
                var stacks = ((AccessorBulkItemMatcher)bulkItemMatcher).getStacks();
                for (ItemStack stack : stacks) {
                    if (FilterItemStack.of(stack) instanceof FilterItemStack.AttributeFilterItemStack filterItemStack) {
                        for (var test : filterItemStack.attributeTests) {
                            if (test.getFirst() instanceof JewelPouchChooseAttribute && !test.getSecond()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
