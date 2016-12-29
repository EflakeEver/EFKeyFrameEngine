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
import com.eflake.keyanimengine.sprite.EFSprite;
import com.eflake.keyanimengine.utils.JsonUtil;
import com.eflake.keyanimengine.utils.LoadImgUtils;

import java.io.File;

public class TransparentSurfaceView extends EFSurfaceView {


    public static final String PNG = ".png";
    public static final int FIREWORKS_FILE_SIZE = 63;
    public static final String KEY_SINGLE_ANIM = "single_anim";
    public static final String KEY_PARENT_ELEMENT = "parent_element";
    private static final String KEY_CHILD_ELEMENT = "child_element";
    private static final String KEY_RED_PACKET_ELEMENT = "red_packet_element";
    private EFSprite mCucumberResSprite;
    private EFSprite mCucumberResAnchorSprite;
    private EFSprite mFireworkFileSprite;
    private EFSprite mFireworkFileAnchorSprite;
    private EFAnim animDemo;
    private int i;

    public EFAnim getAnimDemo() {
        return animDemo;
    }

    public void setAnimDemo(EFAnim animDemo) {
        this.animDemo = animDemo;
    }

    public TransparentSurfaceView(Context context, int width, int height) {
        super(context, width, height);
    }

    public TransparentSurfaceView(Context context) {
        super(context);
    }

    public TransparentSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initSprite() {
        // Sprite场景一,res+左上角坐标
//        mCucumberResSprite = new EFSprite(mContext, R.mipmap.cucumber, 100, 100);
//        mCucumberResSprite.setTag("cucumber_res");
//        EFScheduler.getInstance().addTarget(mCucumberResSprite);

        // Sprite场景二,res+锚点坐标(此处范例为center形式)
//        mCucumberResAnchorSprite = new EFSprite(mContext, R.mipmap.cucumber_new, 100, 100, EFSprite.ANCHOR_POINT_TYPE_CENTER);
//        mCucumberResAnchorSprite.setTag("cucumber_res_anchor");
//        EFScheduler.getInstance().addTarget(mCucumberResAnchorSprite);

        // TODO,测试file加载时,需要把asset文件夹下fireworks文件夹,放置到sd卡根目录
        // Sprite场景三,file+左上角坐标形式
//        mFireworkFileSprite = new EFSprite(mContext, getFramePathByName(getFireworkName(13)), 300, 300);
//        mFireworkFileSprite.setTag("firework_file");
//        EFScheduler.getInstance().addTarget(mFireworkFileSprite);

        // Sprite场景四,file+锚点形式
//        mFireworkFileAnchorSprite = new EFSprite(mContext, getFramePathByName(getFireworkName(48)), 800, 1000, EFSprite.ANCHOR_POINT_TYPE_CENTER);
//        mFireworkFileAnchorSprite.setTag("firework_file_anchor");
//        EFScheduler.getInstance().addTarget(mFireworkFileAnchorSprite);

        //TODO,图片压缩固定宽高读取场景

        // Sprite场景五,files+左上角坐标形式

       /* int mFireworksSize = FIREWORKS_FILE_SIZE;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < mFireworksSize; i++) {
            EFSpriteFrameCache.getInstance().addSpriteFrameByName(mContext, getFireworkName(i));
        }
        long endTime = System.currentTimeMillis();
        Log.e("eflake", "sprite frame load time = " + (endTime - startTime));*/

        //Anim关键帧动画场景一,红包动画实现
        //TODO 动画描述文件JSON解析
        //JSONObject jsonObject = new JSONObject("");
        /*EFAnim anim = new EFAnim();
        anim.setDuration(170);
        anim.setViewPort(new EFViewPort(1080, 1920));
        EFElement element_parent = new EFElement(mContext, R.mipmap.red_packet, 0.0f, 0.0f, 810.0f, 615.0f);
        element_parent.addPositionKeyFrame(new EFKeyFrame(0, 1, "540.0,-500.0"));
        element_parent.addPositionKeyFrame(new EFKeyFrame(0, 2, "540.0,244.0"));
        anim.addElement(KEY_RED_PACKET_ELEMENT, element_parent);

        EFAnimManager.getInstance().addAnim(KEY_SINGLE_ANIM, anim);*/

        //TODO 加载动画描述文件之前,需要确保动画资源文件已经下载完毕
//        EFAnim anim = new EFAnim();
//        anim.setDuration(170);
//        anim.setViewPort(new EFViewPort(1080.0f, 1920.0f));
//        EFElement element_parent = new EFElement(mContext, R.mipmap.mitao, 0, 0);
//        element_parent.addPositionKeyFrame(new EFPosKeyFrame(1, "200.0,-400.0"));
//        element_parent.addPositionKeyFrame(new EFPosKeyFrame(48, "200.0,400.0"));
//        element_parent.addPositionKeyFrame(new EFPosKeyFrame(88, "600.0,400.0"));
//        element_parent.addPositionKeyFrame(new EFPosKeyFrame(128, "600.0,-400.0"));
//        anim.addElement(KEY_PARENT_ELEMENT, element_parent);

//        EFElement element_child = new EFElement(mContext, R.mipmap.cucumber, 0, 0);
//        element_child.addPositionKeyFrame(new EFPosKeyFrame(1, "0.0,0.0"));
//        element_child.addPositionKeyFrame(new EFPosKeyFrame(88, "100.0,0.0"));
//        element_child.setParentNode(element_parent);
//        anim.addElement(KEY_CHILD_ELEMENT, element_child);


        //TODO 红包动画


        //TODO 曲线路径移动
        /*EFAnim anim = new EFAnim();
        anim.setDuration(1000);
        anim.setViewPort(new EFViewPort(1080.0f, 1920.0f));
        EFElement element_parent = new EFElement(mContext, R.mipmap.mitao, 0, 0);
        element_parent.addPositionKeyFrame(new EFPosKeyFrame(1, "200.0,700.0"));
        element_parent.addPositionKeyFrame(new EFPosKeyFrame(30, "500.0,700.0"));
        element_parent.addPositionKeyFrame(new EFPosKeyFrame(60, "700.0,700.0"));
        element_parent.addPositionKeyFrame(new EFPosKeyFrame(90, "1000.0,700.0"));
        element_parent.addPathKeyFrame(new EFPathKeyFrame(1, "200.0,700.0", ""));
        element_parent.addPathKeyFrame(new EFPathKeyFrame(30, "500.0,700.0", ""));
        element_parent.addPathKeyFrame(new EFPathKeyFrame(88, "700.0,700.0", ""));
        element_parent.addPathKeyFrame(new EFPathKeyFrame(90, "1000.0,700.0", ""));
        element_parent.addPathKeyFrame(new EFPathKeyFrame(30, "500.0,700.0", "550.0,300.0,600.0,250.0"));
        element_parent.addPathKeyFrame(new EFPathKeyFrame(60, "700.0,700.0", "750.0,300.0,500.0,200.0"));
        element_parent.addPathKeyFrame(new EFPathKeyFrame(90, "1000.0,700.0", "950.0,300.0"));
        element_parent.addRotationKeyFrame(new EFRotationKeyFrame(30, "0.0"));
        element_parent.addRotationKeyFrame(new EFRotationKeyFrame(60, "45.0"));
        element_parent.addRotationKeyFrame(new EFRotationKeyFrame(90, "-45.0"));
        element_parent.addAlphaKeyFrame(new EFAlphaKeyFrame(30, "100.0"));
        element_parent.addAlphaKeyFrame(new EFAlphaKeyFrame(60, "50.0"));
        element_parent.addAlphaKeyFrame(new EFAlphaKeyFrame(90, "100.0"));
        element_parent.addScaleKeyFrame(new EFScaleKeyFrame(30, "100.0,100.0"));
        element_parent.addScaleKeyFrame(new EFScaleKeyFrame(60, "50.0,50.0"));
        element_parent.addScaleKeyFrame(new EFScaleKeyFrame(90, "200.0,200.0"));
        anim.addElement(KEY_PARENT_ELEMENT, element_parent);

        EFElement element_child = new EFElement(mContext, R.mipmap.cucumber, 0, 0);
        //先设置父子关系，再设置相对坐标，此处相对坐标为参照父组件左上角点
        element_child.setParentNode(element_parent);
        element_child.addPositionKeyFrame(new EFPosKeyFrame(1, "0.0,0.0"));
        element_child.addPositionKeyFrame(new EFPosKeyFrame(44, "100.0,0.0"));
        element_child.addPositionKeyFrame(new EFPosKeyFrame(88, "0.0,0.0"));
        element_child.addScaleKeyFrame(new EFScaleKeyFrame(30, "100.0,100.0"));
        element_child.addScaleKeyFrame(new EFScaleKeyFrame(90, "50.0,50.0"));
        anim.addElement(KEY_CHILD_ELEMENT, element_child);

        //添加并执行动画
        EFAnimManager.getInstance().addAnim(KEY_SINGLE_ANIM, anim);*/

//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//        EFAnimManager.getInstance().startAnimByKey(KEY_SINGLE_ANIM);
//            }
//        }, 1000 * 3);
    }

    public void addAnimDemo() {
        String animDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "yinke_motor";  //json文件和动画图片所在的文件夹
//        String animDirPath = mContext.getExternalCacheDir();
        String jsonPath = animDirPath + File.separator + "yinke_moto_bezier_json.json";
        String a = JsonUtil.readFile(jsonPath, getContext());
        AnimEntity animEntity;
        animEntity = JsonUtil.jsonTobean(a, AnimEntity.class);
        EFAnim anim1 = new EFAnim();
        anim1.setDuration(animEntity.getAnim().getDuration());
        anim1.setName(animEntity.getAnim().getName());
        anim1.setViewPort(new EFViewPort(1080.0f, 1920.0f));
        EFElement element = null;
        for (AnimEntity.AnimBean.ElementsBean efElement : animEntity.getAnim().getElements()) {
           /* if (efElement.getName().equals("motor_body")) {
                element = new EFElement(mContext, R.mipmap.motor_body, 0, 0);
            } else if (efElement.getName().equals("motor_inke")) {
                element = new EFElement(mContext, R.mipmap.motor_inke, 0, 0);
            } else if (efElement.getName().equals("motor_wheel")) {
                element = new EFElement(mContext, R.mipmap.motor_wheel, 0, 0);
            } else if (efElement.getName().equals("motor_shine_back")) {
                element = new EFElement(mContext, R.mipmap.motor_shine_back, 0, 0);
            } else if (efElement.getName().equals("motor_shine_front")) {
                element = new EFElement(mContext, R.mipmap.motor_shine_front, 0, 0);
            } else if (efElement.getName().equals("boat_big_lights")) {
                element = new EFElement(mContext, R.mipmap.boat_big_lights, 0, 0);
            }*/
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
        EFAnimManager.getInstance().addAnim(i + "", anim1);
        EFAnimManager.getInstance().startAnimByKey(i + "");
        i++;
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
