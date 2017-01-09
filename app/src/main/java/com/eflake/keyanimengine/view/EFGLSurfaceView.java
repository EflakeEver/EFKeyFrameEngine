package com.eflake.keyanimengine.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class EFGLSurfaceView extends GLSurfaceView {
    public EFGLSurfaceView(Context context) {
        super(context);
    }

    public EFGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        //设置颜色缓存及深度缓存位数，此参数极大影响性能，会导致fps下降
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        setZOrderOnTop(true);
    }

}
