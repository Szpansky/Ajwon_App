package com.apps.szpansky.ajwon_app.tools;


public final class SimpleFunctions {

    public static String setZeroBeforeString(String value) {
        if (value.length() == 1)
            return "0" + value;
        else return value;
    }

    public static String fillItemNumberWithZeros(String value) {
        while (value.length() < 5) {
            value = "0" + value;
        }
        return value;
    }
}
