package com.eflake.keyanimengine.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.eflake.keyanimengine.base.EFNode;
import com.eflake.keyanimengine.utils.LoadImgUtils;

/**
 * 精灵类
 *
 * @author eflake
 */
public class EFSprite extends EFNode implements IEFSprite {
    public static final int ANCHOR_POINT_TYPE_CENTER = 0;
    public static final int ANCHOR_POINT_TYPE_LEFT_TOP = 1;
    public static final int ANCHOR_POINT_TYPE_LEFT_BOTTOM = 2;
    public static final int ANCHOR_POINT_TYPE_RIGHT_TOP = 3;
    public static final int ANCHOR_POINT_TYPE_RIGHT_BOTTOM = 4;
    public Bitmap mBitmap;//位图
    public Matrix mMatrix;//变换矩阵

    /*
    * Path
    * */
  /*  public EFSprite(Context context, String path, float startPosX, float startPosY, EFViewPort viewPort) {
        initBitmapWithViewPort(context, path, viewPort);
        calculateNormalPosAndRect(startPosX, startPosY);
    }*/

    public EFSprite(Context context, String path, float startPosX, float startPosY) {
        initBitmapPath(context, path);
        calculateNormalPosAndRect(startPosX, startPosY);
    }

    public EFSprite(Context context, String path, float anchorPosX, float anchorPosY, int anchorPointType) {
        initBitmapPath(context, path);
        calculateAnchorPosAndRect(anchorPosX, anchorPosY, anchorPointType);
    }

    public EFSprite(Context context, String path, float startPosX, float startPosY, float width, float height) {
        initBitmapPathWithScale(context, path, width, height);
        calculateNormalPosAndRect(startPosX, startPosY);
    }

    public EFSprite(Context context, int resId, float startPosX, float startPosY, float width, float height) {
        initBitmapResWithScale(context, resId, width, height);
        calculateNormalPosAndRect(startPosX, startPosY);
    }

    @Override
    public void initBitmapPath(Context context, String path) {
        mBitmap = LoadImgUtils.ReadFileBitMap(context, path);
        if (mBitmap != null) {
            mHeight = mBitmap.getHeight();
            mWidth = mBitmap.getWidth();
        }
    }

    private void initBitmapPathWithScale(Context context, String path, float width, float height) {
        mBitmap = LoadImgUtils.ReadFileBitMap(context, path, width, height);
        if (mBitmap != null) {
            mHeight = mBitmap.getHeight();
            mWidth = mBitmap.getWidth();
        }
    }


   /* private void initBitmapWithViewPort(Context context, String path, EFViewPort viewPort) {
        float widthFactor = viewPort.getWidthFactor();
        float heightFactor = viewPort.getHeightFactor();
        mBitmap = LoadImgUtils.ReadFileBitMapWithViewPort(context, path, widthFactor, heightFactor);
        if (mBitmap != null) {
            mHeight = mBitmap.getHeight();
            mWidth = mBitmap.getWidth();
        }
    }
*/
    /*
    * Res
    * */
    public EFSprite(Context context, int resId, float startPosX, float startPosY) {
        initBitmapRes(context, resId);
        calculateNormalPosAndRect(startPosX, startPosY);
    }

    public EFSprite(Context context, int resId, float anchorPosX, float anchorPosY, int anchorPointType) {
        initBitmapRes(context, resId);
        calculateAnchorPosAndRect(anchorPosX, anchorPosY, anchorPointType);
    }

    @Override
    public void initBitmapRes(Context context, int resId) {
        mBitmap = LoadImgUtils.ReadResourceBitMap(context, resId);
        if (mBitmap != null) {
            mHeight = mBitmap.getHeight();
            mWidth = mBitmap.getWidth();
        }
    }


    private void initBitmapResWithScale(Context context, int resId, float width, float height) {
        mBitmap = LoadImgUtils.ReadResourceBitMapWithScale(context, resId, width, height);
        if (mBitmap != null) {
            mHeight = mBitmap.getHeight();
            mWidth = mBitmap.getWidth();
        }
    }

    /*
    * Position
    * */
    @Override
    public void calculateNormalPosAndRect(float startPosX, float startPosY) {
        mStartPosX = startPosX;
        mStartPosY = startPosY;
        mCenterPosX = mStartPosX + mWidth / 2;
        mCenterPosY = mStartPosY + mHeight / 2;
    }

    @Override
    public void calculateAnchorPosAndRect(float anchorPosX, float anchorPosY, int anchorPointType) {
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
    }


    protected Matrix getMatrix() {
        if (mMatrix == null) {
            mMatrix = new Matrix();
        }
        return mMatrix;
    }

    protected Bitmap getBitmap() {
        return mBitmap;
    }

}
