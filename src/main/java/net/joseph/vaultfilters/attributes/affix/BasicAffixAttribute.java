package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.CountAffixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.PrefixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.SuffixAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class BasicAffixAttribute extends CountAffixAttribute {
    public BasicAffixAttribute(String name, Integer count) {
        super(name, count);
    }

    @Override
    public boolean shouldCheck(VaultGearModifier<?> modifier) {
        return true;
    }

    @Override
    public String getNBTKey() {
        return "affix";
    }



    @Override
    public boolean canRead(CompoundTag nbt) {
        boolean original = super.canRead(nbt);
        if (original) {
            return true;
        }
        //datafixer
        String possiblename = "prefix";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            PrefixAttribute suffixAttribute = (PrefixAttribute) new PrefixAttribute("dummy").readNBT(oldAttTag);
            String name = suffixAttribute.value;

            //remove the old attribute
            nbt.remove(possiblename);

            //put in the new attribute
            CompoundTag newAttTag = new CompoundTag();
            ItemAttribute dummyAttribute = withValue(name, 1);
            dummyAttribute.writeNBT(newAttTag);
            nbt.put(getNBTKey(),newAttTag);
            return true;
        }
        possiblename = "suffix";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            SuffixAttribute suffixAttribute = (SuffixAttribute) new SuffixAttribute("dummy").readNBT(oldAttTag);
            String name = suffixAttribute.value;

            //remove the old attribute
            nbt.remove(possiblename);

            //put in the new attribute
            CompoundTag newAttTag = new CompoundTag();
            ItemAttribute dummyAttribute = withValue(name, 1);
            dummyAttribute.writeNBT(newAttTag);
            nbt.put(getNBTKey(),newAttTag);
            return true;
        }


        return false;
    }
}
