package net.joseph.vaultfilters.mixin.caching;

import iskallia.vault.gear.data.GearDataCache;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;

@Mixin(value = GearDataCache.class, remap = false)
public interface InvokerGearDataCache {
    @Invoker(
            value = "queryIntCache",
            remap = false
    )
    Integer callQueryIntCache(String var1, int var2, Function<ItemStack, Integer> var3);

    @Invoker(
            value = "queryCache",
            remap = false
    )
    <R, T> T callQueryCache(String var1, Function<Tag, R> var2, Function<R, Tag> var3, T var4, Function<R, T> var5, Function<ItemStack, R> var6);
}