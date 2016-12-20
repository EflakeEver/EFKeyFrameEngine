package com.eflake.keyanimengine.keyframe;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.eflake.keyanimengine.evaluator.EFBezier2Evaluator;
import com.eflake.keyanimengine.evaluator.EFBezierKeepEvaluator;
import com.eflake.keyanimengine.evaluator.EFKeepEvaluator;
import com.eflake.keyanimengine.evaluator.EFLinearEvaluator;
import com.eflake.keyanimengine.sprite.EFSprite;

import java.util.ArrayList;
import java.util.List;


public class EFElement extends EFSprite implements IEFElement {
    public static final int TYPE_DEFAULT = -1;
    public static final int TYPE_POS = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_ALPHA = 2;
    public static final int TYPE_SCALE = 3;
    public static final int TYPE_ROTATION = 4;

    public String mName;//元素名称
    public List<EFPosKeyFrame> positionKeyFrameList = new ArrayList<>();//位置关键帧集合
    public List<EFKeyFrame> rotationKeyFrameList = new ArrayList<>();//旋转关键帧集合
    public List<EFKeyFrame> alphaKeyFrameList = new ArrayList<>();//透明度关键帧集合
    public List<EFKeyFrame> scaleKeyFrameList = new ArrayList<>();//缩放关键帧集合
    public List<EFPathKeyFrame> pathKeyFrameList = new ArrayList<>();//路径关键帧集合
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
        defaultPosKeyFrame = new EFKeyFrame(0, "0,0");
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
    public void addPositionKeyFrame(EFPosKeyFrame positionKeyFrame) {
        //TODO 插入关键帧时机,应当先将上一帧的数值给赋上
        int posKeyFrameSize = positionKeyFrameList.size();
        //根据当前Size不同,插入的关键帧对应设置也需要分场景
        if (posKeyFrameSize == 0) {
            //添加的是第一个关键帧
            positionKeyFrame.setLastKeyFrame(defaultPosKeyFrame);
            //第一个关键帧区域内,属性以第一个关键帧属性为准
            //TODO new 对象
            positionKeyFrame.setEvaluator(new EFKeepEvaluator());
        } else {
            positionKeyFrame.setLastKeyFrame(positionKeyFrameList.get(posKeyFrameSize - 1));
            //默认设置线性差值器,后续考虑扩展
            //TODO new 对象
            positionKeyFrame.setEvaluator(new EFLinearEvaluator());
        }
        //TODO new 对象
        positionKeyFrame.setInterpolator(new LinearInterpolator());
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
    public void addPathKeyFrame(EFPathKeyFrame pathKeyFrame) {
        int pathKeyFrameSize = pathKeyFrameList.size();
        if (pathKeyFrameSize == 0) {
            pathKeyFrame.setLastKeyFrame(defaultPosKeyFrame);
            pathKeyFrame.setEvaluator(new EFBezierKeepEvaluator());
        } else {
            pathKeyFrame.setLastKeyFrame(pathKeyFrameList.get(pathKeyFrameSize - 1));
            pathKeyFrame.setEvaluator(new EFBezier2Evaluator());
        }
        pathKeyFrame.setInterpolator(new LinearInterpolator());
        pathKeyFrameList.add(pathKeyFrame);
    }

    @Override
    public void updateAnim(long animFrameIndex) {
        //TODO 此处根据动画当前执行的帧数,计算并设置对应属性值
        Log.e("eflake", "updateAnim ,index = " + animFrameIndex);
        // 处理Position关键帧
        if (positionKeyFrameList.size() > 0) {
//            int posFrameAreaIndex = judgePosFrameArea(animFrameIndex);
            int posFrameAreaIndex = judgeFrameArea(TYPE_POS, animFrameIndex);
            setCurrentFramePosValue(TYPE_POS, posFrameAreaIndex, animFrameIndex);
        }

        // 处理Path关键帧
        if (pathKeyFrameList.size() > 0) {
            int pathFrameAreaIndex = judgeFrameArea(TYPE_PATH, animFrameIndex);
            setCurrentFramePosValue(TYPE_PATH, pathFrameAreaIndex, animFrameIndex);
        }

        //TODO 处理Rotation关键帧
        //TODO 处理Scale关键帧
        //TODO 处理Alpha关键帧

        //转换相对坐标为绝对坐标
        convertRelativeToRealPos();
        //转换成对应ViewPort坐标
        convertViewPortPos();
        //设置应用的变换矩阵
        applyMatrix();
    }

    private void applyMatrix() {
        mMatrix = getMatrix();
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
    }

    private int judgeFrameArea(int type, long currentAnimFrameIndex) {
        int result = 0;
        int keyFrameSize = 0;
        List list = null;
        switch (type) {
            case TYPE_POS:
                list = positionKeyFrameList;
                break;
            case TYPE_PATH:
                list = pathKeyFrameList;
                break;
            case TYPE_ALPHA:
                list = alphaKeyFrameList;
                break;
            case TYPE_SCALE:
                list = scaleKeyFrameList;
                break;
            case TYPE_ROTATION:
                list = rotationKeyFrameList;
                break;
        }
        if (list != null) {
            keyFrameSize = list.size();
        }

        if (keyFrameSize == 0) {
            //没有关键帧
            result = 0;
        } else {
            //大于一个的情况
            for (int i = 0; i < keyFrameSize; i++) {
                EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(i);
                if (currentAnimFrameIndex <= targetKeyFrame.time) {
                    result = i;
                    break;
                } else {
                    result = keyFrameSize;
                }
            }
        }
        return result;
    }

    private void setCurrentFramePosValue(int type, int keyFrameAreaIndex, long animFrameIndex) {
        int keyFrameSize = 0;
        List list = null;
        switch (type) {
            case TYPE_POS:
                list = positionKeyFrameList;
                break;
            case TYPE_PATH:
                list = pathKeyFrameList;
                break;
            case TYPE_ALPHA:
                list = alphaKeyFrameList;
                break;
            case TYPE_SCALE:
                list = scaleKeyFrameList;
                break;
            case TYPE_ROTATION:
                list = rotationKeyFrameList;
                break;
        }
        if (list != null) {
            keyFrameSize = list.size();
        }
        if (keyFrameSize != 0) {
            //有关键帧，计算
            if (keyFrameAreaIndex < keyFrameSize) {
                if (type == TYPE_POS || type == TYPE_PATH) {
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameAreaIndex);
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
                    //获取差值器
                    if (type == TYPE_POS) {
                        Interpolator interpolator = ((EFPosKeyFrame) targetKeyFrame).interpolator;
                        //计算KeyFrame差值器input,即当前区间的动画执行时间进度百分比
                        float keyframeTimeFraction = (animFrameIndex - lastPosKeyFrameTime) / (float) keyframeDuration;
                        //计算KeyFrame差值器，计算返回后的结果进度百分比
                        float realTimeFraction = interpolator.getInterpolation(keyframeTimeFraction);
                        //计算设置结果
                        float realValueX = ((EFPosKeyFrame) targetKeyFrame).evaluator.evaluate(realTimeFraction, lastValuePosX, currentValuePosX);
                        float realValueY = ((EFPosKeyFrame) targetKeyFrame).evaluator.evaluate(realTimeFraction, lastValuePosY, currentValuePosY);
                        setCenterPosX(realValueX);
                        setCenterPosY(realValueY);
                    }
                    if (type == TYPE_PATH) {
                        String controlValue = ((EFPathKeyFrame) targetKeyFrame).control;
                        String[] controlValueArray = controlValue.split(",");
                        float controlValuePosX = Float.valueOf(controlValueArray[0]);
                        float controlValuePosY = Float.valueOf(controlValueArray[1]);

                        Interpolator interpolator = ((EFPathKeyFrame) targetKeyFrame).interpolator;
                        //计算KeyFrame差值器input,即当前区间的动画执行时间进度百分比
                        float keyframeTimeFraction = (animFrameIndex - lastPosKeyFrameTime) / (float) keyframeDuration;
                        //计算KeyFrame差值器，计算返回后的结果进度百分比
                        float realTimeFraction = interpolator.getInterpolation(keyframeTimeFraction);
                        //计算设置结果
                        float realValueX = ((EFPathKeyFrame) targetKeyFrame).evaluator.evaluate(realTimeFraction, lastValuePosX, controlValuePosX, currentValuePosX);
                        float realValueY = ((EFPathKeyFrame) targetKeyFrame).evaluator.evaluate(realTimeFraction, lastValuePosY, controlValuePosY, currentValuePosY);
                        setCenterPosX(realValueX);
                        setCenterPosY(realValueY);
                    }

                }
            } else {
                if (type == TYPE_POS || type == TYPE_PATH) {
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameSize - 1);
                    //当前KeyFrame点坐标
                    String currentValue = targetKeyFrame.value;
                    String[] currentValueArray = currentValue.split(",");
                    float currentValuePosX = Float.valueOf(currentValueArray[0]);
                    float currentValuePosY = Float.valueOf(currentValueArray[1]);
                    setCenterPosX(currentValuePosX);
                    setCenterPosY(currentValuePosY);
                }
            }
        } else {
            //没有关键帧，不计算
        }
    }

    private void convertViewPortPos() {
        if (getAnim() != null) {
            setCenterPosX(getAnim().getViewPortPosX(mCenterPosX));
            setCenterPosY(getAnim().getViewPortPosY(mCenterPosY));
        }
    }

    public void draw(Canvas canvas, Paint defaultPaint) {
        defaultPaint.setAlpha(mFakeAlpha);
        canvas.drawBitmap(mBitmap, mMatrix, defaultPaint);
    }


}
