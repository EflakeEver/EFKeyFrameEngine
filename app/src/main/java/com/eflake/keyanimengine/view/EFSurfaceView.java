package com.eflake.keyanimengine.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import com.eflake.keyanimengine.main.R;
import com.eflake.keyanimengine.scheduler.EFScheduler;

/*
* 核心基类，处理非UI线程刷新相关原始事件
* */
public class EFSurfaceView extends EFSurfaceViewBase implements SurfaceHolder.Callback {
    private static final String LOG_TAG = EFSurfaceView.class.getSimpleName();
    public static final int CAL_SAMPLE_TIME = 10;
    //update thread相关
    private HandlerThread updateThread;
    private Handler updateThreadHandler;
    public static final String UPDATE_THREAD_NAME = "update_thread";
    public static final int UPDATE_EVENT = 0;//更新事件
    private static final long REFRESH_SLEEP_TIME = 10; //更新频率
    //canvas相关
    protected int mCanvasWidth;
    protected int mCanvasHeight;
    protected SurfaceHolder mSurfaceHolder;
    protected Canvas mCanvas;
    private Paint mDefaultPaint;
    private Paint mTextPaint;
    //anim thread相关
    protected Thread animThread;
    public final Object lockObj = new Object();
    //计算相关
    private String mAverageCanvasUnlockFrameRate;//Canvas绘制帧率
    private String mCanvasWaitTime;//线程等待时间
    private String mCanvasDrawTime;//绘制计算时间
    private long mLastCanvasUnlockTime;
    private long mLastCanvasUnlockCalTime;
    private int mCanvasUnlockRateCount;
    private long mCanvasUnlockRateSum;
    private int mCanvasDrawTimeCount;
    private long mCanvasDrawSum;
    private int mCanvasWaitTimeCount;
    private long mCanvasWaitSum;
    protected Context mContext;
    private long mLastDeltaTime;

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
        initPaint();
        initSurfaceHolder(mSurfaceHolder);
    }

    private void initPaint() {
        mDefaultPaint = new Paint();
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setColor(mContext.getResources().getColor(R.color.white));
        mDefaultPaint.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.default_txt_size));
        mTextPaint = new Paint();
        mTextPaint.setColor(0xff000000);
        mTextPaint.setTextSize(55);
    }

    /*
    * SurfaceHolder生命周期
    * */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        enableAnim();
        initUpdateThread();
        initAnimThread();
    }

    private void initUpdateThread() {
        updateThread = new HandlerThread(UPDATE_THREAD_NAME);
        updateThread.start();
        updateThreadHandler = new Handler(updateThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_EVENT:
//                        long frameDuration = System.currentTimeMillis() - mLastTime;
//                        Log.e(LOG_TAG, "update frequency = " + frameDuration);
                        constantUpdate();
                        if (isAnimEnable()) {
                            updateThreadHandler.sendEmptyMessageDelayed(0, REFRESH_SLEEP_TIME);
                        }
                        break;
                }
                return false;
            }
        });
        updateThreadHandler.sendEmptyMessage(0);
    }

    private void initAnimThread() {
        animThread = new AnimThread();
        animThread.start();
    }

    private void constantUpdate() {
        synchronized (lockObj) {
            lockObj.notify();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        disableAnim();
        updateThreadHandler.removeCallbacksAndMessages("update");
        updateThread.quit();
    }

    protected void initSurfaceHolder(SurfaceHolder surfaceHolder) {
    }

    private class AnimThread extends Thread {

        @Override
        public void run() {
            while (isAnimEnable()) {
                long beforeDrawTime = System.currentTimeMillis();
                try {
                    mCanvas = mSurfaceHolder.lockCanvas();
                    long deltaTime = System.currentTimeMillis() - mLastDeltaTime;
//                    Log.e(LOG_TAG,"delta time = "+ deltaTime);
                    mLastDeltaTime = System.currentTimeMillis();
                    EFScheduler.getInstance().onCanvasUpdate((int) deltaTime, mCanvas, mDefaultPaint);
                    mCanvas.drawText(getInfo(), 0, 150, mTextPaint);
                } catch (Exception e) {
                    Log.d(LOG_TAG, e.toString());
                } finally {
                    if (mCanvas != null) {
                        try {
                            long beforeWaitTime = System.currentTimeMillis();
                            calculateCanvasDrawTime(beforeDrawTime);
                            synchronized (lockObj) {
                                lockObj.wait();
                            }
                            calculateCanvasFrequency();
                            calculateWaitTime(beforeWaitTime);
                            long beforeUnlockTime = System.currentTimeMillis();
                            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
//                            Log.e(LOG_TAG,"unlock cost time = "+ String.valueOf(System.currentTimeMillis()- beforeUnlockTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private String getInfo() {
        return "帧率 :" + mAverageCanvasUnlockFrameRate + " " +
                "平均计算绘制时间: "  + mCanvasDrawTime;
//                + " " + "平均等待时间 :" + mCanvasWaitTime;
    }

    private void calculateCanvasDrawTime(long beforeDrawTime) {
        mCanvasDrawTimeCount++;
        mCanvasDrawSum += System.currentTimeMillis() - beforeDrawTime;
        if (mCanvasDrawTimeCount == CAL_SAMPLE_TIME) {
            mCanvasDrawTime = String.valueOf(mCanvasDrawSum / mCanvasDrawTimeCount);
            mCanvasDrawTimeCount = 0;
            mCanvasDrawSum = 0;
        }
    }

    private void calculateWaitTime(long beforeWaitTime) {
        mCanvasWaitTimeCount++;
        mCanvasWaitSum += System.currentTimeMillis() - beforeWaitTime;
        if (mCanvasWaitTimeCount == CAL_SAMPLE_TIME) {
            mCanvasWaitTime = String.valueOf(mCanvasWaitSum / mCanvasWaitTimeCount);
            mCanvasWaitTimeCount = 0;
            mCanvasWaitSum = 0;
        }
    }

    private void calculateCanvasFrequency() {
        long canvasUnlockOnceDuration = System.currentTimeMillis() - mLastCanvasUnlockTime;
        mLastCanvasUnlockTime = System.currentTimeMillis();
        long canvasUnlockCalDuration = System.currentTimeMillis() - mLastCanvasUnlockCalTime;
        long canvasUnlockRate = 1000 / canvasUnlockOnceDuration;
        mCanvasUnlockRateCount++;
        mCanvasUnlockRateSum += canvasUnlockRate;
        if (canvasUnlockCalDuration >= 1000) {
            mLastCanvasUnlockCalTime = System.currentTimeMillis();
            mAverageCanvasUnlockFrameRate = String.valueOf(mCanvasUnlockRateSum / mCanvasUnlockRateCount);
            mCanvasUnlockRateCount = 0;
            mCanvasUnlockRateSum = 0;
        }
    }

}
