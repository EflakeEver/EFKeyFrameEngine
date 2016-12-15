package com.eflake.keyanimengine.keyframe;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EFAnim implements IEFAnim {
    public long mDurationFrame;//总帧数
    public HashMap<String, EFElement> mElements = new HashMap<>();//子元素
    public String mName;//动画名称
    public long mElapsedFrame;//动画已运行帧数
    public EFViewPort mViewport;//显示的ViewPort
    public boolean mIsRunning;//动画是否正在执行

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
    }


    @Override
    public void removeAllElements() {

    }

    @Override
    public void removeElementByKey(String key) {

    }

    @Override
    public void setViewPort(EFViewPort viewPort) {

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
        mElapsedFrame++;
        Iterator<Map.Entry<String, EFElement>> iterator = mElements.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EFElement> entry = iterator.next();
            EFElement currentElement = entry.getValue();
            currentElement.updateAnim(mElapsedFrame);
        }
    }

    @Override
    public boolean isDone() {
        return mElapsedFrame >= mDurationFrame && mDurationFrame != 0;
    }

    @Override
    public int getViewPortPosX(int currentPosX) {
        return 0;
    }

    @Override
    public int getViewPortPosY(int currentPosY) {
        return 0;
    }

    @Override
    public void setName(String name) {
        mName = name;
    }

    @Override
    public String getName() {
        return mName;
    }
}
