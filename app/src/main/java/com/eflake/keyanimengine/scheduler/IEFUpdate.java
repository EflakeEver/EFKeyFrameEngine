package com.eflake.keyanimengine.scheduler;


import android.graphics.Canvas;
import android.graphics.Paint;

public interface IEFUpdate {

    void update(int deltaTime);

    void draw(Canvas canvas, Paint defaultPaint);

    boolean isPaused();
}
