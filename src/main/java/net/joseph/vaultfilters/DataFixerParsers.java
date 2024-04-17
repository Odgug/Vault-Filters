package net.joseph.vaultfilters;

import java.util.function.Function;

public class DataFixerParsers {
    public static String getModifierName(String modifier) {
        int firstSpace = modifier.indexOf(' ');
        if (modifier.contains("Cloud")) {
            int lastSpace = modifier.lastIndexOf(' ');

            if (firstSpace == lastSpace) {
                return (modifier.substring(1));
            }
            return modifier.substring(1, firstSpace) + modifier.substring(lastSpace);
        }

        String name = modifier.substring(firstSpace + 1);
        return name.startsWith("to ") ? name.substring(3) : name;
    }

    public static Number parseLevel(String modifierName, String modifier) {
        int firstSpace = modifier.indexOf(' ');
        int lastSpace = modifier.lastIndexOf(' ');
        if (firstSpace == lastSpace) {
            return 1;
        }

        int startIndex = 0;
        if (modifier.contains("Cloud")) {
            // Cloud Modifiers are formatted as "Fear III Cloud", to get III, we need the first and last space
            String cloudNumber = modifier.substring(firstSpace + 1, lastSpace);
            return switch (cloudNumber) {
                case "II" -> 2;
                case "III" -> 3;
                case "IV" -> 4;
                case "V" -> 5;
                default -> 1;
            };
        }

        // Other modifiers are formatted as "(+)number(%) modifier name"
        // we don't want the + or % in the number string

        if (modifier.charAt(0) == '+') {
            startIndex = 1;
        }

        boolean isPercent = modifier.contains("%");
        if (isPercent) {
            firstSpace = firstSpace - 1;
        }

        String numberString = modifier.substring(startIndex, firstSpace);
        if (numberString.isBlank()) {
            return 1;
        }

        return getLevelType(modifierName, isPercent).apply(numberString);
    }

    /**
     * Utility method for {@link DataFixerParsers#parseLevel(String, String) parseLevel}
     * Does not account for Clouds or the differences between int and % modifiers with the same name
     *      as that is handled in {@link DataFixerParsers#parseLevel(String, String) parseLevel} already.
     *
     * @return Returns the parseMethod for the number type associated with the modifierName
     */
    private static Function<String, ? extends Number> getLevelType(String modifierName, boolean isPercent) {
        return switch (modifierName) {
            case "Mana" -> isPercent ? Float::parseFloat : Integer::parseInt;
            case "Armor", "Durability", "Chaining Attack", "Size", "Hammer Size" -> Integer::parseInt;
            case "Attack Damage", "Attack Speed", "Reach", "Attack Range" -> Double::parseDouble;
            default -> modifierName.contains("level to") ? Integer::parseInt : Float::parseFloat;
        };
    }
}
