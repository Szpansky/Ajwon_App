package com.apps.szpansky.ajwon_app.tools;


public final class SimpleFunctions {

    public static String fillWithZeros(String value, int length) {
        while (value.length() < length) {
            value = "0" + value;
        }
        return value;
    }

}
