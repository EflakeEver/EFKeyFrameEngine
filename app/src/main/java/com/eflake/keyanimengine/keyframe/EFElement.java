package com.eflake.keyanimengine.keyframe;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.eflake.keyanimengine.interpolator.KeepInterpolator;
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
    private float mLastPositionX;
    private float mLastPositionY;
    public IEFAnimListener mIEFAnimListener;
    public EFKeyFrame defaultPosKeyFrame;
    public EFAnim mAnim;
    private boolean mHasDraw = false;
    private int mFakeRotation = 35;
    private boolean mIncrease = true;
    private float mFakeScale;
    private int mFakeAlpha = 100;

    public EFElement(Context context, String path, int startPosX, int startPosY) {
        super(context, path, startPosX, startPosY);
        initDefaultKeyFrame();
    }

    public EFElement(Context context, String path, int startPosX, int startPosY, float width, int height) {
        super(context, path, startPosX, startPosY, width, height);
        initDefaultKeyFrame();
    }

    public EFElement(Context context, String path, int anchorPosX, int anchorPosY, int anchorPointType) {
        super(context, path, anchorPosX, anchorPosY, anchorPointType);
        initDefaultKeyFrame();
    }


    public EFElement(Context context, int resId, float startPosX, float startPosY) {
        super(context, resId, startPosX, startPosY);
        initDefaultKeyFrame();
    }

    public EFElement(Context context, int resId, float anchorPosX, float anchorPosY, int anchorPointType) {
        super(context, resId, anchorPosX, anchorPosY, anchorPointType);
        initDefaultKeyFrame();
    }

    public EFElement(Context context, int resId, float startPosX, float startPosY, float width, float height) {
        super(context, resId, startPosX, startPosY, width, height);
        initDefaultKeyFrame();
    }

    private void initDefaultKeyFrame() {
        //此处的defaultKeyFrame并不放入List,仅作为初始赋值用
        defaultPosKeyFrame = new EFKeyFrame(0, 0, "0,0");
    }


    @Override
    public void setAnimListener(IEFAnimListener listener) {
        mIEFAnimListener = listener;
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
    public EFAnim getAnim() {
        return mAnim;
    }

    @Override
    public void setAnim(EFAnim anim) {
        mAnim = anim;
    }

    @Override
    public void addPositionKeyFrame(EFKeyFrame positionKeyFrame) {
        //TODO 插入关键帧时机,应当先将上一帧的数值给赋上
        int posKeyFrameSize = positionKeyFrameList.size();
        //根据当前Size不同,插入的关键帧对应设置也需要分场景
        if (posKeyFrameSize == 0) {
            //添加的是第一个关键帧
            positionKeyFrame.setLastKeyFrame(defaultPosKeyFrame);
            //第一个关键帧区域内,属性以第一个关键帧属性为准
            positionKeyFrame.setInterpolator(new KeepInterpolator());
        } else {
            positionKeyFrame.setLastKeyFrame(positionKeyFrameList.get(posKeyFrameSize - 1));
            //默认设置线性差值器,后续考虑扩展
            positionKeyFrame.setInterpolator(new LinearInterpolator());
        }
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
    public void updateAnim(long animFrameIndex) {
        //TODO 此处根据动画当前执行的帧数,计算并设置对应属性值
        Log.e("eflake", "updateAnim ,index = " + animFrameIndex);
        // 处理Position关键帧
        int posFrameAreaIndex = judgePosFrameArea(animFrameIndex);
        setCurrentFramePosValue(posFrameAreaIndex, animFrameIndex);

        //TODO 处理Rotation关键帧
        //TODO 处理Scale关键帧
        //TODO 处理Alpha关键帧
        //TODO 处理Path关键帧

        //转换相对坐标
        convertRelativeToRealPos();
        //转换成对应ViewPort坐标
        convertViewPortPos();
    }

    private int judgePosFrameArea(long currentAnimFrameIndex) {
        int result = 0;
        int posKeyFrameSize = positionKeyFrameList.size();
        if (posKeyFrameSize == 0) {
            //TODO 没有关键帧的情况
            result = 0;
        } else {
            //大于一个的情况
            for (int i = 0; i < posKeyFrameSize; i++) {
                EFKeyFrame targetKeyFrame = positionKeyFrameList.get(i);
                if (currentAnimFrameIndex <= targetKeyFrame.time) {
                    result = i;
                    break;
                } else {
                    result = posKeyFrameSize;
                }
            }
        }
        return result;
    }

    private void setCurrentFramePosValue(int posFrameAreaIndex, long animFrameIndex) {
        int posKeyFrameSize = positionKeyFrameList.size();
        if (posKeyFrameSize != 0) {
            if (posFrameAreaIndex < posKeyFrameSize) {
                EFKeyFrame targetKeyFrame = positionKeyFrameList.get(posFrameAreaIndex);
                //上一个KeyFrame坐标
                String lastValue = targetKeyFrame.lastKeyFrame.value;
                String[] lastValueArray = lastValue.split(",");
                float lastValuePosX = Float.valueOf(lastValueArray[0]);
                float lastValuePosY = Float.valueOf(lastValueArray[1]);
                //当前KeyFrame点坐标
                String currentValue = targetKeyFrame.value;
                String[] currentValueArray = currentValue.split(",");
                float currentValuePosX = Float.valueOf(currentValueArray[0]);
                float currentValuePosY = Float.valueOf(currentValueArray[1]);
                //两个关键帧的时间差
                long lastPosKeyFrameTime = targetKeyFrame.lastKeyFrame.time;
                long currentPosKeyFrameTime = targetKeyFrame.time;
                long keyframeDuration = currentPosKeyFrameTime - lastPosKeyFrameTime;
                //两个关键帧距离差
                float keyframeDistanceX = currentValuePosX - lastValuePosX;
                float keyframeDistanceY = currentValuePosY - lastValuePosY;
                //获取差值器
                Interpolator interpolator = targetKeyFrame.interpolator;
                //计算KeyFrame差值器当前执行时间进度百分比
                float keyframeTimeFraction = (animFrameIndex - lastPosKeyFrameTime) / (float) keyframeDuration;
                //计算KeyFrame差值器计算返回后的结果进度百分比
                float realValueFraction = interpolator.getInterpolation(keyframeTimeFraction);
                //计算设置结果
                float realValueX = lastValuePosX + keyframeDistanceX * realValueFraction;
                float realValueY = lastValuePosY + keyframeDistanceY * realValueFraction;
                setCenterPosX(realValueX);
                setCenterPosY(realValueY);
            } else {
                EFKeyFrame targetKeyFrame = positionKeyFrameList.get(posKeyFrameSize - 1);
                //当前KeyFrame点坐标
                String currentValue = targetKeyFrame.value;
                String[] currentValueArray = currentValue.split(",");
                float currentValuePosX = Float.valueOf(currentValueArray[0]);
                float currentValuePosY = Float.valueOf(currentValueArray[1]);
                setCenterPosX(currentValuePosX);
                setCenterPosY(currentValuePosY);
            }
        }
    }

    private void convertViewPortPos() {
        if (getAnim() != null) {
            mCenterPosX = getAnim().getViewPortPosX(mCenterPosX);
            mCenterPosY = getAnim().getViewPortPosY(mCenterPosY);
        }
    }

    public void draw(Canvas canvas, Paint defaultPaint) {
//        canvas.drawBitmap(mBitmap, mStartPosX, mStartPosY, defaultPaint);
//        canvas.save();
//        canvas.scale(0.7f,0.7f);
//        canvas.drawBitmap(mBitmap, mStartPosX*1.3f+800, mStartPosY*1.3f, defaultPaint);
//        canvas.restore();

        mMatrix = getMatrix();
        canvas.save();
        mMatrix.setTranslate(mStartPosX, mStartPosY);
        if (mIncrease) {
            mFakeScale += 0.05f;
            mFakeRotation += 4;
            mFakeAlpha += 3;
        } else {
            mFakeScale -= 0.05f;
            mFakeRotation -= 4;
            mFakeAlpha -= 3;
        }
        if (mFakeScale >= 2.0f) {
            mIncrease = false;
        }
        if (mFakeScale <= 0.5) {
            mIncrease = true;
        }
        mMatrix.preScale(mFakeScale, mFakeScale, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        mMatrix.preRotate(mFakeRotation, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        defaultPaint.setAlpha(mFakeAlpha);
        canvas.drawBitmap(mBitmap, mMatrix, defaultPaint);
        canvas.restore();

//        mMatrix.preRotate(1.0f,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//        mMatrix.preScale(0.5f,0.5f,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//        canvas.save();
//        canvas.rotate(45);
//        mMatrix.postTranslate(1.0f,1.0f);
//        mMatrix.preTranslate(1.0f,1.0f);
//        mMatrix.postRotate(500,500.0f,500.0f);
    }


}
