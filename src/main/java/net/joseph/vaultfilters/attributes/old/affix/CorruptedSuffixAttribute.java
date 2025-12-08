package net.joseph.vaultfilters.attributes.old.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CorruptedSuffixAttribute extends AffixAttribute {
    public CorruptedSuffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.CORRUPTED);
    }

    @Override
    public boolean checkModifier(VaultGearModifier<?> modifier) {

        return modifier.hasCategory(VaultGearModifier.AffixCategory.CORRUPTED) && this.value.equals(getName(modifier));
    }
    @Override
    public String getNBTKey() {
        return "corrupted_suffix";
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