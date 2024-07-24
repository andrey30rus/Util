package org.example;

public class TypeChecker {

    public String checkDataType(String value) {
        if (value == null) {
            return null;
        } else if (isInteger(value) || isLong(value)) {
            return "integers";
        } else if (isFloat(value)) {
            return "ﬂoats";
        } else return "strings";
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}