package com.eflake.keyanimengine.base;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.eflake.keyanimengine.action.EFAction;

public interface IEFNode {
    void addChild(EFNode node);

    void removeChild(EFNode node);

    void removeAllChild(EFNode node);

    void runAction(EFAction action);

    String getTag();

    void setTag(String tag);

    void update(int deltaTime, Canvas canvas, Paint defaultPaint);

    void pause(boolean isNeedPause);

    boolean isPaused();

    void draw(Canvas canvas, Paint defaultPaint);
}
