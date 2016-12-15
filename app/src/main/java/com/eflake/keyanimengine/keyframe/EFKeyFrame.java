package com.eflake.keyanimengine.keyframe;


public class EFKeyFrame implements IEFKeyFrame {
    public int index;//当前关键帧,在对应Element的对应属性List中的位置
    public long time;//关键帧时间点
    public String value;//关键帧数值

    public EFKeyFrame(int index, long time, String value) {
        this.index = index;
        this.time = time;
        this.value = value;
    }

    @Override
    public void setIndex(int index) {

    }

    @Override
    public void setTime(long time) {

    }

    @Override
    public void setValue(String value) {

    }
}
