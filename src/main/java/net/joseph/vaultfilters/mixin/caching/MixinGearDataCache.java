package net.joseph.vaultfilters.mixin.caching;

import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.GearDataCache;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.item.tool.JewelItem;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.caching.FilterCacheValue;
import net.joseph.vaultfilters.caching.IVFGearDataCache;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Mixin(value = GearDataCache.class, remap = false)
public class MixinGearDataCache implements IVFGearDataCache {
    @Shadow @Final private ItemStack stack;

    @Unique
    @Inject(method = {"createCache"}, at = @At("TAIL") ,locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void vaultfilters$extraCreateCache(ItemStack stack, CallbackInfo ci, GearDataCache cache) {
//        if (stack.getItem() instanceof VaultGearItem) {
//            ((IVFGearDataCache) cache).vaultfilters$hasLegendaryAttribute();
//            ((IVFGearDataCache) cache).vaultfilters$getExtraGearLevel();
//            if (!(stack.getItem() instanceof JewelItem)) {
//                ((IVFGearDataCache) cache).vaultfilters$getRepairSlots();
//                ((IVFGearDataCache) cache).vaultfilters$getUsedRepairSlots();
//            } else {
//                ((IVFGearDataCache) cache).vaultfilters$getExtraJewelSize();
//            }
//        }
    }

    @Unique
    @Override
    public boolean vaultfilters$testFilter(ItemStack filterStack) {
        // Create or get the InvokerGearDataCache object
        InvokerGearDataCache cache = (InvokerGearDataCache) this;

        // Define the cache key (a fixed string for this example)
        String cacheKey = "VF_filter";

        // Define the hash of the filterStack
        int filterStackHash = filterStack.hashCode();

        // Define a function to compute the FilterCacheValue based on filterStack.test
        Function<ItemStack, FilterCacheValue> computeFunction = stack -> {
            boolean testResult = VaultFilters.checkFilter(stack, filterStack, false);
            return new FilterCacheValue(filterStackHash, testResult ? (byte) 1 : (byte) 0);
        };

        // Call callQueryCache to get or compute the cache value
        FilterCacheValue cacheValue = cache.callQueryCache(
                cacheKey,
                FilterCacheValue::fromTag, // Conversion function from Tag to FilterCacheValue
                FilterCacheValue::toTag,   // Conversion function from FilterCacheValue to Tag
                null,                      // Default value is null
                Function.identity(),       // Identity function
                stack -> {
                    // Compute new value if the tag is null or its hash doesn't match
                    return computeFunction.apply(stack);
                }
        );

        // Check if cacheValue is null
        if (cacheValue == null) {
            // Handle the case where the cache does not contain an entry
            // Typically you might log an error, or compute the value again
            // In this case, assuming false as a default behavior
            return false;
        }

        // Return true if the cached value is 1, false otherwise
        return cacheValue.getValue() == 1;
    }


    @Unique
    @Override
    public boolean vaultfilters$hasLegendaryAttribute() {
        return ((InvokerGearDataCache) this)
                .callQueryCache("VF_leg", tag -> ((ByteTag) tag).getAsByte(), ByteTag::valueOf, null, Function.identity(), stack -> {
                    VaultGearData data = VaultGearData.read(stack);
                    List<VaultGearModifier<?>> affixes = new ArrayList<>();
                    affixes.addAll(data.getModifiers(VaultGearModifier.AffixType.PREFIX));
                    affixes.addAll(data.getModifiers(VaultGearModifier.AffixType.SUFFIX));
                    boolean legendaryModifier = affixes.stream().anyMatch(prefix -> prefix.getCategory() == VaultGearModifier.AffixCategory.LEGENDARY);
                    return legendaryModifier ? (byte) 1 : (byte) 0;
                }) == 1;
    }


    @Unique
    @Override
    public int vaultfilters$getRepairSlots() {
        return ((InvokerGearDataCache) this).callQueryIntCache("VF_rep", 0, stack -> {
            if (stack.getItem() instanceof VaultGearItem && !(stack.getItem() instanceof JewelItem)) {
                VaultGearData data = VaultGearData.read(stack);
                return data.getRepairSlots();
            }
            return null;
        });
    }

    @Unique
    @Override
    public int vaultfilters$getUsedRepairSlots() {
        return ((InvokerGearDataCache) this).callQueryIntCache("VF_repU", 0, stack -> {
            if (stack.getItem() instanceof VaultGearItem && !(stack.getItem() instanceof JewelItem)) {
                VaultGearData data = VaultGearData.read(stack);
                return data.getUsedRepairSlots();
            }
            return null;
        });
    }

    @Unique
    @Override
    public int vaultfilters$getExtraGearLevel() {
        return ((InvokerGearDataCache)this).callQueryIntCache("extra_gear_level", 0, stack -> {
            VaultGearData data = VaultGearData.read(stack);
            return data.getItemLevel();
        });
    }

    @Unique
    @Override
    public Integer vaultfilters$getExtraJewelSize() {
        //use same tag key as bonne to avoid duplicating data
        return ((InvokerGearDataCache)this).callQueryIntCache("extra_jewel_size", 0, stack -> {
            if (stack.getItem() instanceof JewelItem) {
                VaultGearData data = VaultGearData.read(stack);
                return data.getFirstValue(ModGearAttributes.JEWEL_SIZE).orElse(null);
            } else {
                return null;
            }
        });
    }

}
