package com.eflake.keyanimengine.interpolator;

import android.view.animation.Interpolator;


public class EFKeepInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return 1.0f;
    }
}
