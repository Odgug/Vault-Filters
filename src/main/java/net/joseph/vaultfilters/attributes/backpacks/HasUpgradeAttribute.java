package net.joseph.vaultfilters.attributes.backpacks;

import net.joseph.vaultfilters.attributes.abstracts.BooleanAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.joseph.vaultfilters.attributes.abstracts.StringListAttribute;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;

import java.util.ArrayList;
import java.util.List;

public class HasUpgradeAttribute extends StringListAttribute {


    public HasUpgradeAttribute(String value) {
        super(value);
    }

    @Override
    public String getTranslationKey() {
        return "backpack_has_upgrade";
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return "";
    }

    public static List<String> getUpgrades(ItemStack stack) {
        List<String> upgradeNames = new ArrayList<String>();
        if(stack.getItem() instanceof BackpackItem backpack) {
            stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).ifPresent(iBackpackWrapper -> {
                for(IUpgradeWrapper wrapper : iBackpackWrapper.getUpgradeHandler().getSlotWrappers().values()) {

                    upgradeNames.add(wrapper.getUpgradeStack().getDisplayName().getString().replaceAll("([*]*)", "").trim());
                }
            });
        }
        return upgradeNames;
    }

    @Override
    public List<String> getValues(ItemStack itemStack) {
        return getUpgrades(itemStack);
    }
}
