package com.eflake.keyanimengine.base;


import android.graphics.Canvas;
import android.graphics.Paint;

public interface IEFNode {
    void addChild(EFNode node);

    void removeChild(EFNode node);

    void removeAllChild(EFNode node);

    String getTag();

    void setTag(String tag);

    void update(int deltaTime);

    void pause(boolean isNeedPause);

    boolean isPaused();

    void draw(Canvas canvas, Paint defaultPaint);
}
