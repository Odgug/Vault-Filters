package net.joseph.vaultfilters.attributes.backpacks;

import com.sun.jna.platform.unix.X11;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.api.CapabilityBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.injection.At;

import java.util.concurrent.atomic.AtomicReference;

public class HasUUIDAttribute extends StringAttribute {
    public HasUUIDAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        return getBackpackUUID(itemStack);
    }

    @Override
    public String getTranslationKey() {
        return "backpack_has_uuid";
    }

    public static String getBackpackUUID(ItemStack stack) {
        final AtomicReference<String> uuid = new AtomicReference<>("");
        if(stack.getItem() instanceof BackpackItem backpack) {
            stack.getCapability(CapabilityBackpackWrapper.getCapabilityInstance()).ifPresent(iBackpackWrapper -> {
                if(iBackpackWrapper.getContentsUuid().isPresent()) {
                    uuid.set(iBackpackWrapper.getContentsUuid().get().toString());
                }
            });
        }


        return uuid.get();
    }
}
