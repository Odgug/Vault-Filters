package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.item.InfusedCatalystItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CatalystModifierAttribute extends StringAttribute {
    protected CatalystModifierAttribute(String value) {
        super(value);
    }

    /**
     * runs once for every {@link VaultModifier}
     * @return if an attribute should be made for it
     */
    public boolean shouldList(VaultModifier modifier) {
        return true;
    }

    /**
     * runs once for every {@link VaultModifier} that should have a modifier based on it
     * @return an attribute based on the modifier
     * doesn't add anything if the result is null
     */
    public ItemAttribute withValue(VaultModifier modifier) {
        String name = getName(modifier);
        return name.isBlank() ? null : withValue(name);
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {
        return hasModifier(itemStack);
    }

    /**
     * runs once for every modifier
     * @return if the current attribute applies to it
     */
    public boolean checkModifier(VaultModifier modifier) {

        return this.value.equals(getName(modifier));
    }

    public boolean hasModifier(ItemStack itemStack) {
        if (itemStack.getItem() instanceof InfusedCatalystItem) {
            Iterable<VaultModifier> modifiers = getModifiers(itemStack);
            for (VaultModifier modifier : modifiers) {
                if (checkModifier(modifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return returns the name
     */
    public static <T> String getName(VaultModifier modifier) {
        return modifier.getDisplayName();
    }

    /**
     * @return {@link Iterable<VaultModifier>} with all modifiers on the catalyst
     * null will return all types
     */
    public Iterable<VaultModifier> getModifiers(ItemStack itemStack) {
        if (itemStack.getItem() instanceof InfusedCatalystItem) {
            List<ResourceLocation> resourceLocations = InfusedCatalystItem.getModifiers(itemStack);
            if (!resourceLocations.isEmpty()) {
                ArrayList<VaultModifier> vaultModifiers = new ArrayList<>();
                for (ResourceLocation resourceLocation : resourceLocations ) {
                    VaultModifierRegistry.getOpt(resourceLocation).ifPresent(vaultModifiers::add);
                }
                return vaultModifiers;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String getValue(ItemStack itemStack) {
        // Affix attributes can have multiple instances per item
        // So we override this here and return null as extending
        // classes do not need to implement it
        return null;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        for (VaultModifier modifier : getModifiers(itemStack)) {
            if (shouldList(modifier)) {
                ItemAttribute itemAtt = withValue(modifier);
                if (itemAtt != null) {attributes.add(itemAtt);}
            }
        }
        return attributes;
    }
}