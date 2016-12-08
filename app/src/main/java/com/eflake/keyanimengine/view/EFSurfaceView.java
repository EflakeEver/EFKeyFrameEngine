package com.eflake.keyanimengine.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

public class EFSurfaceView extends EFSurfaceViewBase implements SurfaceHolder.Callback, Runnable {

    private static final long REFRESH_RATE = 100;
    private int mCanvaseWidth;
    private int mCanvaseHeight;
    private Thread animThread;
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;

    public EFSurfaceView(Context context, int width, int height) {
        this(context);
        mCanvaseWidth = width;
        mCanvaseHeight = height;
    }

    public EFSurfaceView(Context context) {
        super(context);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        initBitMap();
    }


    public EFSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EFSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    * SurfaceHolder生命周期回调方法
    * */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        enableKeyFrameAnim();
        animThread = new Thread(this);
        animThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mSurfaceHolder = surfaceHolder;
    }

    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
        } catch (Exception e) {
            Log.d("eflake", e.toString());
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        disableKeyFrameAnim();
    }

    /*
    * 绘制线程里获取Canvas,以及进行绘制操作
    * */
    @Override
    public void run() {
        while (isKeyFrameEnable()) {
            long startTime = System.currentTimeMillis();
            synchronized (mSurfaceHolder) {
                draw();
            }
            long endTime = System.currentTimeMillis();
            try {
                Thread.sleep(REFRESH_RATE - (endTime - startTime));
            } catch (Exception e) {
                // handle exception
            }
        }
    }

    /*
    * 初始化图片资源
    * */
    private void initBitMap() {

    }

}
