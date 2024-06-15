package net.joseph.vaultfilters.caching;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class FilterCacheValue {
    private final int hash;
    private final byte value;

    public FilterCacheValue(int hash, byte value) {
        this.hash = hash;
        this.value = value;
    }

    public int getHash() {
        return hash;
    }

    public byte getValue() {
        return value;
    }
    public static FilterCacheValue fromTag(Tag tag) {
        if (tag instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag) tag;
            int hash = compoundTag.getInt("hash");
            byte value = compoundTag.getByte("value");
            return new FilterCacheValue(hash, value);
        }
        // Handle case where tag is not of expected type (though it should be)
        return new FilterCacheValue(0, (byte) 0); // Default or error case
    }

    // Method to convert FilterCacheValue to a Tag
    public Tag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("hash", this.hash);
        tag.putByte("value", this.value);
        return tag;
    }
}
