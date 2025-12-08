package net.joseph.vaultfilters.attributes.old.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LegendaryPrefixAttribute extends AffixAttribute {
    public LegendaryPrefixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return VaultGearModifier.AffixType.PREFIX;
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
        return "legendary_prefix";
    }

    @Override
    public String getLegacyKey() {
        return "legendaryPrefix";
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