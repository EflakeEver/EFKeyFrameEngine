package com.eflake.keyanimengine.keyframe;


public interface IEFElement {
    void setName(String name);

    String getName();

    void addPositionKeyFrame(EFPosKeyFrame positionKeyFrame);

    void addRotationKeyFrame(EFKeyFrame rotationKeyFrame);

    void addAlphaKeyFrame(EFKeyFrame alphaKeyFrame);

    void addScaleKeyFrame(EFKeyFrame scaleKeyFrame);

    void addPathKeyFrame(EFPathKeyFrame pathKeyFrame);

    void updateAnim(long allAnimFrameIndex);

    void setAnimListener(IEFAnimListener listener);

    EFAnim getAnim();

    void setAnim(EFAnim anim);
}