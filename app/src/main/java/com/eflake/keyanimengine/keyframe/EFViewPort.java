package com.eflake.keyanimengine.keyframe;


import com.eflake.keyanimengine.utils.ScreenDimenUtils;

public class EFViewPort {
    public float widthPixel;
    public float heightPixel;

    public EFViewPort(float width, float height) {
        widthPixel = width;
        heightPixel = height;
    }

    public boolean isSame() {
        if (ScreenDimenUtils.getDeviceWidth() == widthPixel && ScreenDimenUtils.getDeviceHeight() == heightPixel) {
            return true;
        } else {
            return false;
        }
    }

    public float getWidthFactor() {
        return ScreenDimenUtils.getDeviceWidth() / widthPixel;
    }

    public float getHeightFactor() {
        return ScreenDimenUtils.getDeviceHeight() / heightPixel;
    }
}
