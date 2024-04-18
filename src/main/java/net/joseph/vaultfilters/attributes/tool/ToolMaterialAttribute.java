package net.joseph.vaultfilters.attributes.tool;

import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.item.tool.ToolItem;
import iskallia.vault.item.tool.ToolMaterial;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import static iskallia.vault.item.tool.ToolItem.getMaterial;

public class ToolMaterialAttribute extends StringAttribute {
    public ToolMaterialAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack)  {
        if (!(itemStack.getItem() instanceof ToolItem)) {
            return null;
        }
        ToolMaterial material = getMaterial(itemStack);
        return material.getDescription();
    }
    @Override
    public Object[] getTranslationParameters() {

        return new Object[]{new TranslatableComponent(this.value).getString()};
    }
    @Override
    public String getTranslationKey() {
        return "tool_material";
    }
}
