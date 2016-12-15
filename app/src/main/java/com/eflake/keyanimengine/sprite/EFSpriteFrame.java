package com.eflake.keyanimengine.sprite;

import android.content.Context;
import android.graphics.Bitmap;

import com.eflake.keyanimengine.utils.LoadImgUtils;

/**
 * 精灵帧类
 *
 * @author eflake
 */
public class EFSpriteFrame {
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;

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

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    /*
    * Path
    * */
    public EFSpriteFrame(Context context, String path, int startPosX, int startPosY) {
        initBitmapPath(context, path);
    }

    public EFSpriteFrame(Context context, String path, int anchorPosX, int anchorPosY, int anchorPointType) {
        initBitmapPath(context, path);
    }

    private void initBitmapPath(Context context, String path) {
        mBitmap = LoadImgUtils.ReadFileBitMap(context, path);
        mHeight = mBitmap.getHeight();
        mWidth = mBitmap.getWidth();
    }

    /*
    * Res
    * */
    public EFSpriteFrame(Context context, int resId, int startPosX, int startPosY) {
        initBitmapRes(context, resId);
    }

    public EFSpriteFrame(Context context, int resId, int anchorPosX, int anchorPosY, int anchorPointType) {
        initBitmapRes(context, resId);
    }

    private void initBitmapRes(Context context, int resId) {
        mBitmap = LoadImgUtils.ReadResourceBitMap(context, resId);
        mHeight = mBitmap.getHeight();
        mWidth = mBitmap.getWidth();
    }
}
