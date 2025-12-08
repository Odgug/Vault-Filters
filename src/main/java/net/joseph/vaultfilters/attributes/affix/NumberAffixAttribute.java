package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.NumberAffixAbstractAttribute;
import net.joseph.vaultfilters.attributes.old.affix.NumberPrefixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.NumberSuffixAttribute;
import net.minecraft.nbt.CompoundTag;

public class NumberAffixAttribute extends NumberAffixAbstractAttribute {
    public NumberAffixAttribute(String value, String simpleName, Number level) {
        super(value, simpleName, level);
    }

    @Override
    public VaultGearModifier.AffixType getAffixType() {
        return null;
    }

    @Override
    public String getNBTKey() {
        return "affix_number";
    }

    @Override
    public boolean canRead(CompoundTag nbt) {
        boolean original = super.canRead(nbt);
        if (original) {
            return true;
        }
        //datafixer
        String possiblename = "prefix_number";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            NumberPrefixAttribute numberPrefixAttribute = (NumberPrefixAttribute) new NumberPrefixAttribute("dummy","dummy",1).readNBT(oldAttTag);
            String displayName = numberPrefixAttribute.value;
            String simpleName = numberPrefixAttribute.name;
            Number level = numberPrefixAttribute.level;
            //remove the old attribute
            nbt.remove(possiblename);

            //put in the new attribute
            CompoundTag newAttTag = new CompoundTag();
            ItemAttribute dummyAttribute = withValue(displayName, simpleName,level);
            dummyAttribute.writeNBT(newAttTag);
            nbt.put(getNBTKey(),newAttTag);
            return true;
        }
        possiblename = "suffix_number";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            NumberSuffixAttribute numberSuffixAttribute = (NumberSuffixAttribute) new NumberSuffixAttribute("dummy","dummy",1).readNBT(oldAttTag);
            String displayName = numberSuffixAttribute.value;
            String simpleName = numberSuffixAttribute.name;
            Number level = numberSuffixAttribute.level;
            //remove the old attribute
            nbt.remove(possiblename);

            //put in the new attribute
            CompoundTag newAttTag = new CompoundTag();
            ItemAttribute dummyAttribute = withValue(displayName, simpleName,level);
            dummyAttribute.writeNBT(newAttTag);
            nbt.put(getNBTKey(),newAttTag);
            return true;
        }


        return false;
    }
}