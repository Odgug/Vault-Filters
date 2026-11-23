package net.joseph.vaultfilters.attributes.charm;

import iskallia.vault.item.gear.VaultCharmItem;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.ItemStack;

public class CharmReputationAttribute extends IntAttribute {
    public CharmReputationAttribute(Integer value) {
        super(value);
    }

    @Override
    public NumComparator getComparator() {
        return NumComparator.AT_LEAST;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        try {
            if (itemStack.getItem() instanceof VaultCharmItem && VaultCharmItem.isIdentified(itemStack)) {
                return VaultCharmItem.getGodReputation(itemStack);
            }
            return null;
        } catch (LinkageError e) {
            return null;
        }
    }

    @Override
    public String getNBTKey() {
        return "charm_reputation";
    }
}
