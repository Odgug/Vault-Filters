package net.joseph.vaultfilters.attributes.affix;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import net.joseph.vaultfilters.attributes.abstracts.AffixAttribute;
import net.joseph.vaultfilters.attributes.abstracts.CountAffixAttribute;
import net.minecraft.nbt.CompoundTag;

import static net.joseph.vaultfilters.attributes.abstracts.AffixAttribute.getName;

public class ModifierGroupAttribute extends CountAffixAttribute {
    public ModifierGroupAttribute(String name, Integer count) {
        super(name,count);
    }

    @Override
    public boolean shouldCheck(VaultGearModifier<?> modifier) {
        return true;
    }

    @Override
    public String getModifierString(VaultGearModifier<?> modifier) {
        return modifier.getModifierGroup();
    }
    @Override
    public Object[] getTranslationParameters() {
        String text = this.value.substring(0,3).equals("Mod") || this.value.substring(0,3).equals("mod")  ? this.value.substring(3) : this.value;
            return new Object[]{text,count};
    }
    @Override
    public String getNBTKey() {
        return "modifier_group";
    }

    @Override
    public ItemAttribute readNBT(CompoundTag tag) {
        String key = this.getNBTKey();
        String countKey = key + "_count";
        if (!(tag.contains(countKey))) {
            tag.putInt(countKey,1);
            return this.withValue(tag.getString(key),1);
        }
        return this.withValue(tag.getString(key),tag.getInt(countKey));
    }
}
