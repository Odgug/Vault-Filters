package net.joseph.vaultfilters.attributes.old.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PrefixAttribute extends AffixAttribute {
    public PrefixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.PREFIX;
    }

    @Override
    public String getNBTKey() {
        return "prefix";
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        return new ArrayList<>();
    }
    @Override
    public boolean canRead(CompoundTag nbt) {
        return false;
    }
}