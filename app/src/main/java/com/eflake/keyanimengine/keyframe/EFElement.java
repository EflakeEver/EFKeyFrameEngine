package com.eflake.keyanimengine.keyframe;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.eflake.keyanimengine.evaluator.EFBezierKeepEvaluator;
import com.eflake.keyanimengine.evaluator.EFBezierSuperEvaluator;
import com.eflake.keyanimengine.evaluator.EFKeepEvaluator;
import com.eflake.keyanimengine.evaluator.EFLinearEvaluator;
import com.eflake.keyanimengine.evaluator.IEFBezierFloatEvaluator;
import com.eflake.keyanimengine.sprite.EFSprite;
import com.eflake.keyanimengine.utils.PropertyConvertUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class EFElement extends EFSprite implements IEFElement {
    public static final int TYPE_DEFAULT = -1;
    public static final int TYPE_POS = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_ALPHA = 2;
    public static final int TYPE_SCALE = 3;
    public static final int TYPE_ROTATION = 4;
    public static final int NO_CONTROL_POINT_SIZE = 0;
    public static final int ONE_CONTROL_POINT_SIZE = 2;
    public static final int TWO_CONTROL_POINT_SIZE = 4;

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
    public float mRealCenterX;
    public float mRealCenterY;
    public float mRealStartX;
    public float mRealStartY;
    public float mRealRotation;//旋转角度
    public float mRealAlpha = 100.0f;//不透明度
    public float mRealScaleX = 1.0f;//缩放比例
    public float mRealScaleY = 1.0f;//缩放比例
    private float mViewPortScaleX = 1.0f;
    private float mViewPortScaleY = 1.0f;

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

//    public EFElement(Context context, String path, float anchorPosX, float anchorPosY, EFViewPort viewPort) {
//        super(context, path, anchorPosX, anchorPosY, viewPort);
//        initDefaultKeyFrame();
//    }

    public EFElement(Context context, int resId, float startPosX, float startPosY, float width, float height) {
        super(context, resId, startPosX, startPosY, width, height);
        initDefaultKeyFrame();
    }

    private void initDefaultKeyFrame() {
        //此处的defaultKeyFrame并不放入各个属性List,仅作为初始赋值用
        defaultPosKeyFrame = new EFPosKeyFrame(0, "0,0");
        defaultAlphaKeyFrame = new EFAlphaKeyFrame(0, "100.0");
        defaultRotationKeyFrame = new EFRotationKeyFrame(0, "0");
        defaultScaleKeyFrame = new EFScaleKeyFrame(0, "100.0,100.0");
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
            pathKeyFrame.setEvaluator(new EFBezierSuperEvaluator());
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

        if (getParentNode() != null) {
            //把所有属性的相对坐标，转换为绝对坐标
            convertParentProperty();
        } else {
            initRealValue();
        }
        //转换成对应ViewPort坐标
        convertViewPortProperty();
        //转换成真实Alpha
        convertAlpha();
        //设置应用的变换矩阵
        applyMatrix();
    }

    private void convertViewPortProperty() {
        convertViewPortPos();
        convertViewPortScale();
    }

    private void convertViewPortPos() {
        //Viewport坐标计算是正确的
        mRealCenterX = convertViewPortPosX(mRealCenterX);
        mRealCenterY = convertViewPortPosY(mRealCenterY);
        mRealStartX = mRealCenterX - mWidth / 2.0f;
        mRealStartY = mRealCenterY - mHeight / 2.0f;
    }

    private void convertViewPortScale() {
        //图片根据ViewPort和ScreenSize比例，做缩放处理，以适配各种分辨率
        //TODO 目前仅仅是按屏幕比例对图片做同比缩放，并不能保证图片不被拉伸（因为考虑到父子关系坐标问题），后续需要解决这个问题
        //此处需要注意，如果包含父子关系，子节点的ViewPort Scale，必须基于自身中心位置做缩放，才能保证最终计算结果准确
        mViewPortScaleX = getAnim().mViewport.getWidthFactor();
        mViewPortScaleY = getAnim().mViewport.getHeightFactor();
    }

    private void convertAlpha() {
        mRealAlpha = PropertyConvertUtils.convertAlpha(mRealAlpha);
    }

    private void convertParentProperty() {
        //转换相对坐标为绝对坐标
        mRealCenterX = convertRelativeToRealPosX();
        mRealCenterY = convertRelativeToRealPosY();
        mRealStartX = mRealCenterX - mWidth / 2.0f;
        mRealStartY = mRealCenterY - mHeight / 2.0f;
        mRealRotation = mRotation;
        mRealAlpha = mAlpha;
        mRealScaleX = mScaleX;
        mRealScaleY = mScaleY;
    }

    private void initRealValue() {
        mRealStartX = mStartPosX;
        mRealStartY = mStartPosY;
        mRealCenterX = mCenterPosX;
        mRealCenterY = mCenterPosY;
        mRealRotation = mRotation;
        mRealAlpha = mAlpha;
        mRealScaleX = mScaleX;
        mRealScaleY = mScaleY;
    }

    private void applyMatrix() {
        mMatrix = getMatrix();
        //设置位移矩阵
        mMatrix.setTranslate(mRealStartX, mRealStartY);
        //设置缩放矩阵
        //缩放矩阵包含三种情况，自身缩放，父组件缩放及ViewPort缩放
        //父组件缩放
        if (getParentNode() != null) {
            float parentScaleX = ((EFElement) getParentNode()).mRealScaleX;
            float parentScaleY = ((EFElement) getParentNode()).mRealScaleY;
            if (parentScaleX != 1.0f || parentScaleY != 1.0f) {
                //父组件的Scale，需要针对父组件自身中心位置进行缩放
                mMatrix.preScale(parentScaleX, parentScaleY, ((EFElement) getParentNode()).mRealCenterX - mRealStartX, ((EFElement) getParentNode()).mRealCenterY - mRealStartY);
            }
        }
        //ViewPort缩放 & 自身缩放
        if (mViewPortScaleX != 1.0f || mViewPortScaleY != 1.0f) {
            mMatrix.preScale(mViewPortScaleX * mRealScaleX, mViewPortScaleY * mRealScaleY, mBitmap.getWidth() / 2.0f, mBitmap.getHeight() / 2.0f);
        } else {
            mMatrix.preScale(mRealScaleX, mRealScaleY, mBitmap.getWidth() / 2.0f, mBitmap.getHeight() / 2.0f);
        }
        //设置旋转矩阵
        //旋转矩阵包括两种情况，自身旋转，父组件旋转
        //父组件旋转
        if (getParentNode() != null) {
            float parentRotation = ((EFElement) getParentNode()).mRealRotation;
            if (parentRotation != 0.0f) {
                //父组件的Scale，需要针对父组件自身中心位置进行缩放
                mMatrix.preRotate(parentRotation, ((EFElement) getParentNode()).mRealCenterX - mRealStartX, ((EFElement) getParentNode()).mRealCenterY - mRealStartY);
            }
        }
        //自身旋转
        mMatrix.preRotate(mRealRotation, mBitmap.getWidth() / 2.0f, mBitmap.getHeight() / 2.0f);
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
                        float controlValuePosX = 0.0f;
                        float controlValuePosY = 0.0f;
                        float anotherControlValuePosX = 0.0f;
                        float anotherControlValuePosy = 0.0f;
                        int controlPointValueArraySize = 0;//control字段解析后的值个数
                        if (!TextUtils.isEmpty(controlValue) && controlValue.contains(",")) {
                            String[] controlValueArray = controlValue.split(",");
                            controlPointValueArraySize = controlValueArray.length;
                            if (controlPointValueArraySize == ONE_CONTROL_POINT_SIZE) {
                                controlValuePosX = Float.valueOf(controlValueArray[0]);
                                controlValuePosY = Float.valueOf(controlValueArray[1]);
                            } else if (controlPointValueArraySize == TWO_CONTROL_POINT_SIZE) {
                                controlValuePosX = Float.valueOf(controlValueArray[0]);
                                controlValuePosY = Float.valueOf(controlValueArray[1]);
                                anotherControlValuePosX = Float.valueOf(controlValueArray[2]);
                                anotherControlValuePosy = Float.valueOf(controlValueArray[3]);
                            } else {
                                //TODO 非二阶及三阶情况，暂不支持
                                Log.e(TAG, "else scene");
                            }
                        } else {
                            //一阶情况，control为空，无控制点
                        }
                        Interpolator interpolator = ((EFPathKeyFrame) targetKeyFrame).interpolator;
                        //计算KeyFrame差值器input,即当前区间的动画执行时间进度百分比
                        float keyframeTimeFraction = (animFrameIndex - lastPosKeyFrameTime) / (float) keyframeDuration;
                        //计算KeyFrame差值器，计算返回后的结果进度百分比
                        float realTimeFraction = interpolator.getInterpolation(keyframeTimeFraction);
                        //计算设置结果
                        //需要计算当前控制点个数，据此判断Bezier曲线阶数
                        IEFBezierFloatEvaluator evaluator = ((EFPathKeyFrame) targetKeyFrame).evaluator;
                        float realValueX = 0.0f;
                        float realValueY = 0.0f;
                        if (evaluator instanceof EFBezierKeepEvaluator) {
                            realValueX = evaluator.evaluate(realTimeFraction, lastValuePosX, currentValuePosX, IEFBezierFloatEvaluator.TYPE_X);
                            realValueY = evaluator.evaluate(realTimeFraction, lastValuePosY, currentValuePosY, IEFBezierFloatEvaluator.TYPE_Y);
                        } else {
                            switch (controlPointValueArraySize) {
                                case 0:
                                    ((EFBezierSuperEvaluator) evaluator).initControlPointList();
                                    realValueX = evaluator.evaluate(realTimeFraction, lastValuePosX, currentValuePosX, IEFBezierFloatEvaluator.TYPE_X);
                                    realValueY = evaluator.evaluate(realTimeFraction, lastValuePosY, currentValuePosY, IEFBezierFloatEvaluator.TYPE_Y);
                                    break;
                                case ONE_CONTROL_POINT_SIZE:
                                    ((EFBezierSuperEvaluator) evaluator).initControlPointList(controlValuePosX, controlValuePosY);
                                    realValueX = evaluator.evaluate(realTimeFraction, lastValuePosX, currentValuePosX, IEFBezierFloatEvaluator.TYPE_X);
                                    realValueY = evaluator.evaluate(realTimeFraction, lastValuePosY, currentValuePosY, IEFBezierFloatEvaluator.TYPE_Y);
                                    break;
                                case TWO_CONTROL_POINT_SIZE:
                                    ((EFBezierSuperEvaluator) evaluator).initControlPointList(controlValuePosX, controlValuePosY, anotherControlValuePosX, anotherControlValuePosy);
                                    realValueX = evaluator.evaluate(realTimeFraction, lastValuePosX, currentValuePosX, IEFBezierFloatEvaluator.TYPE_X);
                                    realValueY = evaluator.evaluate(realTimeFraction, lastValuePosY, currentValuePosY, IEFBezierFloatEvaluator.TYPE_Y);
                                    break;
                            }
                        }
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
                        setAlpha(realValueX);
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
                    setScaleX(PropertyConvertUtils.convertScale(realValueX));
                    setScaleY(PropertyConvertUtils.convertScale(realValueY));
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
                        setAlpha(currentValue);
                    }
                } else {
                    //Scale
                    EFKeyFrame targetKeyFrame = (EFKeyFrame) list.get(keyFrameSize - 1);
                    String currentValue = targetKeyFrame.value;
                    String[] currentValueArray = currentValue.split(",");
                    float currentValueScaleX = Float.valueOf(currentValueArray[0]);
                    float currentValueScaleY = Float.valueOf(currentValueArray[1]);
                    setScaleX(PropertyConvertUtils.convertScale(currentValueScaleX));
                    setScaleY(PropertyConvertUtils.convertScale(currentValueScaleY));
                }
            }
        } else {
            //没有关键帧，不计算
        }

    }

    private float convertViewPortPosX(float realCenterX) {
        float result;
        if (getAnim() != null) {
            result = getAnim().getViewPortPosX(realCenterX);
            return result;
        } else {
            return realCenterX;
        }
    }

    private float convertViewPortPosY(float realCenterY) {
        float result;
        if (getAnim() != null) {
            result = getAnim().getViewPortPosY(realCenterY);
            return result;
        } else {
            return realCenterY;
        }
    }

    public void draw(Canvas canvas, Paint defaultPaint) {
        //TODO 不同的元素，应采用不同画笔
        defaultPaint.setAlpha((int) mRealAlpha);
        long beforeAlpha = System.currentTimeMillis();
        if (canvas != null) {
            canvas.drawBitmap(mBitmap, mMatrix, defaultPaint);
        }
        Log.e("##","element :"+mName+",draw time = "+ String.valueOf(System.currentTimeMillis() - beforeAlpha));
    }

}
