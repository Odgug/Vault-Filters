package net.joseph.vaultfilters.attributes.artifact;

import iskallia.vault.block.VaultArtifactBlock;
import net.joseph.vaultfilters.attributes.abstracts.IntAttribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class ArtifactIDAttribute extends IntAttribute {
    public ArtifactIDAttribute(Integer value) {
        super(value);
    }

    @Override
    public NumComparator getComparator() {
        return NumComparator.EQUAL;
    }

    @Override
    public Integer getValue(ItemStack itemStack) {
        if(itemStack.getItem() instanceof BlockItem blockItem) {
            if(blockItem.getBlock() instanceof VaultArtifactBlock artifactBlock) {
                return artifactBlock.getOrder(itemStack);
            }
        }
        return null;
    }

    @Override
    public String getTranslationKey() {
        return "artifact_id";
    }
}
