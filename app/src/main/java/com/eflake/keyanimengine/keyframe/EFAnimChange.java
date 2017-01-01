package com.eflake.keyanimengine.keyframe;


public class EFAnimChange {
    public static final int TYPE_ADD = 1;
    public static final int TYPE_REMOVE = 2;
    public EFAnim mAnim;
    public String key;
    public int type;

    public EFAnim getAnim() {
        return mAnim;
    }

    public void setAnim(EFAnim anim) {
        mAnim = anim;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
