package com.eflake.keyanimengine.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.concurrent.atomic.AtomicBoolean;

/*
* 抽象基类，包含整个SurfaceView动画刷新开关
* */
public abstract class EFSurfaceViewBase extends SurfaceView {
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public EFSurfaceViewBase(Context context) {
        super(context);
    }

    public EFSurfaceViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EFSurfaceViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void enableAnim() {
        isRunning.compareAndSet(false, true);
    }

    public void disableAnim() {
        isRunning.compareAndSet(true, false);
    }

    public boolean isAnimEnable() {
        return isRunning.get();
    }

}
