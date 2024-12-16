package net.joseph.vaultfilters.mixin.compat.create;


import com.simibubi.create.content.logistics.filter.*;
import net.joseph.vaultfilters.access.FilterMenuAdvancedAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = FilterScreenPacket.class, remap = false)
public class MixinFilterScreenPacket {
    @Final @Shadow
    private FilterScreenPacket.Option option;

    @SuppressWarnings("target")
    @ModifyVariable(method = "lambda$handle$0(Lnet/minecraftforge/network/NetworkEvent$Context;)V", at = @At(value = "STORE", ordinal = 0), name = "c", remap = false)
    private FilterMenu modifyFilterMenu(FilterMenu c) {
        // Modify or use the FilterMenu instance `c` here
        if (this.option == FilterScreenPacket.Option.ADD_TAG) {
            ((FilterMenuAdvancedAccessor) c).vault_filters$setMatchAll(true);
        } else if (this.option == FilterScreenPacket.Option.ADD_INVERTED_TAG) {
            ((FilterMenuAdvancedAccessor) c).vault_filters$setMatchAll(false);
        }
        return c;
    }
}
