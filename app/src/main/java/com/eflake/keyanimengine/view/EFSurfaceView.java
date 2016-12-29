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

public class EFSurfaceView extends EFSurfaceViewBase implements SurfaceHolder.Callback, Runnable {

    private static final long REFRESH_SLEEP_TIME = 10; //绘制频率,默认为100ms,即1秒绘制10次
    private static final int AVERAGE_FRAME_RATE_TIMES = 15;
    public static final int UPDATE_EVENT = 0;
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
    private String mAverageFrameRate;
    private long mFraneRateSume;
    private long mCountTime;

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
        handlerThread = new HandlerThread("update");
        handlerThread.start();
        mCountTime = System.currentTimeMillis();
        mHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_EVENT:
                        //计算帧率
                        long frameDuration = System.currentTimeMillis() - mLastTime;
                        long duration = System.currentTimeMillis() - mCountTime;
                        mFrameRate = 1000 / frameDuration;
                        mFrameRateCount++;
                        mFraneRateSume += mFrameRate;
                        if (duration >= 1000) {
                            mCountTime = System.currentTimeMillis();
                            mAverageFrameRate = String.valueOf(mFraneRateSume / mFrameRateCount);
                            mFrameRateCount = 0;
                            mFraneRateSume = 0;
                        }
                        mLastTime = System.currentTimeMillis();
                        draw();
                        if (isKeyFrameEnable()) {
                            mHandler.sendEmptyMessageDelayed(0, REFRESH_SLEEP_TIME);
                        }
                        break;
                }
                return false;
            }
        });
        mHandler.sendEmptyMessage(0);
//        animThread = new Thread(this);
//        animThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mSurfaceHolder = surfaceHolder;
    }

    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            EFScheduler.getInstance().update((int) mDeltaTime, mCanvas, mDefaultPaint);
            mCanvas.drawText("帧率:" + mAverageFrameRate, 0, 150, mTextPaint);
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
        mHandler.removeCallbacksAndMessages("update");
        handlerThread.quit();
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
            long drawTime = (System.currentTimeMillis() - startTime);
//            Log.e("eflake", "draw time = " + drawTime);
            if (drawTime <= REFRESH_SLEEP_TIME) {
                mDeltaTime = REFRESH_SLEEP_TIME - drawTime;
            } else {
                mDeltaTime = 0;
            }
            try {
                Thread.sleep(mDeltaTime);
            } catch (Exception e) {
                // handle exception
            }
        }
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

}
