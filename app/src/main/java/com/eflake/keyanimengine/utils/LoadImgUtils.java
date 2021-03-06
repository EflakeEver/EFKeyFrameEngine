package com.eflake.keyanimengine.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.content.ContentValues.TAG;
import static com.eflake.keyanimengine.sprite.EFSpriteFrameCache.KSYUN_FRAMES;

/**
 * 工具类
 *
 * @author eflake
 */
public class LoadImgUtils {
    public static int ScaleRadio = 1;
    public static final String PNG = ".png";

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

    public static Bitmap ReadResourceBitMapWithScale(Context context, int resId, float targetWidth, float targetHeight) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, opt);
        opt.inJustDecodeBounds = false;
//        opt.outWidth = (int) targetWidth;
//        opt.outHeight = (int) targetHeight;
        opt.inSampleSize = calSampleSize(opt, (int) targetWidth, (int) targetHeight);
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

    public static Bitmap ReadFileBitMap(Context context, String imgPath, float targetWidth, float targetHeight) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inSampleSize = 1;
        opt.inSampleSize = calSampleSize(opt, (int) targetWidth, (int) targetHeight);
        return BitmapFactory.decodeFile(imgPath, opt);
    }

    public static Bitmap ReadFileBitMapWithViewPort(Context context, String imgPath, float widthViewPortFactor, float heightViewPortFactor) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, opt);/* 这里返回的bmp是null */
        opt.inPreferredConfig = Config.RGB_565;
        opt.outHeight = (int) (opt.outHeight * heightViewPortFactor);
        opt.outWidth = (int) (opt.outWidth * widthViewPortFactor);
//        opt.inSampleSize = 1;
//        opt.inSampleSize = calSampleSize(opt, opt.outWidth, opt.outHeight);
        opt.inJustDecodeBounds = false;
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

    public static String getAppDir() {
        File fileDir = Environment.getExternalStorageDirectory();
        if (fileDir == null) {
            Log.e(TAG, "External Storage ERROR!!!!!!");
            return null;
        }
        if (!fileDir.exists()) {
            Log.e(TAG, "media not exists!");
            return null;
        }
        String appDir = fileDir.getAbsolutePath();
        //LogUtil.e(TAG, "create file" + appDir);
        return appDir;
    }

    public static String getFrameAnimPath(String fileName) {
        return LoadImgUtils.getAppDir() + File.separator + KSYUN_FRAMES + File.separator + fileName + PNG;
    }

}
