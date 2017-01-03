package com.eflake.keyanimengine.keyframe;


public interface IEFAnimManager {
    void addAnim(String key, EFAnim anim);

    void removeAnimByKey(String key);

    void removeAllAnim();

    void startAnimByKey(String key);

}
