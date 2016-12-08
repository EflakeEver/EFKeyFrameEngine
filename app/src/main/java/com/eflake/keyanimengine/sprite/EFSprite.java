package com.eflake.keyanimengine.sprite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.eflake.keyanimengine.utils.Utils;

/**
 * 精灵类
 * 
 * @author eflake
 * 
 */
public class EFSprite {
	private Drawable mDrawable = null;
	private int mPosX = 0;
	private int mPosY = 0;
	private int mWidth = 0;
	private int mHeight = 0;
	private Rect mRect;
	private Rect mCanvasRect;
	private Bitmap mBitmap;
	private Context context;

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

	public EFSprite(Context context, int resId, int x, int y) {
		mDrawable = Utils.ReadDrawable(context, resId);
		mPosX = x;
		mPosY = y;
		mWidth = mDrawable.getIntrinsicWidth();
		mHeight = mDrawable.getIntrinsicHeight();
		// mDrawable = Utils.rotateDrawable(mDrawable, mWidth,
		// mHeight,context.getResources());
		mRect = new Rect(mPosX, mPosY, x + mWidth, y + mHeight);
		mDrawable.setBounds(mRect);
	}

	public EFSprite(Context context, int resId, boolean isCenter,
					Rect canvas_rect) {
		mDrawable = Utils.ReadDrawable(context, resId);
		mWidth = mDrawable.getIntrinsicWidth();
		mHeight = mDrawable.getIntrinsicHeight();
		mRect = new Rect(canvas_rect.width() / 2 - mWidth / 2,
				canvas_rect.height() / 2 - mHeight / 2, canvas_rect.width() / 2
						- mWidth / 2 + mWidth, canvas_rect.height() / 2
						- mHeight / 2 + mHeight);
		mDrawable.setBounds(mRect);

	}

	public EFSprite(Context context, int y, int resId,
					boolean isCenterHorizontal, Rect canvas_rect) {
		mDrawable = Utils.ReadDrawable(context, resId);
		mPosY = y;
		mWidth = mDrawable.getIntrinsicWidth();
		mHeight = mDrawable.getIntrinsicHeight();
		mRect = new Rect(canvas_rect.width() / 2 - mWidth / 2, mPosY,
				canvas_rect.width() / 2 - mWidth / 2 + mWidth, mPosY + mHeight);
		mDrawable.setBounds(mRect);

	}

	public EFSprite(boolean isCenterVertical, Context context, int x,
					int resId, Rect canvas_rect) {
		mDrawable = Utils.ReadDrawable(context, resId);
		mPosX = x;
		mWidth = mDrawable.getIntrinsicWidth();
		mHeight = mDrawable.getIntrinsicHeight();
		mRect = new Rect(mPosX, canvas_rect.height() / 2 - mHeight / 2, mPosX
				+ mWidth, mPosY + mHeight);
		mDrawable.setBounds(mRect);
	}

	public EFSprite(Context context, int resId, int x, int y, Boolean isBitMap) {
		mBitmap = Utils.ReadBitMap(context, resId);
		mPosX = x;
		mPosY = y;
		mHeight = mBitmap.getHeight();
		mWidth = mBitmap.getWidth();
		mRect = new Rect(0, 0, mWidth, mHeight);
	}

	public EFSprite(Context context, int resId, boolean isCenterPosition,
					int centX, int centY, Rect canvas_rect) {
		mDrawable = Utils.ReadDrawable(context, resId);
		mWidth = mDrawable.getIntrinsicWidth();
		mHeight = mDrawable.getIntrinsicHeight();
		mPosX = centX - mWidth / 2;
		mPosY = centY - mHeight / 2;
		mRect = new Rect(mPosX, mPosY, mPosX + mWidth, mPosY + mHeight);
		mDrawable.setBounds(mRect);
	}

	public EFSprite(Context context, int resId, int x, int y, Rect canvas_rect) {
		mBitmap = Utils.ReadBitMap(context, resId);
		mPosX = x;
		mPosY = y;
		mHeight = mBitmap.getHeight();
		mWidth = mBitmap.getWidth();
		mRect = new Rect(0, 0, mWidth, mHeight);
		mCanvasRect = canvas_rect;
	}

	public void DrawDrawableSprite(Canvas canvas, Paint paint) {
		mDrawable.draw(canvas);
	}

	public void DrawBitmapFilledSprite(Canvas canvas, Paint paint) {
		canvas.drawBitmap(mBitmap, mRect, mCanvasRect, paint);
	}

	public void DrawBitmapSprite(Canvas canvas, Paint paint) {
		canvas.drawBitmap(mBitmap, mPosX, mPosY, paint);
	}

}
