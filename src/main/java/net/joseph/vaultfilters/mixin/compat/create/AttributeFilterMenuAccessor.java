package net.joseph.vaultfilters.mixin.compat.create;

import com.simibubi.create.content.logistics.filter.AttributeFilterMenu;
import com.simibubi.create.content.logistics.filter.ItemAttribute;
import com.simibubi.create.foundation.utility.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = AttributeFilterMenu.class,remap = false)
public interface AttributeFilterMenuAccessor {
    @Accessor
    List<Pair<ItemAttribute, Boolean>> getSelectedAttributes();
}