package com.eflake.keyanimengine.utils;


import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenDimenUtils {


    private static int mScreenWidthPixels;
    private static int mScreenHeightPixels;

    public static void init(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidthPixels = dm.widthPixels;
        mScreenHeightPixels = dm.heightPixels;
    }

    public static float getDeviceWidth() {
        return mScreenWidthPixels;
    }

    public static float getDeviceHeight() {
        return mScreenHeightPixels;
    }
}
