package net.joseph.vaultfilters.attributes.focus;

import iskallia.vault.config.gear.VaultGearTagConfig;
import iskallia.vault.gear.modification.operation.ReforgeAddTaggedModification;
import iskallia.vault.item.modification.ReforgeTagModificationFocus;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class FacetedFocusTypeAttribute extends StringAttribute {
    public FacetedFocusTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ReforgeTagModificationFocus focus)) {
            return null;
        }
        VaultGearTagConfig.ModTagGroup tag = ReforgeTagModificationFocus.getModifierTag(itemStack);
        if (tag != null) {
            return tag.getDisplayName();
        }
        return null;
    }

    @Override
    public String getNBTKey() {
        return "faceted_focus_type";
    }
}
