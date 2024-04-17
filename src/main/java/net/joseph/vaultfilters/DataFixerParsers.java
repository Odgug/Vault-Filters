package net.joseph.vaultfilters;

import net.minecraft.nbt.CompoundTag;

public class DataFixerParsers {
    public static boolean isNumber(String num) {
        char c = num.charAt(0);
        int ascii = c;
        if (ascii >= 48 && ascii <= 57) {
            return true;
        }
        return false;
    }

    public static double getDoubleValue(String modifier) {
        int firstSpace = modifier.indexOf(' ');
        int lastSpace = modifier.lastIndexOf(' ');
        if (firstSpace == lastSpace) {
            return 1;
        }
        int startIndex = 0;
        if (modifier.contains("Cloud")) {
            String cloudNumber = modifier.substring(firstSpace+1,lastSpace);
            return switch (cloudNumber) {
                case "II" -> 2;
                case "III" -> 3;
                case "IV" -> 4;
                case "V" -> 5;
                default -> 1;
            };
        }
        boolean containsPrecentage = modifier.contains("%");
        if (containsPrecentage) {
            firstSpace = firstSpace-1;
        }
        if (modifier.charAt(0) == '+') {
            startIndex = 1;
        }
        String number = modifier.substring(startIndex,firstSpace);
        if (number.isBlank()) {
            return 1;
        }
        return containsPrecentage ? Double.parseDouble(number) / 100 : Double.parseDouble(number);
    }
    public static String getNameFromString(String modifier) {

        int firstSpace = modifier.indexOf(' ');
        if (modifier.contains("Cloud")) {
            int lastSpace = modifier.lastIndexOf(' ');

            if (firstSpace == lastSpace) {
                return (modifier.substring(1));
            }
            return modifier.substring(1,firstSpace) + modifier.substring(lastSpace);
        }
        String name = modifier.substring(firstSpace+1);

        if (name.substring(0,3).equals("to ")) {
            return name.substring(3);
        }
        return name;
    }
    public static byte getTypeFromName(String name) {
       switch (name) {
           case "Armor":
               return CompoundTag.TAG_INT;
           case "Mana":
               return CompoundTag.TAG_BYTE;
           case "Durability":
               return CompoundTag.TAG_INT;
           case "Chaining Attack":
               return CompoundTag.TAG_INT;
           case "Size":
               return CompoundTag.TAG_INT;
           case "Hammer Size":
               return CompoundTag.TAG_INT;
           case "Attack Damage":
               return CompoundTag.TAG_DOUBLE;
           case "Attack Speed":
               return CompoundTag.TAG_DOUBLE;
           case "Reach":
               return CompoundTag.TAG_DOUBLE;
           case "Attack Range":
               return CompoundTag.TAG_DOUBLE;
           default:
               if (name.contains("Cloud") || name.contains("level to")) {
                   return CompoundTag.TAG_INT;
               }
               return CompoundTag.TAG_FLOAT;

       }
    }
   
}
