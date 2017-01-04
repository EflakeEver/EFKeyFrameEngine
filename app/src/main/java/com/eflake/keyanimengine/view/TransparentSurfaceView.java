package com.eflake.keyanimengine.view;


import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.eflake.keyanimengine.utils.LoadImgUtils;

public class TransparentSurfaceView extends EFSurfaceView {



    public TransparentSurfaceView(Context context, int width, int height) {
        super(context, width, height);
    }

    public TransparentSurfaceView(Context context) {
        super(context);
    }

    public TransparentSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    private String getFramePathByName(String frameName) {
        return LoadImgUtils.getFrameAnimPath(frameName);
    }

    private String getFireworkName(int index) {
        return "firework" + "_" + String.valueOf(index);
    }

    @Override
    protected void initSurfaceHolder(SurfaceHolder surfaceHolder) {
        //设置层级在所有View之上,且背景为透明
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

}
