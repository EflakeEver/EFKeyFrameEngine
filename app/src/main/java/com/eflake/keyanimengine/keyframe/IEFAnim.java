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

    int getViewPortPosX(int currentPosX);

    int getViewPortPosY(int currentPosY);

    void setName(String name);

    String getName();

    void draw(Canvas canvas, Paint defaultPaint);
}
