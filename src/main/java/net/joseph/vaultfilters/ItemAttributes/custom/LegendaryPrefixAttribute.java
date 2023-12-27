package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.init.ModGearAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LegendaryPrefixAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new LegendaryPrefixAttribute("dummy"));
    }
    String prefixname;

    public LegendaryPrefixAttribute(String prefixname) {
        this.prefixname = prefixname;
    }

    public boolean hasAsLegendaryPrefix(ItemStack itemStack) {
        VaultGearData data = VaultGearData.read(itemStack);
        List<VaultGearModifier<?>> prefixes = data.getModifiers(VaultGearModifier.AffixType.PREFIX);

        for (int i = 0; i < prefixes.size(); i++) {
            if (prefixes.get(i).getAttribute().getReader().getModifierName().equals(this.prefixname)) {
                if (prefixes.get(i).getCategory() == VaultGearModifier.AffixCategory.LEGENDARY) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof VaultGearItem) {
            return (hasAsLegendaryPrefix(itemStack));
        }

        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof VaultGearItem) {
           VaultGearData data = VaultGearData.read(itemStack);
           List<VaultGearModifier<?>> prefixes = data.getModifiers(VaultGearModifier.AffixType.PREFIX);

           for (int i = 0; i < prefixes.size(); i++) {
               if (prefixes.get(i).getCategory() == VaultGearModifier.AffixCategory.LEGENDARY) {
                   atts.add(new LegendaryPrefixAttribute(prefixes.get(i).getAttribute().getReader().getModifierName()));
               }
           }
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "legendary_prefix";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{prefixname};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("legendaryPrefix", this.prefixname);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new LegendaryPrefixAttribute(nbt.getString("legendaryPrefix"));
    }
}