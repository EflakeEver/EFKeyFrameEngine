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
    private static final long REFRESH_SLEEP_TIME = 33; //更新频率
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
    private String mCanvasWaitTime;//线程等待时间
    private String mCanvasCostAllTime;//绘制计算时间
    private long mLastCanvasUnlockTime;
    private long mLastCanvasUnlockCalTime;
    private int mCanvasUnlockRateCount;
    private long mCanvasUnlockRateSum;
    private int mCanvasCostAllTimeCount;
    private long mCanvasCostAllSum;
    private int mCanvasWaitTimeCount;
    private long mCanvasWaitSum;
    protected Context mContext;
    private long mLastDeltaTime;
    private long mCanvaseUpdateSum;
    private long mCanvaseDrawSum;
    private String mCanvasUpdateTime;
    private String mCanvasDrawTime;
    private int mCanvasLockTimeCount;
    private long mCanvasLockSum;
    private String mCanvasLockTime;
    private long mLockDuration;
    private long mAllDuration;
    private long mUpdateDuration;
    private long mDrawDuration;
    private long mWaitDuration;
    private long mUnlockDuration;
    private long mCanvasUnlockSum;
    private String mCanvasUnlockTime;
    private long mFrameRateSum;
    private String mCanvasRate;
    private int mTextHeight;

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
        mTextPaint.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.default_paint_text_size));
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
                try {
                    long beforeWaitTime = System.currentTimeMillis();
                    synchronized (lockObj) {
                        lockObj.wait();
                    }
                    mWaitDuration = System.currentTimeMillis() - beforeWaitTime;
                    long beforeLockTime = System.currentTimeMillis();
                    mCanvas = mSurfaceHolder.lockCanvas();
//                    Log.e(LOG_TAG, "lock time = " + String.valueOf(System.currentTimeMillis() - beforeLockTime));
                    mLockDuration = System.currentTimeMillis() - beforeLockTime;
                    mAllDuration = System.currentTimeMillis() - mLastDeltaTime;
//                    Log.e(LOG_TAG, "allDuration time = " + mAllDuration);
                    mLastDeltaTime = System.currentTimeMillis();
                    long beforeUpdateTime = System.currentTimeMillis();
                    EFScheduler.getInstance().onCanvasUpdate(mAllDuration);
                    mUpdateDuration = System.currentTimeMillis() - beforeUpdateTime;
//                    Log.e("LOG_TAG", "update cost time = " + String.valueOf(updateDuration));
                    long beforeDrawTime = System.currentTimeMillis();
                    EFScheduler.getInstance().onCanvasDraw(mCanvas, mDefaultPaint);
                    mDrawDuration = System.currentTimeMillis() - beforeDrawTime;
//                    Log.e("LOG_TAG, "draw cost time = " + String.valueOf(System.currentTimeMillis() - beforeDrawTime));
                    mTextHeight = mContext.getResources().getDimensionPixelSize(R.dimen.default_paint_text_size);
                    mCanvas.drawText(getFrameInfo(), 0, mTextHeight * 5, mTextPaint);
                    mCanvas.drawText(getAnimAllCostInfo(), 0, mTextHeight * 6, mTextPaint);
                    mCanvas.drawText(getUpdateInfo(), 0, mTextHeight * 7, mTextPaint);
                    mCanvas.drawText(getDrawInfo(), 0, mTextHeight * 8, mTextPaint);
                    mCanvas.drawText(getWaitInfo(), 0, mTextHeight * 9, mTextPaint);
                    mCanvas.drawText(getCanvasLockInfo(), 0, mTextHeight * 10, mTextPaint);
                    mCanvas.drawText(getCanvasUnlockInfo(), 0, mTextHeight * 11, mTextPaint);
                } catch (Exception e) {
                    Log.d(LOG_TAG, e.toString());
                } finally {
                    if (mCanvas != null) {
//                        try {
//                            Log.e(LOG_TAG, "wait cost time = " + String.valueOf(waitDuration));
                        long beforeUnlockTime = System.currentTimeMillis();
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                        mUnlockDuration = System.currentTimeMillis() - beforeUnlockTime;
                        calculateCostTime(mAllDuration, mLockDuration, mUpdateDuration, mDrawDuration, mWaitDuration, mUnlockDuration);
//                            Log.e(LOG_TAG, "unlock cost time = " + String.valueOf(System.currentTimeMillis() - beforeUnlockTime));
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }
        }
    }

    private void calculateCostTime(long allDuration, long lockDuration, long updateDuration, long drawDuration, long waitDuration, long unlockTime) {
        mCanvasCostAllTimeCount++;
        mCanvasCostAllSum += allDuration;
        mCanvaseUpdateSum += updateDuration;
        mCanvaseDrawSum += drawDuration;
        mCanvasWaitSum += waitDuration;
        mCanvasLockSum += lockDuration;
        mCanvasUnlockSum += unlockTime;
        long frameRate = 1000 / allDuration;
        mFrameRateSum += frameRate;

        if (mCanvasCostAllTimeCount == CAL_SAMPLE_TIME) {
            mCanvasCostAllTime = String.valueOf(mCanvasCostAllSum / mCanvasCostAllTimeCount);
            mCanvasUpdateTime = String.valueOf(mCanvaseUpdateSum / mCanvasCostAllTimeCount);
            mCanvasDrawTime = String.valueOf(mCanvaseDrawSum / mCanvasCostAllTimeCount);
            mCanvasLockTime = String.valueOf(mCanvasLockSum / mCanvasCostAllTimeCount);
            mCanvasUnlockTime = String.valueOf(mCanvasUnlockSum / mCanvasCostAllTimeCount);
            mCanvasWaitTime = String.valueOf(mCanvasWaitSum / mCanvasCostAllTimeCount);
            mCanvasRate = String.valueOf(mFrameRateSum / mCanvasCostAllTimeCount);
            mCanvasCostAllTimeCount = 0;
            mCanvasCostAllSum = 0;
            mCanvaseDrawSum = 0;
            mCanvaseUpdateSum = 0;
            mCanvasWaitSum = 0;
            mCanvasUnlockSum = 0;
            mCanvasLockSum = 0;
            mFrameRateSum = 0;
        }
    }

    private String getDrawInfo() {
        return "平均渲染耗时: " + mCanvasDrawTime;
    }

    private String getUpdateInfo() {
        return "平均计算耗时: " + mCanvasUpdateTime;
    }

    private String getAnimAllCostInfo() {
        return "平均总耗时: " + mCanvasCostAllTime;
    }

    private String getFrameInfo() {
        return "平均帧率 :" + mCanvasRate;
    }

    private String getWaitInfo() {
        return "平均等待时间 :" + mCanvasWaitTime;
    }

    private String getCanvasUnlockInfo() {
        return "平均Canvas-Unlock耗时: " + mCanvasUnlockTime;
    }

    private String getCanvasLockInfo() {
        return "平均Canvas-Lock耗时: " + mCanvasLockTime;
    }
}
