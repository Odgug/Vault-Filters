package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.AbstractFilterMenu;
import com.simibubi.create.foundation.gui.menu.GhostItemMenu;
import net.joseph.vaultfilters.VFNestedFilters;
import net.joseph.vaultfilters.VaultFilters;
import net.joseph.vaultfilters.access.AbstractFilterMenuAdvancedAccessor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = AbstractFilterMenu.class, remap = false)
public abstract class MixinAbstractFilterMenu extends GhostItemMenu<ItemStack> implements AbstractFilterMenuAdvancedAccessor {

    @Unique
    String name;

    protected MixinAbstractFilterMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
        //uncalled
    }

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
    @Override
    public void removed(Player player) {
        super.removed(player);

        if (player instanceof ServerPlayer serverPlayer) {
            MinecraftServer server = serverPlayer.server;
            server.execute(() -> {
                if (!(serverPlayer.containerMenu instanceof AbstractFilterMenu)) {
                    AbstractFilterMenu instance = (AbstractFilterMenu) (Object) this;
                    VFNestedFilters.popAll(serverPlayer,instance.contentHolder);
                }
            });

        }

    }
    //debug for nested filters
    @Overwrite
    public boolean stillValid(Player player) {
        return true;
    }
}
