package com.eflake.keyanimengine.keyframe;


public interface IEFElement {
    void setName(String name);

    String getName();

    void addPositionKeyFrame(EFKeyFrame positionKeyFrame);

    void addRotationKeyFrame(EFKeyFrame rotationKeyFrame);

    void addAlphaKeyFrame(EFKeyFrame alphaKeyFrame);

    void addScaleKeyFrame(EFKeyFrame scaleKeyFrame);

    void addPathKeyFrame(EFKeyFrame pathKeyFrame);

//    void setAnimListener(AnimListener listener);

    void updateAnim(long frameIndex);


}
