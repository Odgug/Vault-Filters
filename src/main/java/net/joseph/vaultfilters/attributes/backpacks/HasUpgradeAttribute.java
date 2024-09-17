package net.joseph.vaultfilters.attributes.backpacks;

import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringListAttribute;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;

import java.util.ArrayList;
import java.util.List;

public class HasUpgradeAttribute extends StringListAttribute {


    public HasUpgradeAttribute(String value) {
        super(value);
    }

    @Override
    public String getTranslationKey() {
        return "bag_upgrade";
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return "";
    }

    @Override
    public Object[] getTranslationParameters() {
        String modifiedItemName = this.value.replace("[", "").replace("]", "").trim();
        return new Object[]{modifiedItemName};
    }

    public static List<String> getUpgrades(ItemStack stack) {
        List<String> upgradeNames = new ArrayList<String>();
        if(stack.getItem() instanceof BackpackItem backpack) {
            if(stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).resolve().isPresent()) {
                IBackpackWrapper iBackpackWrapper = stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).resolve().get();
                for(IUpgradeWrapper wrapper : iBackpackWrapper.getUpgradeHandler().getSlotWrappers().values()) {
                    upgradeNames.add(wrapper.getUpgradeStack().getDisplayName().getString());
                }
            }
        }
        return upgradeNames;
    }

    @Override
    public List<String> getValues(ItemStack itemStack) {
        return getUpgrades(itemStack);
    }
}
