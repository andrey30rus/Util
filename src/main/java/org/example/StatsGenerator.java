package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Map;

public class StatsGenerator {
    public static void getStats(Map<String, ArrayList<String>> dataTypesMapForStats, boolean fullStats, boolean shortStats) {
        if (fullStats) {
            generateFullStatistic(dataTypesMapForStats);
        } else if (shortStats) {
            generateShortStatistic(dataTypesMapForStats);
        }
    }

    private static void generateShortStatistic(Map<String, ArrayList<String>> dataTypesMapForStats) {
        for (Map.Entry<String, ArrayList<String>> entry : dataTypesMapForStats.entrySet()) {
            System.out.println(entry.getKey() + " count: " + entry.getValue().size());
        }
    }

    private static void generateFullStatistic(Map<String, ArrayList<String>> dataTypesMapForStats) {
        generateShortStatistic(dataTypesMapForStats);
        for (Map.Entry<String, ArrayList<String>> entry : dataTypesMapForStats.entrySet()) {
            if (entry.getKey().equals("strings")) {
                int maxLength = findMaxLengthString(entry.getValue());
                int minLength = findMinLengthString(entry.getValue());
                System.out.println("-------" + entry.getKey() + " full stat-----------");
                System.out.println("Longest String length: " + maxLength);
                System.out.println("Shortest String length: " + minLength);
            } else {
                System.out.println("-------" + entry.getKey() + " full stat-----------");
                var max = findMaxValue(entry.getValue());
                System.out.println(entry.getKey() + " max value: " + max);
                var min = findMinValue(entry.getValue());
                System.out.println(entry.getKey() + " min value: " + min);
                var sum = findSumValue(entry.getValue());
                System.out.println(entry.getKey() + " sum value: " + sum);
                var avarage = findAverageValue(entry.getValue());
                System.out.println(entry.getKey() + " avarage value: " + avarage);
            }
        }
    }

    private static Object findSumValue(ArrayList<String> numbersList) {
        if (checkListForNullOrEmpty(numbersList)) return null;
        BigDecimal sum = BigDecimal.ZERO;
        for (String number : numbersList) {
            sum = sum.add(new BigDecimal(number));
        }
        return sum;
    }

    private static Object findAverageValue(ArrayList<String> numbersList) {
        if (checkListForNullOrEmpty(numbersList)) return null;

        BigDecimal sum = BigDecimal.ZERO;
        for (String number : numbersList) {
            sum = sum.add(new BigDecimal(number));
        }
        BigDecimal count = new BigDecimal(numbersList.size());
        return sum.divide(count, MathContext.DECIMAL128);
    }

    private static boolean checkListForNullOrEmpty(ArrayList<String> numbersList) {
        if (numbersList == null || numbersList.isEmpty()) {
            System.out.println("List is null or empty");
            return true;
        }
        return false;
    }

    private static Object findMinValue(ArrayList<String> numbersList) {
        if (checkListForNullOrEmpty(numbersList)) return null;
        BigDecimal minValue = new BigDecimal(numbersList.get(0));
        for (String number : numbersList) {
            BigDecimal currentValue;
            try {
                currentValue = new BigDecimal(new BigInteger(number));
            } catch (NumberFormatException e) {
                try {
                    currentValue = new BigDecimal(number);
                } catch (NumberFormatException e2) {
                    continue;
                }
                if (minValue == null || currentValue.compareTo(minValue) < 0) {
                    minValue = currentValue;
                }
            }
        }
        return minValue;
    }

    private static Object findMaxValue(ArrayList<String> numbersList) {
        BigDecimal maxValue = null;
        for (String number : numbersList) {
            BigDecimal currentValue;
            try {
                currentValue = new BigDecimal(new BigInteger(number));
            } catch (NumberFormatException e) {
                try {
                    currentValue = new BigDecimal(number);
                } catch (NumberFormatException e2) {
                    continue;
                }
            }
            if (maxValue == null || currentValue.compareTo(maxValue) > 0) {
                maxValue = currentValue;
            }
        }
        return maxValue;
    }

    private static int findMinLengthString(ArrayList<String> value) {
        String shortestWord = value.get(0);
        for (String word : value) {
            if (word.length() < shortestWord.length()) {
                shortestWord = word;
            }
        }
        return shortestWord.length();
    }

    private static int findMaxLengthString(ArrayList<String> value) {
        String longestWord = value.get(0);
        for (String word : value) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        return longestWord.length();
    }
}
