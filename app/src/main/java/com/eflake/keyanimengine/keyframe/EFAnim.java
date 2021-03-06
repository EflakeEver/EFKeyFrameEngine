package com.eflake.keyanimengine.keyframe;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.eflake.keyanimengine.utils.ScreenDimenUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class EFAnim implements IEFAnim {
    private long mDurationFrame;//总帧数
    private String mName;//动画名称
    private long mElapsedFrame;//动画已运行帧数
    public EFViewPort mViewport;//显示的ViewPort
    private boolean mIsRunning;//动画是否正在执行
    public HashMap<String, EFElement> mElements = new LinkedHashMap<>();//子元素
    public IEFAnimEndListener mIEFAnimEndListener;

    @Override
    public void setDuration(long duration) {
        mDurationFrame = duration;
    }

    @Override
    public void addElement(String key, EFElement element) {
        if (element == null) {
            return;
        }
        if (mElements.containsKey(key)) {
            return;
        }
        mElements.put(key, element);
        element.setAnim(this);
    }


    @Override
    public void removeAllElements() {

    }

    @Override
    public void removeElementByKey(String key) {

    }

    @Override
    public void setViewPort(EFViewPort viewPort) {
        mViewport = viewPort;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.mIsRunning = isRunning;
    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    @Override
    public void step(long deltaTime) {
        long beforeStep = System.currentTimeMillis();
        Iterator<Map.Entry<String, EFElement>> iterator = mElements.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EFElement> entry = iterator.next();
            entry.getValue().updateAnim(mElapsedFrame);
        }
        mElapsedFrame++;
        long stepCost = System.currentTimeMillis() - beforeStep;
    }

    @Override
    public boolean isDone() {
        return mElapsedFrame >= mDurationFrame && mDurationFrame != 0;
    }

    @Override
    public float getViewPortPosX(float currentPosX) {
        return ScreenDimenUtils.getDeviceWidth() / mViewport.widthPixel * currentPosX;
    }

    @Override
    public float getViewPortPosY(float currentPosY) {
        return ScreenDimenUtils.getDeviceHeight() / mViewport.heightPixel * currentPosY;
    }

    @Override
    public void setName(String name) {
        mName = name;
    }

    @Override
    public void setEndListener(IEFAnimEndListener listener) {
        this.mIEFAnimEndListener = listener;
    }

    @Override
    public void removeEndListener() {
        if (mIEFAnimEndListener != null) {
            mIEFAnimEndListener = null;
        }
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void draw(Canvas canvas, Paint defaultPaint) {
        long beforeDraw = System.currentTimeMillis();
        Iterator<Map.Entry<String, EFElement>> iterator = mElements.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EFElement> entry = iterator.next();
            entry.getValue().draw(canvas, defaultPaint);
        }
        long drawCost = System.currentTimeMillis() - beforeDraw;
        Log.e("@@", "drawCost = " + drawCost);
    }

    public void onAnimDone() {
        if (mIEFAnimEndListener != null) {
            mIEFAnimEndListener.onAnimEnd();
            removeEndListener();
        }
    }


}
