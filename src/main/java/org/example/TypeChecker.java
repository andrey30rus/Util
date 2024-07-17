package org.example;

public class TypeChecker {
    String dataString;

    public TypeChecker(String dataString) {
        this.dataString = dataString;
    }

    public boolean isInteger(String dataString) {
        try {
            Integer.parseInt(dataString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Double isDouble(String dataString) {
        try {
            return Double.parseDouble(dataString);
        } catch (Exception e) {
        }
        return null;
    }
}