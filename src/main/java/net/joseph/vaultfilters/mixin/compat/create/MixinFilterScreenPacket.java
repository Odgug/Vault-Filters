package net.joseph.vaultfilters.mixin.compat.create;


import com.simibubi.create.content.logistics.filter.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = FilterScreenPacket.class)
public class MixinFilterScreenPacket {
    @Final
    @Shadow
    private FilterScreenPacket.Option option;


    @ModifyVariable(method = "lambda$handle$0", at = @At(value = "STORE", ordinal = 0), name = "c",remap = false)
    private FilterMenu modifyFilterMenu(FilterMenu c) {
        // Modify or use the FilterMenu instance `c` here
        if (this.option == FilterScreenPacket.Option.ADD_TAG) {
            ((FilterMenuAdvancedAccessor)c).vault_filters$setMatchAll(true);
        }
        if (this.option == FilterScreenPacket.Option.ADD_INVERTED_TAG) {
            ((FilterMenuAdvancedAccessor)c).vault_filters$setMatchAll(false);
        }
        return c;
    }


}
