package net.joseph.vaultfilters.mixin.other;

import com.simibubi.create.content.logistics.filter.FilterItem;
import iskallia.vault.research.ResearchTree;
import iskallia.vault.research.Restrictions;
import iskallia.vault.research.type.Research;
import net.joseph.vaultfilters.configs.VFServerConfig;
import net.joseph.vaultfilters.items.SelectItemAugment;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//Create filter integration for modular routers by radimous on GitHub rizek_ on Discord, massive thanks.

@Mixin(value = ResearchTree.class, remap = false)
public class MixinAttributeResearchBypass {
    @Redirect(method = "restrictedBy(Lnet/minecraft/world/item/ItemStack;Liskallia/vault/research/Restrictions$Type;)Ljava/lang/String;", at = @At(value = "INVOKE", target = "Liskallia/vault/research/type/Research;restricts(Lnet/minecraft/world/item/ItemStack;Liskallia/vault/research/Restrictions$Type;)Z"))
    public boolean researchOverride(Research instance, ItemStack stack, Restrictions.Type type) {
        if (stack.getItem() instanceof FilterItem || stack.getItem() instanceof SelectItemAugment){
            String name = VFServerConfig.RESEARCH_NAME.get();
            return instance.getName().equals(name);
        }
        return instance.restricts(stack,type);
    }
}
