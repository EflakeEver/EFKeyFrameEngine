package com.eflake.keyanimengine.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * 工具类
 *
 * @author eflake
 */
public class LoadImgUtils {
    public static int ScaleRadio = 1;

    /*
    * dp转px
    * */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    /*
    * px转dp
    * */
    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.d("eflake", "density = " + scale + "");
        return (int) (pxValue / scale);
    }

    /*
    * 加载Resource中的图片
    * */
    public static Bitmap ReadResourceBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.ARGB_8888;
        opt.inSampleSize = 1;
        return BitmapFactory.decodeResource(context.getResources(), resId, opt);
    }

    public static Bitmap ReadResourceBitMapWithScale(Context context, int resId, int targetWidth, int targetHeight) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, opt);
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = calSampleSize(opt, targetWidth, targetHeight);
        return BitmapFactory.decodeResource(context.getResources(), resId, opt);
    }

    /*
    * 加载文件中的图片
    * */
    public static Bitmap ReadFileBitMap(Context context, String imgPath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inSampleSize = 1;
        return BitmapFactory.decodeFile(imgPath, opt);
    }

    public static Bitmap ReadFileBitMap(Context context, String imgPath, int targetWidth, int targetHeight) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inSampleSize = 1;
        opt.inSampleSize = calSampleSize(opt, targetWidth, targetHeight);
        return BitmapFactory.decodeFile(imgPath, opt);
    }

    public static Drawable ReadDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    /*
    * 计算图片采样率
    * */
    public static int calSampleSize(BitmapFactory.Options options, int dstWidth, int dstHeight) {
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;
        int inSampleSize = 1;
        if (rawWidth > dstWidth || rawHeight > dstHeight) {
            float ratioHeight = (float) rawHeight / dstHeight;
            float ratioWidth = (float) rawWidth / dstHeight;
            inSampleSize = (int) Math.min(ratioWidth, ratioHeight);
        }
        return inSampleSize;
    }

}
