package com.eflake.keyanimengine.utils;


public class PropertyConvertUtils {

    public static float convertAlpha(float originAlpha) {
        return originAlpha * 255 / 100;
    }

    public static float convertScale(float realValue) {
        return realValue / 100.0f;
    }
}
