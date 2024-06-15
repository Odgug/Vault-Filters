package net.joseph.vaultfilters.caching;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterCacheValue {
    private final List<HashEntry> hashEntries;
    public static int maxHashes = 2;
    public FilterCacheValue(List<HashEntry> hashEntries) {
        this.hashEntries = hashEntries;
    }

    public List<HashEntry> getHashEntries() {
        return hashEntries;
    }

    public static FilterCacheValue fromTag(Tag tag) {
        if (tag instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag) tag;
            ListTag listTag = compoundTag.getList("hashEntries", Tag.TAG_COMPOUND);
            List<HashEntry> hashEntries = new ArrayList<>();
            for (Tag entryTag : listTag) {
                CompoundTag entryCompoundTag = (CompoundTag) entryTag;
                int hash = entryCompoundTag.getInt("hash");
                byte value = entryCompoundTag.getByte("value");
                hashEntries.add(new HashEntry(hash, value));
            }
            return new FilterCacheValue(hashEntries);
        }
        return new FilterCacheValue(new ArrayList<>()); // Default or error case
    }

    public Tag toTag() {
        CompoundTag tag = new CompoundTag();
        ListTag listTag = new ListTag();
        for (HashEntry entry : hashEntries) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putInt("hash", entry.getHash());
            entryTag.putByte("value", entry.getValue());
            listTag.add(entryTag);
        }
        tag.put("hashEntries", listTag);
        return tag;
    }

    public static class HashEntry {
        private final int hash;
        private final byte value;

        public HashEntry(int hash, byte value) {
            this.hash = hash;
            this.value = value;
        }

        public int getHash() {
            return hash;
        }

        public byte getValue() {
            return value;
        }
    }
}
