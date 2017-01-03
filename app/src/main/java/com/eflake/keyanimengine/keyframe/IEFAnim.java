package com.eflake.keyanimengine.keyframe;


import android.graphics.Canvas;
import android.graphics.Paint;

public interface IEFAnim {
    void setDuration(long duration);

    void addElement(String key, EFElement element);

    void removeAllElements();

    void removeElementByKey(String key);

    void setViewPort(EFViewPort viewPort);

    void setIsRunning(boolean isRunning);

    boolean isRunning();

    void step(long deltaTime);

    boolean isDone();

    float getViewPortPosX(float currentPosX);

    float getViewPortPosY(float currentPosY);

    void setName(String name);

    void setEndListener(IEFAnimEndListener listener);

    void removeEndListener();

    String getName();

    void draw(Canvas canvas, Paint defaultPaint);
}
