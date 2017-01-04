package com.eflake.keyanimengine.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
import com.eflake.keyanimengine.utils.ScreenDimenUtils;
import com.eflake.keyanimengine.view.TransparentSurfaceView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TransparentSurfaceView transparentAnimView;
    private boolean mPermissionGranted;
    private int index;
    private boolean mSequenceAnimEnable = true;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Real_Splash);
        setContentView(R.layout.activity_main);
        //获取屏幕参数
        ScreenDimenUtils.init(this);
        initWidget();
        //Android 5.0以上，需要获取SD卡读写权限
        getPermission();
    }

    private void initWidget() {
        transparentAnimView = (TransparentSurfaceView) findViewById(R.id.transparentAnimView);
        Button bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mPermissionGranted) {
                    addAnim();
                } else {
                    addAnim();
                }
            }
        });
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.checkPermissions(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    mPermissionGranted = true;
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    public void addAnim() {
        if (mSequenceAnimEnable) {
            mSequenceAnimEnable = false;
            String animDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "yinke_motor";  //json文件和动画图片所在的文件夹
            String jsonPath = animDirPath + File.separator + "yinke_moto_bezier_json.json";
            String animJson = JsonUtil.readFile(jsonPath, this);
            AnimEntity animEntity = JsonUtil.jsonTobean(animJson, AnimEntity.class);
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
                element = new EFElement(this, animDirPath + File.separator + efElement.getName() + ".png", 0, 0);
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
            EFAnimManager.getInstance().addAnim(index + "", anim1);
            index++;
        }
    }
}
