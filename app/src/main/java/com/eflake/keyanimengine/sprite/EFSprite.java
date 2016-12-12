package com.eflake.keyanimengine.sprite;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.eflake.keyanimengine.base.EFNode;
import com.eflake.keyanimengine.utils.LoadImgUtils;

/**
 * 精灵类
 *
 * @author eflake
 */
public class EFSprite extends EFNode{
    public static final int ANCHOR_POINT_TYPE_CENTER = 0;
    public static final int ANCHOR_POINT_TYPE_LEFT_TOP = 1;
    public static final int ANCHOR_POINT_TYPE_LEFT_BOTTOM = 2;
    public static final int ANCHOR_POINT_TYPE_RIGHT_TOP = 3;
    public static final int ANCHOR_POINT_TYPE_RIGHT_BOTTOM = 4;

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public EFSprite(Context context, int resId, int startPosX, int startPosY) {
        initBitmap(context, resId);
        calculateNormalPosAndRect(startPosX, startPosY);
    }

    public EFSprite(Context context, int resId, int anchorPosX, int anchorPosY, int anchorPointType) {
        initBitmap(context, resId);
        calculateAnchorPosAndRect(anchorPosX, anchorPosY, anchorPointType);
    }

    private void initBitmap(Context context, int resId) {
        mBitmap = LoadImgUtils.ReadResourceBitMap(context, resId);
        mHeight = mBitmap.getHeight();
        mWidth = mBitmap.getWidth();
    }

    private void calculateNormalPosAndRect(int startPosX, int startPosY) {
        mStartPosX = startPosX;
        mStartPosY = startPosY;
        mCenterPosX = mStartPosX + mWidth / 2;
        mCenterPosY = mStartPosY + mHeight / 2;
//        mRect = new Rect(0, 0, mWidth, mHeight);
    }

    private void calculateAnchorPosAndRect(int anchorPosX, int anchorPosY, int anchorPointType) {
        switch (anchorPointType) {
            case ANCHOR_POINT_TYPE_CENTER:
                mCenterPosX = anchorPosX;
                mCenterPosY = anchorPosY;
                mStartPosX = mCenterPosX - mWidth / 2;
                mStartPosY = mCenterPosY - mHeight / 2;
                break;
            case ANCHOR_POINT_TYPE_LEFT_TOP:
                mStartPosX = anchorPosX;
                mStartPosY = anchorPosY;
                mCenterPosX = mStartPosX + mWidth / 2;
                mCenterPosY = mStartPosY + mHeight / 2;
                break;
            case ANCHOR_POINT_TYPE_LEFT_BOTTOM:
                //TODO
                break;
            case ANCHOR_POINT_TYPE_RIGHT_TOP:
                //TODO
                break;
            case ANCHOR_POINT_TYPE_RIGHT_BOTTOM:
                //TODO
                break;
        }
        mHeight = mBitmap.getHeight();
        mWidth = mBitmap.getWidth();
//        mRect = new Rect(mStartPosX, mStartPosY, mStartPosX + mWidth, mStartPosY + mHeight);
    }


    @Override
    public void update(int deltaTime, Canvas canvas, Paint defaultPaint) {
        Log.e("eflake","EFSprite update");
        canvas.drawBitmap(mBitmap,getMatrix(),defaultPaint);
//        canvas.drawBitmap(mBitmap, mStartPosX, mStartPosY, defaultPaint);
    }

    private Matrix getMatrix() {
        mMatrix = new Matrix();
        return mMatrix;
    }
}
