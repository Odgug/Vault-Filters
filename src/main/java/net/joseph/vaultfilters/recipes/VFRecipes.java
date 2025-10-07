package net.joseph.vaultfilters.recipes;

import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VFRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, VaultFilters.MOD_ID);

    public static final RegistryObject<SimpleRecipeSerializer<AttributeFilterCopyRecipe>> ATTRIBUTE_FILTER_COPY =
            SERIALIZERS.register("attribute_filter_copy", () ->
                    new SimpleRecipeSerializer<>(AttributeFilterCopyRecipe::new));

    public static final RegistryObject<SimpleRecipeSerializer<ListFilterCopyRecipe>> LIST_FILTER_COPY =
            SERIALIZERS.register("list_filter_copy", () ->
                    new SimpleRecipeSerializer<>(ListFilterCopyRecipe::new));
    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
