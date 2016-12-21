package com.eflake.keyanimengine.keyframe;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.eflake.keyanimengine.evaluator.EFBezier2Evaluator;
import com.eflake.keyanimengine.evaluator.EFBezierKeepEvaluator;
import com.eflake.keyanimengine.evaluator.EFKeepEvaluator;
import com.eflake.keyanimengine.evaluator.EFLinearEvaluator;
import com.eflake.keyanimengine.sprite.EFSprite;
import com.eflake.keyanimengine.utils.AlphaUtils;

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
    public List<EFRotationKeyFrame> rotationKeyFrameList = new ArrayList<>();//旋转关键帧集合
    public List<EFAlphaKeyFrame> alphaKeyFrameList = new ArrayList<>();//透明度关键帧集合
    public List<EFScaleKeyFrame> scaleKeyFrameList = new ArrayList<>();//缩放关键帧集合
    public List<EFPathKeyFrame> pathKeyFrameList = new ArrayList<>();//路径关键帧集合
    public IEFAnimListener mIEFAnimListener;
    public EFPosKeyFrame defaultPosKeyFrame;
    public EFScaleKeyFrame defaultScaleKeyFrame;
    public EFPathKeyFrame defaultPathKeyFrame;
    public EFRotationKeyFrame defaultRotationKeyFrame;
    public EFAlphaKeyFrame defaultAlphaKeyFrame;
    public EFAnim mAnim;


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
        //此处的defaultKeyFrame并不放入各个属性List,仅作为初始赋值用
        defaultPosKeyFrame = new EFPosKeyFrame(0, "0,0");
        defaultAlphaKeyFrame = new EFAlphaKeyFrame(0, "255");
        defaultRotationKeyFrame = new EFRotationKeyFrame(0, "0");
        defaultScaleKeyFrame = new EFScaleKeyFrame(0, "1.0,1.0");
        defaultPathKeyFrame = new EFPathKeyFrame(0, "0.0,0.0", "0.0,0.0");
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
    public void addRotationKeyFrame(EFRotationKeyFrame rotationKeyFrame) {
        int rotationKeyFrameSize = rotationKeyFrameList.size();
        //根据当前Size不同,插入的关键帧对应设置也需要分场景
        if (rotationKeyFrameSize == 0) {
            //添加的是第一个关键帧
            rotationKeyFrame.setLastKeyFrame(defaultRotationKeyFrame);
            //第一个关键帧区域内,属性以第一个关键帧属性为准
            //TODO new 对象
            rotationKeyFrame.setEvaluator(new EFKeepEvaluator());
        } else {
            rotationKeyFrame.setLastKeyFrame(rotationKeyFrameList.get(rotationKeyFrameSize - 1));
            //默认设置线性差值器,后续考虑扩展
            //TODO new 对象
            rotationKeyFrame.setEvaluator(new EFLinearEvaluator());
        }
        //TODO new 对象
        rotationKeyFrame.setInterpolator(new LinearInterpolator());
        rotationKeyFrameList.add(rotationKeyFrame);
    }

    @Override
    public void addAlphaKeyFrame(EFAlphaKeyFrame alphaKeyFrame) {
        int alphaKeyFrameSize = alphaKeyFrameList.size();
        //根据当前Size不同,插入的关键帧对应设置也需要分场景
        if (alphaKeyFrameSize == 0) {
            //添加的是第一个关键帧
            alphaKeyFrame.setLastKeyFrame(defaultAlphaKeyFrame);
            //第一个关键帧区域内,属性以第一个关键帧属性为准
            //TODO new 对象
            alphaKeyFrame.setEvaluator(new EFKeepEvaluator());
        } else {
            alphaKeyFrame.setLastKeyFrame(alphaKeyFrameList.get(alphaKeyFrameSize - 1));
            //默认设置线性差值器,后续考虑扩展
            //TODO new 对象
            alphaKeyFrame.setEvaluator(new EFLinearEvaluator());
        }
        //TODO new 对象
        alphaKeyFrame.setInterpolator(new LinearInterpolator());
        alphaKeyFrameList.add(alphaKeyFrame);
    }

    @Override
    public void addScaleKeyFrame(EFScaleKeyFrame scaleKeyFrame) {
        int scaleKeyFrameSize = scaleKeyFrameList.size();
        //根据当前Size不同,插入的关键帧对应设置也需要分场景
        if (scaleKeyFrameSize == 0) {
            //添加的是第一个关键帧
            scaleKeyFrame.setLastKeyFrame(defaultScaleKeyFrame);
            //第一个关键帧区域内,属性以第一个关键帧属性为准
            //TODO new 对象
            scaleKeyFrame.setEvaluator(new EFKeepEvaluator());
        } else {
            scaleKeyFrame.setLastKeyFrame(scaleKeyFrameList.get(scaleKeyFrameSize - 1));
            //默认设置线性差值器,后续考虑扩展
            //TODO new 对象
            scaleKeyFrame.setEvaluator(new EFLinearEvaluator());
        }
        //TODO new 对象
        scaleKeyFrame.setInterpolator(new LinearInterpolator());
        scaleKeyFrameList.add(scaleKeyFrame);
    }

    @Override
    public void addPathKeyFrame(EFPathKeyFrame pathKeyFrame) {
        int pathKeyFrameSize = pathKeyFrameList.size();
        if (pathKeyFrameSize == 0) {
            pathKeyFrame.setLastKeyFrame(defaultPathKeyFrame);
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

        // 处理Position关键帧
        if (positionKeyFrameList.size() > 0) {
            int posFrameAreaIndex = judgeFrameArea(TYPE_POS, animFrameIndex);
            setCurrentKeyFrameValue(TYPE_POS, posFrameAreaIndex, animFrameIndex);
        }

        // 处理Path关键帧
        if (pathKeyFrameList.size() > 0) {
            int pathFrameAreaIndex = judgeFrameArea(TYPE_PATH, animFrameIndex);
            setCurrentKeyFrameValue(TYPE_PATH, pathFrameAreaIndex, animFrameIndex);
        }

        // 处理Rotation关键帧
        if (rotationKeyFrameList.size() > 0) {
            int rotationFrameAreaIndex = judgeFrameArea(TYPE_ROTATION, animFrameIndex);
            setCurrentKeyFrameValue(TYPE_ROTATION, rotationFrameAreaIndex, animFrameIndex);
        }

        // 处理Scale关键帧
        if (scaleKeyFrameList.size() > 0) {
            int scaleFrameAreaIndex = judgeFrameArea(TYPE_SCALE, animFrameIndex);
            setCurrentKeyFrameValue(TYPE_SCALE, scaleFrameAreaIndex, animFrameIndex);
        }

        // 处理Alpha关键帧
        if (alphaKeyFrameList.size() > 0) {
            int pathFrameAreaIndex = judgeFrameArea(TYPE_ALPHA, animFrameIndex);
            setCurrentKeyFrameValue(TYPE_ALPHA, pathFrameAreaIndex, animFrameIndex);
        }

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
        mMatrix.preScale(mScaleX, mScaleY, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        mMatrix.preRotate(mRotation, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
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

    private void setCurrentKeyFrameValue(int type, int keyFrameAreaIndex, long animFrameIndex) {
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
                //所有关键帧区间内
                if (type == TYPE_POS || type == TYPE_PATH) {
                    //Position & Path
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
                } else if (type == TYPE_ROTATION || type == TYPE_ALPHA) {
                    //Rotation & Alpha
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameAreaIndex);
                    //上一个KeyFrame坐标
                    float lastValue = Float.valueOf(targetKeyFrame.lastKeyFrame.value);
                    //当前KeyFrame点坐标
                    float currentValue = Float.valueOf(targetKeyFrame.value);
                    //两个关键帧的时间差
                    long lastPosKeyFrameTime = targetKeyFrame.lastKeyFrame.time;
                    long currentPosKeyFrameTime = targetKeyFrame.time;
                    long keyframeDuration = currentPosKeyFrameTime - lastPosKeyFrameTime;
                    Interpolator interpolator = ((EFFloatKeyFrame) targetKeyFrame).interpolator;
                    //计算KeyFrame差值器input,即当前区间的动画执行时间进度百分比
                    float keyframeTimeFraction = (animFrameIndex - lastPosKeyFrameTime) / (float) keyframeDuration;
                    //计算KeyFrame差值器，计算返回后的结果进度百分比
                    float realTimeFraction = interpolator.getInterpolation(keyframeTimeFraction);
                    //计算设置结果
                    float realValueX = ((EFFloatKeyFrame) targetKeyFrame).evaluator.evaluate(realTimeFraction, lastValue, currentValue);
                    if (type == TYPE_ROTATION) {
                        setRotation(realValueX);
                    } else {
                        //因为paint.setAlpha的参数取值为[0,255]，这里需要转换一下
                        setAlpha(AlphaUtils.convert(realValueX));
                    }
                } else {
                    //Scale
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameAreaIndex);
                    //上一个KeyFrame坐标
                    String lastValue = targetKeyFrame.lastKeyFrame.value;
                    String[] controlValueArray = lastValue.split(",");
                    float lastValueScaleX = Float.valueOf(controlValueArray[0]);
                    float lastValueScaleY = Float.valueOf(controlValueArray[1]);
                    //当前KeyFrame点坐标
                    String currentValue = targetKeyFrame.value;
                    String[] currentValueArray = currentValue.split(",");
                    float currentValueScaleX = Float.valueOf(currentValueArray[0]);
                    float currentValueScaleY = Float.valueOf(currentValueArray[1]);
                    //两个关键帧的时间差
                    long lastPosKeyFrameTime = targetKeyFrame.lastKeyFrame.time;
                    long currentPosKeyFrameTime = targetKeyFrame.time;
                    long keyframeDuration = currentPosKeyFrameTime - lastPosKeyFrameTime;
                    Interpolator interpolator = ((EFFloatKeyFrame) targetKeyFrame).interpolator;
                    //计算KeyFrame差值器input,即当前区间的动画执行时间进度百分比
                    float keyframeTimeFraction = (animFrameIndex - lastPosKeyFrameTime) / (float) keyframeDuration;
                    //计算KeyFrame差值器，计算返回后的结果进度百分比
                    float realTimeFraction = interpolator.getInterpolation(keyframeTimeFraction);
                    //计算设置结果
                    float realValueX = ((EFFloatKeyFrame) targetKeyFrame).evaluator.evaluate(realTimeFraction, lastValueScaleX, currentValueScaleX);
                    float realValueY = ((EFFloatKeyFrame) targetKeyFrame).evaluator.evaluate(realTimeFraction, lastValueScaleY, currentValueScaleY);
                    setScaleX(realValueX);
                    setScaleY(realValueY);
                }
            } else {
                //超过最后一个区间，保持最后关键帧属性值
                if (type == TYPE_POS || type == TYPE_PATH) {
                    //Position & Path
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameSize - 1);
                    //当前KeyFrame点坐标
                    String currentValue = targetKeyFrame.value;
                    String[] currentValueArray = currentValue.split(",");
                    float currentValuePosX = Float.valueOf(currentValueArray[0]);
                    float currentValuePosY = Float.valueOf(currentValueArray[1]);
                    setCenterPosX(currentValuePosX);
                    setCenterPosY(currentValuePosY);
                } else if (type == TYPE_ROTATION || type == TYPE_ALPHA) {
                    //Rotation & Alpha
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameSize - 1);
                    float currentValue = Float.valueOf(targetKeyFrame.value);
                    if (type == TYPE_ROTATION) {
                        setRotation(currentValue);
                    } else {
                        setAlpha(AlphaUtils.convert(currentValue));
                    }
                } else {
                    //Scale
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameSize - 1);
                    String currentValue = targetKeyFrame.value;
                    String[] currentValueArray = currentValue.split(",");
                    float currentValueScaleX = Float.valueOf(currentValueArray[0]);
                    float currentValueScaleY = Float.valueOf(currentValueArray[1]);
                    setScaleX(currentValueScaleX);
                    setScaleY(currentValueScaleY);
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
        defaultPaint.setAlpha((int) mAlpha);
        canvas.drawBitmap(mBitmap, mMatrix, defaultPaint);
    }


}
