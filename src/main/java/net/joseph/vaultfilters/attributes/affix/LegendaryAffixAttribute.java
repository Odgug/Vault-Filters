package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.CountAffixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.LegendaryPrefixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.LegendarySuffixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.PrefixAttribute;
import net.joseph.vaultfilters.attributes.old.affix.SuffixAttribute;
import net.minecraft.nbt.CompoundTag;

public class LegendaryAffixAttribute extends CountAffixAttribute {
    public LegendaryAffixAttribute(String name, Integer count) {
        super(name, count);
    }

    @Override
    public boolean shouldCheck(VaultGearModifier<?> modifier) {
        return modifier.hasCategory(VaultGearModifier.AffixCategory.LEGENDARY);
    }

    @Override
    public String getNBTKey() {
        return "legendary_affix";
    }



    @Override
    public boolean canRead(CompoundTag nbt) {
        boolean original = super.canRead(nbt);
        if (original) {
            return true;
        }
        //datafixer
        String possiblename = "legendary_prefix";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            LegendaryPrefixAttribute legendaryPrefixAttribute = (LegendaryPrefixAttribute) new LegendaryPrefixAttribute("dummy").readNBT(oldAttTag);
            String name = legendaryPrefixAttribute.value;

            //remove the old attribute
            nbt.remove(possiblename);

            //put in the new attribute
            CompoundTag newAttTag = new CompoundTag();
            ItemAttribute dummyAttribute = withValue(name, 1);
            dummyAttribute.writeNBT(newAttTag);
            nbt.put(getNBTKey(),newAttTag);
            return true;
        }
        possiblename = "legendary_suffix";
        if (nbt.contains(possiblename)) {
            //get the attribute
            CompoundTag oldAttTag = nbt.getCompound(possiblename);
            LegendarySuffixAttribute legendarySuffixAttribute = (LegendarySuffixAttribute) new LegendarySuffixAttribute("dummy").readNBT(oldAttTag);
            String name = legendarySuffixAttribute.value;

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
