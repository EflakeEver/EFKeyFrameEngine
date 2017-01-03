package com.eflake.keyanimengine.view;


import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.eflake.keyanimengine.bean.AnimEntity;
import com.eflake.keyanimengine.keyframe.EFAlphaKeyFrame;
import com.eflake.keyanimengine.keyframe.EFAnim;
import com.eflake.keyanimengine.keyframe.EFAnimManager;
import com.eflake.keyanimengine.keyframe.EFElement;
import com.eflake.keyanimengine.keyframe.EFPathKeyFrame;
import com.eflake.keyanimengine.keyframe.EFPosKeyFrame;
import com.eflake.keyanimengine.keyframe.EFRotationKeyFrame;
import com.eflake.keyanimengine.keyframe.EFScaleKeyFrame;
import com.eflake.keyanimengine.keyframe.EFViewPort;
import com.eflake.keyanimengine.keyframe.IEFAnimEndListener;
import com.eflake.keyanimengine.utils.JsonUtil;
import com.eflake.keyanimengine.utils.LoadImgUtils;

import java.io.File;

public class TransparentSurfaceView extends EFSurfaceView {

    public static final String PNG = ".png";
    private int i;
    private boolean mSequenceAnimEnable = true;

    public TransparentSurfaceView(Context context, int width, int height) {
        super(context, width, height);
    }

    public TransparentSurfaceView(Context context) {
        super(context);
    }

    public TransparentSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void addAnimDemo() {
        if (mSequenceAnimEnable) {
            mSequenceAnimEnable = false;
            String animDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "yinke_motor";  //json文件和动画图片所在的文件夹
            String jsonPath = animDirPath + File.separator + "yinke_moto_bezier_json.json";
            String a = JsonUtil.readFile(jsonPath, getContext());
            AnimEntity animEntity;
            animEntity = JsonUtil.jsonTobean(a, AnimEntity.class);
            final EFAnim anim1 = new EFAnim();
            anim1.setEndListener(new IEFAnimEndListener() {
                @Override
                public void onAnimEnd() {
                    mSequenceAnimEnable = true;
                }
            });
            anim1.setDuration(animEntity.getAnim().getDuration());
            anim1.setName(animEntity.getAnim().getName());
            anim1.setViewPort(new EFViewPort(1080.0f, 1920.0f));
            EFElement element = null;
            for (AnimEntity.AnimBean.ElementsBean efElement : animEntity.getAnim().getElements()) {
                element = new EFElement(mContext, animDirPath + File.separator + efElement.getName() + ".png", 0, 0);
                if (!TextUtils.isEmpty(efElement.getName())) {
                    element.setName(efElement.getName());
                }
                if (efElement.getPosition() != null) {
                    for (AnimEntity.AnimBean.ElementsBean.PositionBean positionBean : efElement.getPosition()) {
                        element.addPositionKeyFrame(new EFPosKeyFrame(positionBean.getTime(), positionBean.getValue()));
                    }
                }

                if (efElement.getAlpha() != null) {
                    for (AnimEntity.AnimBean.ElementsBean.AlphaBean alphaBean : efElement.getAlpha()) {
                        element.addAlphaKeyFrame(new EFAlphaKeyFrame(alphaBean.getTime(), alphaBean.getValue()));
                    }
                }
                if (efElement.getRotation() != null) {
                    for (AnimEntity.AnimBean.ElementsBean.RotationBean rotationBean : efElement.getRotation()) {
                        element.addRotationKeyFrame(new EFRotationKeyFrame(rotationBean.getTime(), rotationBean.getValue()));
                    }
                }

                if (efElement.getPath() != null) {
                    for (AnimEntity.AnimBean.ElementsBean.PathBean pathBean : efElement.getPath()) {
                        element.addPathKeyFrame(new EFPathKeyFrame(pathBean.getTime(), pathBean.getValue(), pathBean.getControl()));
                    }
                }

                if (efElement.getScale() != null) {
                    for (AnimEntity.AnimBean.ElementsBean.ScaleBean scaleBean : efElement.getScale()) {
                        element.addScaleKeyFrame(new EFScaleKeyFrame(scaleBean.getTime(), scaleBean.getValue()));
                    }
                }

                if (efElement.getParent() != null) {
                    element.setParentNode(anim1.mElements.get(efElement.getParent()));
                }
                anim1.addElement(efElement.getName(), element);
            }
            anim1.setIsRunning(true);
            EFAnimManager.getInstance().addAnim(i + "", anim1);
            i++;
        }
    }

    private String getFramePathByName(String frameName) {
        return LoadImgUtils.getFrameAnimPath(frameName);
    }

    private String getFireworkName(int index) {
        return "firework" + "_" + String.valueOf(index);
    }

    @Override
    protected void initSurfaceHolder(SurfaceHolder surfaceHolder) {
        //设置层级在所有View之上,且背景为透明
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

}
