package net.joseph.vaultfilters.mixin;

import com.simibubi.create.content.logistics.filter.FilterItem;
import iskallia.vault.research.ResearchTree;
import iskallia.vault.research.Restrictions;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Create filter integration for modular routers by radimous on GitHub rizek_ on Discord, massive thanks.

@Mixin(value = ResearchTree.class, remap = false)
public class MixinAttributeResearchBypass {
    @Inject(method = "restrictedBy(Lnet/minecraft/world/item/ItemStack;Liskallia/vault/research/Restrictions$Type;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    public void researchOverride(ItemStack item, Restrictions.Type restrictionType, CallbackInfoReturnable<String> cir) {
        if (item.getItem() instanceof FilterItem){
            cir.setReturnValue(null);
        }
    }
}
