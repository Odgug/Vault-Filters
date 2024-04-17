package net.joseph.vaultfilters;

import java.util.function.Function;

public class DataFixerParsers {
    public static String getModifierName(String modifier) {
        int firstSpace = modifier.indexOf(' ');
        if (modifier.contains("Cloud")) {
            int lastSpace = modifier.lastIndexOf(' ');
            if (firstSpace == lastSpace) {
                return modifier.substring(1);
            }
            return modifier.substring(1, firstSpace) + modifier.substring(lastSpace);
        }

        String name = modifier.substring(firstSpace + 1);
        return name.startsWith("to ") ? name.substring(3) : name;
    }

    public static Number parseLevel(String modifierName, String modifier) {
        int firstSpace = modifier.indexOf(' ');
        int startIndex = 0;

        // Clouds are a special case
        if (modifier.contains("Cloud")) {
            // The first tier of a cloud is formatted as "Fear Cloud" no I
            int lastSpace = modifier.lastIndexOf(' ');
            if (firstSpace == lastSpace) {
                return 1;
            }

            // Cloud Modifiers 2+ are formatted as "Fear II Cloud", to get II, we need the first and last space
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

        // Some Modifier types have special cases for the number itself (e.g. as Attack Speed)
        Number level = getLevelType(modifierName, isPercent).apply(numberString);
        if (modifierName.equals("Attack Speed")) {
            return (double) level - 4;
        } else {
            return level;
        }
    }

    /**
     * Utility method for {@link DataFixerParsers#parseLevel(String, String) parseLevel}
     * Does not account for Clouds as that is handled in {@link DataFixerParsers#parseLevel(String, String) parseLevel} already.
     *
     * @return Returns the parseMethod for the number type associated with the modifierName
     */
    private static Function<String, ? extends Number> getLevelType(String modifierName, boolean isPercent) {
        return switch (modifierName) {
            case "Mana"
                    -> isPercent ? DataFixerParsers::parseFloatPercent : Integer::parseInt;
            case "Armor", "Durability", "Chaining Attack", "Size", "Hammer Size"
                    -> isPercent ? DataFixerParsers::parseIntPercent : Integer::parseInt;
            case "Attack Damage", "Reach", "Attack Range", "Attack Speed"
                    -> isPercent ? DataFixerParsers::parseDoublePercent : Double::parseDouble;
            default -> {
                if (modifierName.contains("level of")) {
                    yield Integer::parseInt;
                }
                yield  isPercent ? DataFixerParsers::parseFloatPercent : Float::parseFloat;
            }
        };
    }

    private static Integer parseIntPercent(String string) {
        return Integer.parseInt(string) / 100;
    }

    private static Double parseDoublePercent(String string) {
        return Double.parseDouble(string) / 100;
    }

    private static Float parseFloatPercent(String string) {
        return Float.parseFloat(string) / 100;
    }
}
