package net.joseph.vaultfilters;

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
}
