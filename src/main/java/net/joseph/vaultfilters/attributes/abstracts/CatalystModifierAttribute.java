package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.item.InfusedCatalystItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CatalystModifierAttribute extends StringAttribute {
    protected CatalystModifierAttribute(String value) {
        super(value);
    }

    public String getName(VaultModifier<?> modifier) {
        return modifier.getDisplayName();
    }

    /**
     * @return {@link Iterable<VaultModifier>} with all modifiers on the catalyst
     * null will return all types
     */
    public Iterable<VaultModifier<?>> getModifiers(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof InfusedCatalystItem)) {
            return new ArrayList<>();
        }

        List<ResourceLocation> resourceLocations = InfusedCatalystItem.getModifiers(itemStack);
        if (resourceLocations.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<VaultModifier<?>> vaultModifiers = new ArrayList<>();
        for (ResourceLocation resourceLocation : resourceLocations) {
            VaultModifierRegistry.getOpt(resourceLocation).ifPresent(vaultModifiers::add);
        }
        return vaultModifiers;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof InfusedCatalystItem)) {
            return false;
        }

        Iterable<VaultModifier<?>> modifiers = getModifiers(itemStack);
        for (VaultModifier<?> modifier : modifiers) {
            if (this.value.equals(getName(modifier))) {
                return true;
            }
        }
        return false;
    }

    public ItemAttribute withValue(VaultModifier<?> modifier) {
        String name = getName(modifier);
        return name.isBlank() ? null : withValue(name);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        // There can be multiple modifiers on a catalyst
        // So we override this here and return null as extending
        // classes do not need to implement it
        return null;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        for (VaultModifier<?> modifier : getModifiers(itemStack)) {
            ItemAttribute itemAtt = withValue(modifier);
            if (itemAtt != null) {
                attributes.add(itemAtt);
            }
        }
        return attributes;
    }
}