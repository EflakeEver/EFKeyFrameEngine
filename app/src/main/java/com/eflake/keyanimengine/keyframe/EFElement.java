package com.eflake.keyanimengine.keyframe;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.eflake.keyanimengine.sprite.EFSprite;

import java.util.ArrayList;
import java.util.List;

public class EFElement extends EFSprite implements IEFElement {
    public String mName;//元素名称
    public List<EFKeyFrame> positionKeyFrameList = new ArrayList<>();//位置关键帧集合
    public List<EFKeyFrame> rotationKeyFrameList = new ArrayList<>();//旋转关键帧集合
    public List<EFKeyFrame> alphaKeyFrameList = new ArrayList<>();//透明度关键帧集合
    public List<EFKeyFrame> scaleKeyFrameList = new ArrayList<>();//缩放关键帧集合
    public List<EFKeyFrame> pathKeyFrameList = new ArrayList<>();//路径关键帧集合
    private int mLastPositionX;
    private int mLastPositionY;

    public EFElement(Context context, String path, int startPosX, int startPosY) {
        super(context, path, startPosX, startPosY);
    }

    public EFElement(Context context, String path, int anchorPosX, int anchorPosY, int anchorPointType) {
        super(context, path, anchorPosX, anchorPosY, anchorPointType);
    }

    public EFElement(Context context, int resId, int startPosX, int startPosY) {
        super(context, resId, startPosX, startPosY);
    }

    public EFElement(Context context, int resId, int anchorPosX, int anchorPosY, int anchorPointType) {
        super(context, resId, anchorPosX, anchorPosY, anchorPointType);
    }

    @Override
    public void setName(String name) {
        mName = name;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void addPositionKeyFrame(EFKeyFrame positionKeyFrame) {
        positionKeyFrameList.add(positionKeyFrame);
    }

    @Override
    public void addRotationKeyFrame(EFKeyFrame rotationKeyFrame) {
        rotationKeyFrameList.add(rotationKeyFrame);
    }

    @Override
    public void addAlphaKeyFrame(EFKeyFrame alphaKeyFrame) {
        alphaKeyFrameList.add(alphaKeyFrame);
    }

    @Override
    public void addScaleKeyFrame(EFKeyFrame scaleKeyFrame) {
        scaleKeyFrameList.add(scaleKeyFrame);
    }

    @Override
    public void addPathKeyFrame(EFKeyFrame pathKeyFrame) {
        pathKeyFrameList.add(pathKeyFrame);
    }

    @Override
    public void updateAnim(long frameIndex) {
        //TODO 此处根据动画当前执行的帧数,计算并设置对应属性值
        Log.e("eflake","updateAnim ,index = "+frameIndex);
    }


    @Override
    public void draw(Canvas canvas, Paint defaultPaint) {
//        int dx = mCenterPosX - mLastPositionX;
//        int dy = mCenterPosY - mLastPositionY;
//
//        mMatrix.setRotate(mRotation);
//        mMatrix.preScale(mScale, mScale);
//        mMatrix.preTranslate(dx, dy);
//        defaultPaint.setAlpha(mAlpha);
//        canvas.drawBitmap(mBitmap, mMatrix, defaultPaint);
//        mLastPositionX = mCenterPosX;
//        mLastPositionY = mCenterPosY;
    }
}
