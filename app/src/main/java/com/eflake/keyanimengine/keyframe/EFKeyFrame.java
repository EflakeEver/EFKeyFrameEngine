package com.eflake.keyanimengine.keyframe;


public class EFKeyFrame implements IEFKeyFrame {
    public long time;//关键帧时间点
    public String value;//关键帧数值
    public EFKeyFrame lastKeyFrame;//上一个关键帧

    public EFKeyFrame(long time, String value) {
        this.time = time;
        this.value = value;
    }

    @Override
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setLastKeyFrame(EFKeyFrame lastKeyFrame) {
        this.lastKeyFrame = lastKeyFrame;
    }

}
