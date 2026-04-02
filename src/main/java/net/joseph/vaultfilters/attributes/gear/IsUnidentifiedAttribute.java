package net.joseph.vaultfilters.attributes.gear;

import iskallia.vault.gear.VaultGearState;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.IdentifiableItem;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.TemporalShardItem;
import iskallia.vault.item.gear.TrinketItem;
import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.minecraft.world.item.ItemStack;

public class IsUnidentifiedAttribute extends BooleanAttribute {
    public IsUnidentifiedAttribute(Boolean value) {
        super(true);
    }

    @Override
    public Boolean getValue(ItemStack stack) {
        return isUnidentified(stack);
    }

    public static Boolean isUnidentified(ItemStack stack) {
        if (stack.getItem() instanceof VaultGearItem) {
            return VaultGearData.read(stack).getState() == VaultGearState.UNIDENTIFIED;
        } else if (stack.getItem() instanceof TrinketItem) {
            return !TrinketItem.isIdentified(stack);
        } else if (stack.getItem() instanceof CharmItem) {
            return !CharmItem.isIdentified(stack);
        } else if (stack.getItem() instanceof TemporalShardItem) {
            return TemporalShardItem.isIdentified(stack);
        } else if (stack.getItem() instanceof IdentifiableItem identifiableItem) {
            VaultGearState state = identifiableItem.getState(stack);
            return state == VaultGearState.UNIDENTIFIED;
        }
        return false;
    }

    @Override
    public String getNBTKey() {
        return "is_unidentified";
    }

    @Override
    public String getLegacyKey() {
        return "unidentified";
    }
}