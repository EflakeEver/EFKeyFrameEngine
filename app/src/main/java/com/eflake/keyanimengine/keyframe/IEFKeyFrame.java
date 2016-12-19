package com.eflake.keyanimengine.keyframe;


import android.view.animation.Interpolator;

public interface IEFKeyFrame {
    void setIndex(int index);

    void setTime(long time);

    void setValue(String value);

    void setLastKeyFrame(EFKeyFrame lastKeyFrame);

    void setInterpolator(Interpolator interpolator);
}
