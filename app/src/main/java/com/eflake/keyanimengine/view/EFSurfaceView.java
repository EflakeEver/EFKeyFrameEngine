package com.eflake.keyanimengine.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import com.eflake.keyanimengine.main.R;
import com.eflake.keyanimengine.scheduler.EFScheduler;
import com.eflake.keyanimengine.utils.LoadImgUtils;

public class EFSurfaceView extends EFSurfaceViewBase implements SurfaceHolder.Callback {

    private static final long REFRESH_SLEEP_TIME = 10; //绘制频率
    public static final int UPDATE_EVENT = 0;
    public static final String UPDATE_THREAD_NAME = "update";
    protected int mCanvasWidth;
    protected int mCanvasHeight;
    protected Thread animThread;
    protected SurfaceHolder mSurfaceHolder;
    protected Canvas mCanvas;
    protected Context mContext;
    private Paint mDefaultPaint;
    private long mDeltaTime = REFRESH_SLEEP_TIME;
    public Bitmap mBitmap;
    public int mPositionX = 100;
    public int mPositionY = 100;
    public Matrix mMatrix;
    public Rect mRect;
    private HandlerThread handlerThread;
    private Handler mHandler;
    private long mLastTime;
    private long mFrameRate;
    private Paint mTextPaint;
    private int mFrameRateCount;
    private String mAverageFrameRate = "";
    private long mFrameRateSum;
    private long mCountTime;
    public final Object lockObj = new Object();
    private long mDrawTime;

    public EFSurfaceView(Context context, int width, int height) {
        this(context);
        mCanvasWidth = width;
        mCanvasHeight = height;
    }

    public EFSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public EFSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EFSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mBitmap = LoadImgUtils.ReadResourceBitMap(mContext, R.mipmap.red_packet);
        mMatrix = new Matrix();
        mRect = new Rect(mPositionX, mPositionY, mBitmap.getWidth(), mBitmap.getHeight());
        initDefaultPaint();
        initSurfaceHolder(mSurfaceHolder);
        initSprite();
    }

    private void initDefaultPaint() {
        mDefaultPaint = new Paint();
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setColor(mContext.getResources().getColor(R.color.white));
        mDefaultPaint.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.default_txt_size));
        mTextPaint = new Paint();
        mTextPaint.setColor(0xff000000);
        mTextPaint.setTextSize(55);
    }

    /*
    * SurfaceHolder生命周期回调方法
    * */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        enableKeyFrameAnim();
        handlerThread = new HandlerThread(UPDATE_THREAD_NAME);
        handlerThread.start();
        mCountTime = System.currentTimeMillis();
        mHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_EVENT:
                        long frameDuration = System.currentTimeMillis() - mLastTime;
                        long duration = System.currentTimeMillis() - mCountTime;
//                        Log.e("@@@", "frame duration = " + frameDuration);
                        mLastTime = System.currentTimeMillis();
                        constantUpdate(frameDuration);
                        if (isKeyFrameEnable()) {
                            mHandler.sendEmptyMessageDelayed(0, REFRESH_SLEEP_TIME);
                        }
                        //帧率update平均计算
                        mFrameRate = 1000 / frameDuration;
                        mFrameRateCount++;
                        mFrameRateSum += mFrameRate;
                        if (duration >= 1000) {
                            mCountTime = System.currentTimeMillis();
                            mAverageFrameRate = String.valueOf(mFrameRateSum / mFrameRateCount);
                            mFrameRateCount = 0;
                            mFrameRateSum = 0;
                        }
                        break;
                }
                return false;
            }
        });
        mHandler.sendEmptyMessage(0);
        animThread = new AnimThread();
        animThread.start();
    }

    private void constantUpdate(long frameDuration) {
//        if (mDrawTime <= REFRESH_SLEEP_TIME) {
        synchronized (lockObj) {
            lockObj.notify();
        }
//        } else {
//            Log.e("@@@", "stay frame");
//            mDrawTime -= REFRESH_SLEEP_TIME;
//        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        disableKeyFrameAnim();
        mHandler.removeCallbacksAndMessages("update");
        handlerThread.quit();
    }

    /*
    * 初始化图片资源
    * */
    protected void initSprite() {
    }

    /*
    * 初始化SurfaceHolder
    * */
    protected void initSurfaceHolder(SurfaceHolder surfaceHolder) {
    }

    private class AnimThread extends Thread {

        @Override
        public void run() {
            while (isKeyFrameEnable()) {
                long startTime = System.currentTimeMillis();
                try {
                    mCanvas = mSurfaceHolder.lockCanvas();
                    EFScheduler.getInstance().update((int) mDeltaTime, mCanvas, mDefaultPaint);
                    mCanvas.drawText("Base :" + mAverageFrameRate, 0, 150, mTextPaint);
                } catch (Exception e) {
                    Log.d("eflake", e.toString());
                } finally {
                    if (mCanvas != null) {
                        try {
                            long waitTime = System.currentTimeMillis();
                            mDrawTime = (System.currentTimeMillis() - startTime);
//                            Log.e("&&&", "drawTime = " + String.valueOf(mDrawTime));
                            synchronized (lockObj) {
                                lockObj.wait();
                            }
//                            Log.e("&&&", "wait time = " + String.valueOf(System.currentTimeMillis() - waitTime));
                            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
//                            mDrawTime = 0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
