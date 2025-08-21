package net.joseph.vaultfilters.attributes.companion;

import iskallia.vault.item.CompanionItem;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class CompanionSkinAttribute extends StringAttribute {
    public CompanionSkinAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof CompanionItem)) {
            return null;
        }

        String skinName = CompanionItem.getSkinName(itemStack);

        if(skinName.equals("Steve")) {
            return null;
        }
        

        return skinName;
    }

    @Override
    public String getTranslationKey() {
        return "companion_skin_name";
    }
}
