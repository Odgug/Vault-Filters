package net.joseph.vaultfilters.attributes.charm;

import iskallia.vault.item.gear.CharmItem;
import iskallia.vault.item.gear.VaultCharmItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CharmUsesAttribute extends IntAttribute {
    public CharmUsesAttribute(Integer value) {
        super(value);
    }

    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        try
        {
            if (itemStack.getItem() instanceof VaultCharmItem charm) {
                return VaultCharmItem.isIdentified(itemStack) ? charm.getUses(itemStack) - charm.getUsedVaults(itemStack).size() : null;
            }
            if (CharmItem.isIdentified(itemStack)) {
                return CharmItem.getUses(itemStack) - CharmItem.getUsedVaults(itemStack).size();
            }
            return null;
        }
        catch (LinkageError e)
        {
            return null;
        }
    }

    @Override
    public String getNBTKey() {
        return "charm_uses";
    }

    @Override
    public String getLegacyKey() {
        return "charmUses";
    }
}