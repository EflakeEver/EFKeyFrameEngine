package com.eflake.keyanimengine.keyframe;


public interface IEFElement {
    void setName(String name);

    String getName();

    void addPositionKeyFrame(EFPosKeyFrame positionKeyFrame);

    void addRotationKeyFrame(EFRotationKeyFrame rotationKeyFrame);

    void addAlphaKeyFrame(EFAlphaKeyFrame alphaKeyFrame);

    void addScaleKeyFrame(EFScaleKeyFrame scaleKeyFrame);

    void addPathKeyFrame(EFPathKeyFrame pathKeyFrame);

    void updateAnim(long allAnimFrameIndex);

    void setAnimListener(IEFAnimListener listener);

    EFAnim getAnim();

    void setAnim(EFAnim anim);
}