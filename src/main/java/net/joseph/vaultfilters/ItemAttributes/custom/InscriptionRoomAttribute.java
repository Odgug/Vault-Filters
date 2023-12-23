package net.joseph.vaultfilters.ItemAttributes.custom;

import com.simibubi.create.content.logistics.filter.ItemAttribute;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.gear.item.VaultGearItem;
import iskallia.vault.item.InscriptionItem;
import iskallia.vault.item.data.InscriptionData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InscriptionRoomAttribute implements ItemAttribute {

    public static void register() {
        ItemAttribute.register(new InscriptionRoomAttribute("dummy"));
    }
    String room;
    public static String getRoom(ItemStack itemStack) {
        InscriptionData data = InscriptionData.from(itemStack);
        if (data.getEntries().size() == 0) {
            return "Empty";
        }
        return data.getEntries().get(0).toRoomEntry().getName().getString();
    }

    public InscriptionRoomAttribute(String room) {
        this.room = room;
    }



    @Override
    public boolean appliesTo(ItemStack itemStack) {

        if (itemStack.getItem() instanceof InscriptionItem) {
            return (getRoom(itemStack).equals(room));
        }
        return false;
    }

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack itemStack) {

        List<ItemAttribute> atts = new ArrayList<>();
       if (itemStack.getItem() instanceof InscriptionItem) {
               atts.add(new InscriptionRoomAttribute(getRoom(itemStack)));
       }
        return atts;
    }

    @Override
    public String getTranslationKey() {
        return "inscription_room";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{room};
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putString("room", this.room);
    }

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new InscriptionRoomAttribute(nbt.getString("room"));
    }
}