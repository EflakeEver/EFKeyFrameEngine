package com.eflake.keyanimengine.keyframe;


public interface IEFAnimManager {
    boolean addAnim(String key, EFAnim anim);

    boolean removeAnimByKey(String key);

    void removeAllAnim();

    void startAnimByKey(String key);

}
