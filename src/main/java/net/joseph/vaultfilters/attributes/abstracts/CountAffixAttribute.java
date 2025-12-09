package net.joseph.vaultfilters.attributes.abstracts;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.BiFunction;

import static net.joseph.vaultfilters.attributes.abstracts.AffixAttribute.getName;

public abstract class CountAffixAttribute extends StringAttribute {
    private static final Map<Class<?>, BiFunction<String, Integer, ItemAttribute>> factories = new HashMap<>();
    protected final Integer count;

    protected CountAffixAttribute(String name, Integer count) {
        super(name);
        this.count = count;
    }

    public void register(BiFunction<String, Integer, ItemAttribute> factory) {
        factories.put(getClass(), factory);
        super.register();
    }

    public ItemAttribute withValue(String name, Integer count) {
        return factories.getOrDefault(getClass(), (o1, o2) -> null).apply(name, count);
    }

    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {
        List<ItemAttribute> attributes = new ArrayList<>();
        Set<String> alreadyChecked = new HashSet<>();
        for (VaultGearModifier<?> modifier : getModifiers(itemStack)) {
            if (shouldCheck(modifier)) {
                String name = getModifierString(modifier);
                if (name.isEmpty() || alreadyChecked.contains(name)) {
                    continue;
                }
                alreadyChecked.add(name);
                ItemAttribute itemAtt = withValue(name,countModifiers(itemStack,name));
                if (itemAtt != null) {attributes.add(itemAtt);}
            }
        }
        return attributes;
    }


    public abstract boolean shouldCheck(VaultGearModifier<?> modifier);

    public Iterable<VaultGearModifier<?>> getModifiers(ItemStack itemStack) {
        if (itemStack.getItem() instanceof VaultGearItem) {
            return VaultGearData.read(itemStack).getAllModifierAffixes();
        }
        return new ArrayList<>();
    }

    public int countModifiers(ItemStack itemStack, String name) {
        int count = 0;
        if (itemStack.getItem() instanceof VaultGearItem) {
            Iterable<VaultGearModifier<?>> modifiers = getModifiers(itemStack);
            for (VaultGearModifier<?> modifier : modifiers) {
                if (shouldCheck(modifier)) {
                    if (getModifierString(modifier).equals(name)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public String getModifierString(VaultGearModifier<?> modifier) {
        return getName(modifier);
    }
    @Override
    public boolean appliesTo(ItemStack itemStack) {
        return countModifiers(itemStack, this.value) >= this.count;
    }

    @Override
    public String getValue(ItemStack itemStack) {
        // Affix attributes can have multiple instances per item
        // So we override this here and return null as extending
        // classes do not need to implement it
        return null;
    }
    @Override
    public String getTranslationKey() {
        if (count == 1) {
            return getNBTKey() + "_single";
        }
        return getNBTKey() + "_plural";
    }

    @Override
    public Object[] getTranslationParameters() {
        String text = this.value;
            return new Object[]{text,count};
    }

    @Override
    public ItemAttribute readNBT(CompoundTag tag) {
        String key = this.getNBTKey();
        String countKey = key + "_count";
        return this.withValue(tag.getString(key),tag.getInt(countKey));
    }
    @Override
    public void writeNBT(CompoundTag tag) {
        String key = this.getNBTKey();
        String countKey = key + "_count";
        tag.putString(key,this.value);
        tag.putInt(countKey,this.count);
    }

}
