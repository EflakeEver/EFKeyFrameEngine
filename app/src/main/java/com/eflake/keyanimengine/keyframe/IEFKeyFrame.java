package com.eflake.keyanimengine.keyframe;


public interface IEFKeyFrame {
    void setTime(long time);

    void setValue(String value);

    void setLastKeyFrame(EFKeyFrame lastKeyFrame);
}
