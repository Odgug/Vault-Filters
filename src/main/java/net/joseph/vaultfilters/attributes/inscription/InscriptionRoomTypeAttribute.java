package net.joseph.vaultfilters.attributes.inscription;

import iskallia.vault.core.world.generator.layout.ArchitectRoomEntry;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.joseph.vaultfilters.attributes.abstracts.StringAttribute;
import net.minecraft.world.item.ItemStack;

public class InscriptionRoomTypeAttribute extends StringAttribute {
    public InscriptionRoomTypeAttribute(String value) {
        super(value);
    }

    @Override
    public String getValue(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof InscriptionItem)) {
            return null;
        }

        InscriptionData data = InscriptionData.from(itemStack);
        if (data.getEntries().isEmpty()) {
            return null;
        }

        ArchitectRoomEntry entry = data.getEntries().get(0).toRoomEntry();
        String color = String.valueOf(entry.getName().getStyle().getColor().getValue());
        return switch (color) {
            case "15769088", "16733695" -> "Challenge";
            case "16777215" -> "Common";
            case "7012096", "5635925" -> "Omega";
            default -> color;
        };
    }

    @Override
    public String getTranslationKey() {
        return "inscription_type";
    }

    @Override
    public String getLegacyKey() {
        return "roomType";
    }
}