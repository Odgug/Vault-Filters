package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.CorruptedPrefixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.CorruptedSuffixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.PrefixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.SuffixAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CorruptedAffixAttribute extends AffixAttribute {
    public CorruptedAffixAttribute(String value) {
        super(value);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return null;
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
        return "corrupted_affix";
    }


    @Override
    public boolean canRead(CompoundTag nbt) {
        boolean original = super.canRead(nbt);
        if (original) {
            return true;
        }
        //datafixer
        String possiblename = "corrupted_prefix";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            CorruptedPrefixAttribute corruptedPrefixAttribute = (CorruptedPrefixAttribute) new CorruptedPrefixAttribute("dummy").readNBT(oldAttTag);
            String name = corruptedPrefixAttribute.value;

            //remove the old attribute
            nbt.remove(possiblename);

            //put in the new attribute
            CompoundTag newAttTag = new CompoundTag();
            ItemAttribute dummyAttribute = withValue(name);
            dummyAttribute.writeNBT(newAttTag);
            nbt.put(getNBTKey(),newAttTag);
            return true;
        }
        possiblename = "corrupted_suffix";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            CorruptedSuffixAttribute corruptedSuffixAttribute = (CorruptedSuffixAttribute) new CorruptedSuffixAttribute("dummy").readNBT(oldAttTag);
            String name = corruptedSuffixAttribute.value;

            //remove the old attribute
            nbt.remove(possiblename);

            //put in the new attribute
            CompoundTag newAttTag = new CompoundTag();
            ItemAttribute dummyAttribute = withValue(name);
            dummyAttribute.writeNBT(newAttTag);
            nbt.put(getNBTKey(),newAttTag);
            return true;
        }


        return false;
    }

}