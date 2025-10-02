package net.joseph.vaultfilters.items;

import net.joseph.vaultfilters.VaultFilters;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VFItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VaultFilters.MOD_ID);

    public static final RegistryObject<Item> JEWEL_POUCH_AUGMENT = ITEMS.register("jewel_pouch_augment", SelectItemAugment::new);
    public static final RegistryObject<Item> CARD_PACK_AUGMENT = ITEMS.register("card_pack_augment", SelectItemAugment::new);


}
