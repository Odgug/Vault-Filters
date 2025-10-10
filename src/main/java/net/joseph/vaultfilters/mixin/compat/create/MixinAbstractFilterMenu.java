package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = AbstractFilterMenu.class, remap = false)
public class MixinAbstractFilterMenu {
    /**
     * @author
     * @reason
     */
    //debug for nested filters
    @Overwrite
    public boolean stillValid(Player player) {
        return true;
    }
}
