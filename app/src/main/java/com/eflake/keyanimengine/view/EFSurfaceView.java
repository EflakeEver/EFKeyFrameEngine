package com.eflake.keyanimengine.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import com.eflake.keyanimengine.main.R;
import com.eflake.keyanimengine.scheduler.EFScheduler;
import com.eflake.keyanimengine.utils.LoadImgUtils;

public class EFSurfaceView extends EFSurfaceViewBase implements SurfaceHolder.Callback, Runnable {

    private static final long REFRESH_SLEEP_TIME = 30; //绘制频率,默认为100ms,即1秒绘制10次
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
            EFScheduler.getInstance().update((int) mDeltaTime, mCanvas, mDefaultPaint);
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
            long drawTime = (System.currentTimeMillis() - startTime);
            Log.e("eflake", "draw time = " + drawTime);
            if (drawTime <= 30) {
                mDeltaTime = REFRESH_SLEEP_TIME - drawTime;
            } else {
                mDeltaTime = drawTime;
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
//        mCucumberResAnchorSprite = new EFSprite(mContext, R.mipmap.cucumber_new, 100, 100, EFSprite.ANCHOR_POINT_TYPE_CENTER);

    }

    /*
    * 初始化SurfaceHolder
    * */
    protected void initSurfaceHolder(SurfaceHolder surfaceHolder) {
    }

}
