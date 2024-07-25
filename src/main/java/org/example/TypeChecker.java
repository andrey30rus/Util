package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TypeChecker {

    public String checkDataType(String value) {
        if (value == null) {
            return null;
        } else if (isInteger(value)) {
            return "integers";
        } else if (isFloat(value)) {
            return "ﬂoats";
        } else return "strings";
    }

    private boolean isInteger(String value) {
        try {
            BigInteger val = new BigInteger(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String value) {
        try {
            BigDecimal val = new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}