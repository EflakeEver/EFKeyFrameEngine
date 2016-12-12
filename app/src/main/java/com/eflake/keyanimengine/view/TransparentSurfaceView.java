package com.eflake.keyanimengine.view;


import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.eflake.keyanimengine.main.R;
import com.eflake.keyanimengine.scheduler.EFScheduler;
import com.eflake.keyanimengine.sprite.EFSprite;

public class TransparentSurfaceView extends EFSurfaceView {


    private EFSprite mCucumberSprite;
    private EFSprite mCucumberNewSprite;

    public TransparentSurfaceView(Context context, int width, int height) {
        super(context, width, height);
    }

    public TransparentSurfaceView(Context context) {
        super(context);
    }

    public TransparentSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initBitMap() {
        //加载黄瓜
        mCucumberSprite = new EFSprite(mContext, R.mipmap.cucumber, 100, 100);
        mCucumberNewSprite = new EFSprite(mContext, R.mipmap.cucumber_new, 100, 100, EFSprite.ANCHOR_POINT_TYPE_CENTER);
        mCucumberSprite.setTag("cucumber");
        mCucumberNewSprite.setTag("new_cucumber");
        EFScheduler.getInstance().addTarget(mCucumberSprite);
        EFScheduler.getInstance().addTarget(mCucumberNewSprite);
    }

    @Override
    protected void initSurfaceHolder(SurfaceHolder surfaceHolder) {
        //设置层级在所有View之上,且背景为透明
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

}
