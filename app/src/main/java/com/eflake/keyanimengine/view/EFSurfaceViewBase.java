package com.eflake.keyanimengine.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.concurrent.atomic.AtomicBoolean;

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

    public void enableKeyFrameAnim() {
        isRunning.compareAndSet(false, true);
    }

    public void disableKeyFrameAnim() {
        isRunning.compareAndSet(true, false);
    }

    public boolean isKeyFrameEnable() {
        return isRunning.get();
    }

}
