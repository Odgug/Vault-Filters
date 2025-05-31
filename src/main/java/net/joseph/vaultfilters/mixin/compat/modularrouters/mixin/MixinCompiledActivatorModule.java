package net.joseph.vaultfilters.mixin.compat.modularrouters.mixin;

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
import net.joseph.vaultfilters.attributes.packs.CardPackChooseAttribute;
import net.joseph.vaultfilters.attributes.pouch.JewelPouchChooseAttribute;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = CompiledActivatorModule.class, remap = false)
public abstract class MixinCompiledActivatorModule {

    @Inject(method = "doUseItem", at = @At("HEAD"), cancellable = true)
    private void vaultOpenables(ModularRouterBlockEntity router, FakePlayer fakePlayer, CallbackInfoReturnable<Boolean> cir) {
        ItemStack routerStack = router.getBufferItemStack();
        if (routerStack.getItem() instanceof BoosterPackItem) {
            if (!vault_Filters$shouldChooseCard(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers())) {
                return;
            }
            var outcomes = BoosterPackItem.getOutcomes(routerStack);
            if (outcomes == null || outcomes.isEmpty()) {
                return;
            }
            var filter = ((AccessorCompiledModule) this).getFilter();
            for (ItemStack outcome : outcomes) {
                if (filter.test(outcome)) {
                    fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, outcome.copy());
                    cir.setReturnValue(true);
                    return;
                }
            }
        }

        if (routerStack.getItem() instanceof JewelPouchItem) {
            if (!vault_Filters$shouldChooseJewel(((AccessorFilter)((AccessorCompiledModule) this).getFilter()).getMatchers())) {
                return;
            }
            var outcomes = JewelPouchItem.getJewels(routerStack);
            if (outcomes.isEmpty()) {
                return;
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
        }
    }

    @Unique private boolean vault_Filters$shouldChooseCard(List<IItemMatcher> matchers) {
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
