package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.core.data.key.FieldKey;
import iskallia.vault.core.world.generator.layout.ArchitectRoomEntry;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import iskallia.vault.core.world.generator.layout.ArchitectRoomEntry.Type;
import java.util.ArrayList;
import java.util.List;

public class InscriptionRoomTypeAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new InscriptionRoomTypeAttribute("dummy"));
    }
    String type;
    public static String getType(ItemStack itemStack) {
        InscriptionData data = InscriptionData.from(itemStack);
        if (data.getEntries().size() == 0) {
            return "Empty";
        }
        ArchitectRoomEntry entry = data.getEntries().get(0).toRoomEntry();
        String color = String.valueOf(entry.getName().getStyle().getColor().getValue());
        if (color.equals("15769088")) {
            return "Challenge";
        }
        if (color.equals("16777215")) {
            return "Common";
        }
        if (color.equals("7012096")) {
            return "Omega";
        }
        return color;
    }

    public InscriptionRoomTypeAttribute(String type) {
        this.type = type;
    }



    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof InscriptionItem) {
            return (getType(itemStack).equals(type));
        }
        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof InscriptionItem) {
           if (getType(itemStack).equals("Empty")) {
               return atts;
           }
               atts.add(new InscriptionRoomTypeAttribute(getType(itemStack)));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "inscription_type";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{type};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("roomType", this.type);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        if (nbt.getString("RoomType").equals("7012096")) {
            return new InscriptionRoomTypeAttribute("Omega");
        }
        return new InscriptionRoomTypeAttribute(nbt.getString("roomType"));
    }
}