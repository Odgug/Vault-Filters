package net.joseph.vaultfilters.attributes.old.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LegendarySuffixAttribute extends AffixAttribute {
    public LegendarySuffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.SUFFIX;
    }

    @Override
    public boolean shouldList(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY);
    }

    @Override
    public boolean checkModifier(VaultGearModifier<?> modifier) {

        return modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY) && this.value.equals(getName(modifier));
    }
    @Override
    public String getNBTKey() {
        return "legendary_suffix";
    }

    @Override
    public String getLegacyKey() {
        return "legendarySuffix";
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