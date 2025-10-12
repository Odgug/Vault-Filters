package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = AbstractFilterMenu.class, remap = false)
public class MixinAbstractFilterMenu implements AbstractFilterMenuAdvancedAccessor {
    @Unique
    String name;
    @Override
    public String vault_filters$getName() {
        return this.name;
    }

    @Override
    public void vault_filters$setName(String name) {
        this.name = name;
    }
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
