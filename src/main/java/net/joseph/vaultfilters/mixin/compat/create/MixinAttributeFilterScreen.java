package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.AttributeFilterScreen;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.attributes.abstracts.VaultAttribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(AttributeFilterScreen.class)
public class MixinAttributeFilterScreen {
    @Redirect(at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/filter/ItemAttribute;listAttributesOf(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;)Ljava/util/List;"), method = "referenceItemChanged", remap = false)
    public List<ItemAttribute> listAttributes(ItemAttribute instance, ItemStack stack, Level world) {
        if (!VaultFilters.serverHasVaultFilters && instance instanceof VaultAttribute<?>) {
            return List.of();
        }
        return instance.listAttributesOf(stack, world);
    }
}
